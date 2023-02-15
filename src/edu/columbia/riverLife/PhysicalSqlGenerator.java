package edu.columbia.riverLife;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PhysicalSqlGenerator {
	protected boolean isEmpty(String buffer) {
		if (buffer == null || buffer.trim().length() == 0)
			return true;
		else
			return false;
	}
	protected void processDataLine(String line,BufferedWriter writer) throws IOException {
	    String[] fields = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
	    
		if (fields.length < 11)
			return;
	        String measure_time=fields[0];
		String air_temperature=fields[1];
		String weather_today= fields[2];
		String weather_last_3_days = fields[3];
		String cloud_coverage_id = fields[4];
		String wind_speed = fields[5];
		String wind_beaufort_id = fields[6];
		String wind_direction_id = fields[7];
		String water_condition = fields[8];
		String water_temperature = fields[9];
		String siteSamplingId= fields[10];
		
		
		if (this.isEmpty(measure_time) &&  this.isEmpty(siteSamplingId) ) {
			return;
		}
		
		StringBuffer sql=new StringBuffer();
		 
		String columns="measure_time, air_temperature, weather_today, weather_last_3_days, cloud_coverage_id, " +
				"wind_speed, wind_beaufort_id, wind_direction_id, water_condition, water_temperature, site_sampling_id";
		sql.append("insert into riverlife.physical_measurement (" + columns + ") values (");
		
		appendColumns(sql, measure_time);
		appendColumns(sql, air_temperature);
		appendColumns(sql, weather_today);
		appendColumns(sql, weather_last_3_days);
		appendColumns(sql, cloud_coverage_id);
		appendColumns(sql, wind_speed);
		appendColumns(sql, wind_beaufort_id);
		appendColumns(sql, wind_direction_id);
		appendColumns(sql, water_condition);
		appendColumns(sql, water_temperature);
		
		if (this.isEmpty(siteSamplingId))	
			sql.append("null);");
		else
			sql.append(siteSamplingId + ");");
		writer.write(sql.toString()+"\n");		
	//	insert into "schemaA".site_sampling_fish_count (amount, site_sampling_id, fish_id,fishing_method_id, sampling_date) values (1, 1, 17, 1, '2014-07-12');
	}
	
	private void appendColumns(StringBuffer sql, String value) {
		if (this.isEmpty(value))	
			sql.append("null,");
		else
			sql.append("'" + value + "',");
		
	}
	public void processFile(String inputFile) {
		try {
			File file = new File(inputFile);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			int i=0;
			BufferedWriter writer = new BufferedWriter(new FileWriter("../../../../sql/2021/2021Physical.sql"));
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
		String inputFile="csv/2021_Physical_updated.csv";
		
		PhysicalSqlGenerator work=new PhysicalSqlGenerator();
		work.processFile(inputFile);
	
	}
}
