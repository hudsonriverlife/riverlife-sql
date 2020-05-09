package edu.columbia.riverLife;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FishCountSqlGenerator {
	private ArrayList<Integer> fishIds=new ArrayList<Integer>();
	
	protected void processHeader(String line) {
		String [] ids=line.split(",", -1);
		int length=ids.length;
		for (int i=2;i<length;i++) {
			String buffer=ids[i];
			if (buffer.trim().length() == 0)
				continue;
			int id=Integer.parseInt(buffer);
			fishIds.add(id);
		}
	}
	
	protected void processFishCountLine(String line, BufferedWriter writer) throws IOException {
		String [] ids=line.split(",");
		int length=ids.length;
		if (length < 2)
			return;
	//	String date=ids[0];
	//	if (date.trim().length() == 0)
	//		return;
		String samplingSiteId=ids[0];
		if (samplingSiteId.trim().length() == 0)
			return;
		String methodId=ids[1];
		if (methodId == null || methodId.trim().length() == 0)
			methodId = "null";
		for (int i=2, j=0;i < ids.length && j<fishIds.size();i++, j++) {
			String buffer=ids[i];
			if (buffer.trim().length() == 0)
				continue;
			StringBuffer sql=new StringBuffer();
			Integer fishId=this.fishIds.get(j);
			sql.append("insert into riverlife.site_sampling_fish_count ( site_sampling_id, fishing_method_id, fish_id, amount) values (");
			sql.append(samplingSiteId + "," + methodId + "," + fishId + "," + buffer + ");"+"\n");			
			//System.out.println(sql.toString());
			writer.write(sql.toString());
		}
	//	insert into "schemaA".site_sampling_fish_count (amount, site_sampling_id, fish_id,fishing_method_id, sampling_date) values (1, 1, 17, 1, '2014-07-12');

	}
	public void processFile(String inputFile) {
		try {
			File file = new File(inputFile);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			int i=0;
			BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/song/Documents/javaworkspace/riverlife2/data/2019_DITL_CSVs/sql/2019FishCount.sql"));
			while ((line = bufferedReader.readLine()) != null) {
				i++;
				if ( i == 1)
					continue;
				if (i == 2 ) {
					processHeader(line);
					continue;
				}
				processFishCountLine(line,writer);
			}
			fileReader.close();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public static void main(String args[]) {
		String inputFile="/Users/song/Documents/javaworkspace/riverLife2/data/2019_DITL_CSVs/2019FishCount.csv";
		FishCountSqlGenerator work=new FishCountSqlGenerator();
		work.processFile(inputFile);
	
	}

}
