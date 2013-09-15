chcp 1251

	:: Загрузка бэкапа
	:: 
	:: 
set APPENGINE_PYTHON_SDK_HOME="C:\Program Files (x86)\Google\google_appengine"
set BACKUP_FILE=C:\1_work\43_SqlGreed\buckup\backup_2
set APP_ID=shop4my
set EMAIL=curdes@gmail.com


%APPENGINE_PYTHON_SDK_HOME%\appcfg.py upload_data --url=http://%APP_ID%.appspot.com/_ah/remote_api --filename=%BACKUP_FILE% --noisy --email %EMAIL%  --log_file GAE_upload_backup.log  --db_filename GAE_upload_backup.db --kind _AE_Backup_Information,_AE_Backup_Information_Kind_Files,_AE_Backup_Information_Kind_Type_Info,_AE_DatastoreAdmin_Operation
