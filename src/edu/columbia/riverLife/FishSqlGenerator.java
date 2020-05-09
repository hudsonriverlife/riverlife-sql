package edu.columbia.riverLife;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FishSqlGenerator {
	protected void processFishLine(String line, BufferedWriter writer) throws IOException {
		String [] ids=line.split(",");
		int length=ids.length;
		if (length < 2)
			return;
		String fishId=ids[0];
		if (fishId.trim().length() == 0)
			return;
		String name=ids[1];
		if (name == null || name.trim().length() == 0)
			name = "null";	
		StringBuffer sql=new StringBuffer();
		sql.append("insert into riverlife.fish ( fish_id, name) values (");
		sql.append(fishId + ",'" + name +  "');"+"\n");
		writer.write(sql.toString());
	}
	public void processFile(String inputFile) {
		try {
			File file = new File(inputFile);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			int i=0;
			BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/song/Documents/javaworkspace/riverlife2/data/2019_DITL_CSVs/sql/2019Fish.sql"));
			while ((line = bufferedReader.readLine()) != null) {
				i++;
				if ( i == 1)
					continue;
				processFishLine(line,writer);
			}
			fileReader.close();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	public static void main(String args[]) {
		String inputFile="/Users/song/Documents/javaworkspace/riverLife2/data/2019_DITL_CSVs/2019Fish.csv";
		FishSqlGenerator work=new FishSqlGenerator();
		work.processFile(inputFile);	
	}
}
