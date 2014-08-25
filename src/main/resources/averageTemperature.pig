month1 = LOAD '$inputfile' USING PigStorage(',') AS (wban:int, date:chararray, time:chararray, stationType:int, maintenanceIndicator:chararray, skyCondition:chararray, visibility:float, weatherType:chararray, weatherTypeFlag:int, dryTemp:int);

transform = FOREACH month1 GENERATE date, SUBSTRING(date, 0, 4) as year, SUBSTRING(date, 4, 6) as month, SUBSTRING(date, 6, 8) as day, skyCondition, dryTemp;

grouped = GROUP transform by (month, day);

aggregated = FOREACH grouped GENERATE group, AVG(transform.dryTemp);

STORE aggregated INTO '$outputpath' USING PigStorage(':');