Example with Apache Hadoop, Pig, Spring for Apache Hadoop and Spring XD
=======================================================================

In this example, a simple application is created which uses *Apache Hadoop*, *Pig* and the two Spring projects
*Spring for Apache for Hadoop* and *Spring XD*.

The example looks as follows:
* A stream looks for new weather data
* A Spring Batch job which
  * uploads the new file to HDFS
  * calculates the average temperature of clear days
  * stores the results back to the filesystem
* Another stream sends the results per email

Prerequisites
-------------
* Apache Hadoop 2.x
* Spring XD 1.0.0.RELEASE (with Hadoop configured)

Installation
------------
    mvn assembly:assembly
    ./copyFiles.sh

Create and deploy jobs
----------------------
Before running the example, Hadoop, XD and XD Shell must be running.

First, the job will be created and deployed.

    xd:>job create --name weatherJob --definition "averageTemperatureJob" --deploy

Then a file lister which sends new files to the job.

    xd:>stream create --name weatherFiles --definition "file --ref=true > queue:job:weatherJob"  --deploy

And finally, a mail stream will be created and deployed.

    xd:>stream create mailstream --definition "file --dir=/tmp/xd/output/weatherJob/ --outputType=text/plain | mail --to='\"reciever@domain.com\"' --port=2525 --from='\"sender@domain.com\"' --subject='\"New average temperatures\"'"  --deploy


Running the application
-----------------------
The only missing thing here are input files. Weather data can be downloaded at http://cdo.ncdc.noaa.gov/qclcd_ascii/.
The example has been tested with the file `199607hourly.txt`.

    cp [filename] /tmp/xd/input/weatherFiles

