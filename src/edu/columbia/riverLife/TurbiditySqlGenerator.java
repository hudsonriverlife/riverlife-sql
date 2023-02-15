package edu.columbia.riverLife;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TurbiditySqlGenerator {
	protected boolean isEmpty(String buffer) {
		if (buffer == null || buffer.trim().length() == 0)
			return true;
		else
			return false;
	}
	protected void processDataLine(String line,BufferedWriter writer) throws IOException {
		String [] fields=line.split(",", -1);
		
		if (fields.length < 3)
			return;
		String turbidity=fields[0];
		String turbidity_method_id=fields[1];
		String site_sampling_id= fields[2];
		
		
		if (this.isEmpty(turbidity) && this.isEmpty(turbidity_method_id) && this.isEmpty(site_sampling_id)) {
			return;
		}
		  
		StringBuffer sql=new StringBuffer();
		sql.append("insert into riverlife.turbidity ( turbidity, turbidity_method_id, site_sampling_id ) values (");
		
		if (this.isEmpty(turbidity))	
			sql.append("null,");
		else
			sql.append(turbidity + ",");
		
		if (this.isEmpty(turbidity_method_id))	
			sql.append("null,");
		else
			sql.append(turbidity_method_id + ",");
		
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
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			int i=0;
			BufferedWriter writer = new BufferedWriter(new FileWriter("../../../../sql/2021/2021Turbidity.sql"));
			while ((line = bufferedReader.readLine()) != null) {
				i++;
				if ( i == 1)
					continue;
				
				processDataLine(line,writer);
			}
			fileReader.close();
			//System.out.println(stringBuffer.toString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public static void main(String args[]) {
		String inputFile="csv/2021_turbidity_updated.csv";
		TurbiditySqlGenerator work=new TurbiditySqlGenerator();
		work.processFile(inputFile);
	
	}
}
