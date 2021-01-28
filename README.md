# glcsv2log

Parce CSV export from graylog to log file:
Fields for export:
1. timestamp
2. app_name
3. hostname
4. traceId
5. source_class_name
6. level_name
7. full_message

Run:
app.jar {path_to_csv} {merge_to_one_log}

Where:
  path_to_csv - path to export file
  
  merge_to_one_log (true|false) - merge log from different app_name/hostname to one file. Default is true;

Output will be same file name with ".log" prefix
