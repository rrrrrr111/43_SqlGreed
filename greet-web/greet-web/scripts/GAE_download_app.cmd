chcp 1251

	:: Выгрузка приложения
	:: 
	:: 
set APPENGINE_PYTHON_SDK_HOME="C:\Program Files (x86)\Google\google_appengine"
set BACKUP_FILE=C:\1_work\43_SqlGreed\buckup\backup_3_buckup
set APP_ID=shop4my
set APP_VERSION=2
set EMAIL=curdes@gmail.com


%APPENGINE_PYTHON_SDK_HOME%\appcfg.py download_app --email %EMAIL% -A %APP_ID% -V %APP_VERSION% %APP_ID% 