package edu.columbia.riverLife;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ChemistrySqlGenerator {
	protected boolean isEmpty(String buffer) {
		if (buffer == null || buffer.trim().length() == 0)
			return true;
		else
			return false;
	}
	protected void processDataLine(String line,BufferedWriter writer) throws IOException {
		String [] fields=line.split(",", -1);
		
		if (fields.length < 11)
			return;
		String name=fields[0];
		String sample_time=fields[1];
		String do_ppm= fields[2];
		String water_temperature= fields[3];
		String saturation_percentage= fields[4];
		String ph=fields[5];
		String nitrates_ppm=fields[6];
		String phosphates_ppm= fields[7];
		String alkalinity_ppm= fields[8];
		String notes= fields[9];
		String site_sampling_id= fields[10];
		
		
		if (this.isEmpty(name) && this.isEmpty(site_sampling_id)) {
			return;
		}
		  
		StringBuffer sql=new StringBuffer();
		sql.append("insert into riverlife.chemistry ( name, sample_time, do_ppm, water_temperature, saturation_percentage, " +
		"ph, nitrates_ppm, phosphates_ppm, alkalinity_ppm, notes, site_sampling_id ) values (");
		if (this.isEmpty(name))	
			sql.append("null,");
		else
			sql.append("'" + name.trim() + "',");
		
		if (this.isEmpty(sample_time))	
			sql.append("null,");
		else
			sql.append("'" + sample_time + "',");
			
		if (this.isEmpty(do_ppm))	
			sql.append("null,");
		else
			sql.append(do_ppm + ",");
		
		if (this.isEmpty(water_temperature))	
			sql.append("null,");
		else
			sql.append(water_temperature + ",");
		
		if (this.isEmpty(saturation_percentage))	
			sql.append("null,");
		else
			sql.append(saturation_percentage + ",");		
		
		if (this.isEmpty(ph))	
			sql.append("null,");
		else
			sql.append(ph + ",");		
		
		if (this.isEmpty(nitrates_ppm))	
			sql.append("null,");
		else
			sql.append(nitrates_ppm + ",");		
		
		if (this.isEmpty(phosphates_ppm))	
			sql.append("null,");
		else
			sql.append(phosphates_ppm + ",");		
			
		if (this.isEmpty(alkalinity_ppm))	
			sql.append("null,");
		else
			sql.append(alkalinity_ppm + ",");		
		
		if (this.isEmpty(notes))	
			sql.append("null,");
		else
			sql.append("'" + notes + "',");
		
		if (this.isEmpty(site_sampling_id))	
			sql.append("null,");
		else
			sql.append(site_sampling_id + ");");
		
		writer.write(sql.toString()+"\n");
		//System.out.println(sql.toString());

		
	//	insert into "schemaA".site_sampling_fish_count (amount, site_sampling_id, fish_id,fishing_method_id, sampling_date) values (1, 1, 17, 1, '2014-07-12');

	}
	public void processFile(String inputFile) {
		try {
			File file = new File(inputFile);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			int i=0;
			BufferedWriter writer = new BufferedWriter(new FileWriter("../../../../sql/2021/2021Chemistry.sql"));
			while ((line = bufferedReader.readLine()) != null) {
				i++;
				if ( i == 1)
					continue;
				
				processDataLine(line,writer);
			}
			fileReader.close();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public static void main(String args[]) {
		String inputFile="csv/2021_chemistry.csv";
		ChemistrySqlGenerator work=new ChemistrySqlGenerator();
		work.processFile(inputFile);
	
	}
}
