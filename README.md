How to run MinotaurPresents.java
In the command line, type the following in order:
- javac MinotaurPresents.java
- java MinotaurPresents
The program will run for a couple of seconds and will finish as soon as all presents are thanked
Each thread will print what it is doing, so it might be hard to read before it finishes.


How to run MarsRover.java
In the command line, type the following in order:
- javac MarsRover.java
- java MarsRover
After typing the last command, the program will start. It won't actually print anything until a minute has passed
That is because the measurements are being taken every second, and the report prints every minute.

Disclaimer: I know the assignment asked for the measurements to be every minute and the report to be every hour however
in order to make testing and grading easier, I made it so that measurements are taken every second and reports are made
every minute. After a report is made, the program will not terminate. It will instead wipe all data collected so far
and start taking measurements again, and after an additional minute it will make a new report. The program only terminates
if the user forces it by using the command "ctrl+C".
