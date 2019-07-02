-- Increase the name column maximum character length from 50 to 256
ALTER TABLE hudson.riverlife.chemistry ALTER COLUMN "name" TYPE varchar(256);
ALTER TABLE hudson.riverlife.physical_measurement ALTER COLUMN weather_last_3_days TYPE varchar(1024);
