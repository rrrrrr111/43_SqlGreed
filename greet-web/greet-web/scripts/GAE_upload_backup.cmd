chcp 1251

	:: Загрузка бэкапа
	:: 
	:: 
set APPENGINE_PYTHON_SDK_HOME="C:\Program Files (x86)\Google\google_appengine"
set BACKUP_FILE_PATH=C:\01_work\43_SqlGreed\buckup\
set BACKUP_FILE_NAME=backup_2
set APP_ID=bim-app
set EMAIL=curdes@gmail.com


%APPENGINE_PYTHON_SDK_HOME%\appcfg.py upload_data --url=http://%APP_ID%.appspot.com/_ah/remote_api --filename=%BACKUP_FILE_PATH%%BACKUP_FILE_NAME% --noisy --email %EMAIL%  --log_file GAE_upload_backup_%BACKUP_FILE_NAME%.log  --db_filename GAE_upload_backup_%BACKUP_FILE_NAME%.db 


::::::::::: BACKUP TABLES 
:: _AE_Backup_Information 
:: _AE_Backup_Information_Kind_Files
:: _AE_Backup_Information_Kind_Type_Info 
