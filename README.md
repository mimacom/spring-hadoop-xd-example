Example with Apache Hadoop, Pig, Spring for Apache Hadoop and Spring XD
=======================================================================

In this example, a simple application is created which uses Apache Hadoop, Pig and the two Spring projects
Spring for Apache for Hadoop and Spring XD.

The example looks as follows:
* A stream looks for new weather data
* A Spring Batch application which
** uploads the new file to HDFS
** calculates the average temperature of clear days
** stores the results back to the filesystem
* Another stream sends the results per email

Prerequisites
-------------
* Apache Hadoop 2.x
* Pig 0.13
* Spring XD 1.0.0.RELEASE (with Hadoop configuration)

Installation
------------
    mvn assembly:assembly
    ./copyFiles.sh
    start xd and xd shell

Create and deploy jobs and streams with the XD console
------------------------------------------------------
First, the job will be created and deployed.

    xd:>job create --name weatherJob --definition "averageTemperatureJob" --deploy

Then the file lister which sends new files to the job.

    xd:>stream create --name weatherFiles --definition "file --ref=true > queue:job:weatherJob"  --deploy

And finally, the mail stream will be created and deployed.

    xd:>stream create mailstream --definition "file --dir=/tmp/xd/output/weatherJob/ --outputType=text/plain | mail --to='\"reciever@mimacom.com\"' --port=2525 --from='\"sender@mimacom.com\"' --subject='\"New average temperatures\"'"  --deploy


Running the application
-----------------------
The only missing thing here are input files. Weather data can be downloaded at http://cdo.ncdc.noaa.gov/qclcd_ascii/.
The example has been tested with the file 199607hourly.txt
    cp [filename] /tmp/xd/input/weatherFiles

