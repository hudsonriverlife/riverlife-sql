package edu.columbia.riverLife;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class StandardSalinitySqlGenerator {
	protected boolean isEmpty(String buffer) {
		if (buffer == null || buffer.trim().length() == 0)
			return true;
		else
			return false;
	}
	protected void processDataLine(String line,BufferedWriter writer) throws IOException {
		String [] fields=line.split(",", -1);
		
		if (fields.length < 5)
			return;
		String time=fields[0];
		String reading=fields[1];
		String notes= fields[2];
		String siteSamplingId= fields[3];
		String methodId= fields[4];
		
		
		if (this.isEmpty(time) && this.isEmpty(reading) && this.isEmpty(notes) && this.isEmpty(siteSamplingId) 
				&& this.isEmpty(methodId)) {
			return;
		}
		
		StringBuffer sql=new StringBuffer();
		sql.append("insert into riverlife.standardized_salinity ( salinity_time, total_salinity_ppm, notes, site_sampling_id, salinity_method_id) values (");
		if (this.isEmpty(time))	
			sql.append("null,");
		else
			sql.append("'" + time + "',");
		if (this.isEmpty(reading))	
			sql.append("null,");
		else
			sql.append(reading + ",");
			
		if (this.isEmpty(notes))	
			sql.append("null,");
		else
			sql.append("'" + notes + "',");
		
		if (this.isEmpty(siteSamplingId))	
			sql.append("null,");
		else
			sql.append(siteSamplingId + ",");
		if (this.isEmpty(methodId))	
			sql.append("null);");
		else
			sql.append(methodId + ");");			
        writer.write(sql.toString()+"\n");
		
	//	insert into "schemaA".site_sampling_fish_count (amount, site_sampling_id, fish_id,fishing_method_id, sampling_date) values (1, 1, 17, 1, '2014-07-12');

	}
	public void processFile(String inputFile) {
		try {
			File file = new File(inputFile);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			int i=0;
			BufferedWriter writer = new BufferedWriter(new FileWriter("../../../../sql/2021/2021StandardizedSalinity.sql"));
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
		String inputFile="csv/2021_Standardized_Salinity_Updated.csv";
		StandardSalinitySqlGenerator work=new StandardSalinitySqlGenerator();
		work.processFile(inputFile);
	
	}
}
