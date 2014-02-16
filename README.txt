This is java port of sma-bluetooth.

Install instructions:

Download a java implementation of a bluetooth stack. 
I used bluecove which can be found here:
http://bluecove.org

Create a new project in your ide and attach the jars bluecove-2.1.0.jar and bluecove-gpl-2.1.0.jar as libraries.
Put them in a 'lib' dir.

Copy all the smajava java files to your project and indicate the src dir as a source dir.

It should compile without errors.

On linux systems you have to install some dev library:
sudo apt-get install libbluetooth-dev

Usage:

Find out the mac address of your inverter with a scan:
hcitool scan

Run be.geek.smajava.Smajava as such:
java -Dfile.encoding=UTF-8 -Dbluecove.native.path=lib -Djava.util.logging.config.file=./logging.properties -classpath lib/bluecove-2.1.0.jar:lib/bluecove-gpl-2.1.0.jar:smajava.jar be.geek.smajava.Smajava <inverter address> yyyy-MM-dd

The second argument is an optional from date, f.e. '25-01-2012' (without quotes).
