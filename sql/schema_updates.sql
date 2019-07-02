-- Increase the name column maximum character length from 50 to 256
ALTER TABLE hudson.riverlife.chemistry ALTER COLUMN "name" TYPE varchar(256)
