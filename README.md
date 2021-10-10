# test-logfile

This sample application processes logfile passed as a command line argument and finds events which took more than 4 ms to complete. All events are stored in LOGFILEEVENTS table in HSQLDB.

# Steps to run this application
- Checkout this project and navigate to **/db/hsqldb-2.6.0/hsqldb/data**.
- Start command prompt.
- Run the following command `java -cp ../lib/hsqldb.jar org.hsqldb.server.Server --database.0 file:eventdb --dbname.0 eventdb`.
- Above command will start HSQLDB.
- Now, start the application with command `gradlew bootRun --args="C:\var\temp\logfile\logfile.txt"`.
- Above command will start the application and process the logfile passed as an argument. 

# What does this app do?
- Accept the path of log file as a command line argument.
- Parse the contents of log file.
- Flag event which takes more than 4 ms. 
- Saves the events in database.

# Improvements
- App can be improved upon by accepting comma separated log files as a command line argument.
- Parse csv log files path and try to process each log file in a separate thread.
- For multithreading, thread pool can be used. 