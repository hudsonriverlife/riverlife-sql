package edu.columbia.riverLife;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SiteSamplingSqlGenerator {

	public SiteSamplingSqlGenerator() {
		// TODO Auto-generated constructor stub
	}

	protected boolean isEmpty(String buffer) {
		if (buffer == null || buffer.trim().length() == 0)
			return true;
		else
			return false;
	}
	
	protected void processDataLine(String line) {
		String [] fields=line.split(",", -1);
		
		if (fields.length < 3)
			return;
		String river_site_id=fields[0];
		String site_sampling_id=fields[1];
		String sampling_time = fields[2];
		
		
		if (this.isEmpty(river_site_id) && this.isEmpty(site_sampling_id) && this.isEmpty(sampling_time)) {
			return;
		}
		  
		StringBuffer sql=new StringBuffer();
		sql.append("insert into riverlife.site_sampling ( river_site_id, site_sampling_id, sampling_time) values (");
		
		sql.append(river_site_id + ",");
		sql.append(site_sampling_id + ",");
		
		if (this.isEmpty(sampling_time))	
			sql.append("null,");
		else
			sql.append("'"+sampling_time + "');");
					
		System.out.println(sql.toString());
    }
	
	public void processFile(String inputFile) {
		try {
			File file = new File(inputFile);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			int i=0;
			while ((line = bufferedReader.readLine()) != null) {
				i++;
				if ( i == 1)
					continue;
				
				processDataLine(line);
			}
			fileReader.close();
			System.out.println(stringBuffer.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public static void main(String args[]) {
		String inputFile="/Users/song/Documents/javaworkspace/riverlife2/data/2019_DITL_CSVs/2019Site_Sampling.csv";
		SiteSamplingSqlGenerator work=new SiteSamplingSqlGenerator();
		work.processFile(inputFile);
	
	}
	
}
