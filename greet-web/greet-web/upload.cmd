

	:: установка версии приложения
	:: упаковка и подготовка к деплою
	:: деплой приложения на GAE



::set JAVA_HOME=C:\Program Files\Java\jdk1.7.0_01
set APPENGINE_HOME=C:\1_work\45_JavaLib\appengine-java-sdk-1.7.2
set ANT_HOME=C:\1_work\45_JavaLib\apache-ant-1.8.4
::set APPENGINE_HOME=C:\1_work\40_STS\01_appengine-java-sdk\appengine-java-sdk-1.6.0


	:: выполняем подписание jar

::call %ANT_HOME%\bin\ant -file ..\..\sql-greed-gui\keystore\build.xml sign_jar
call %ANT_HOME%\bin\ant -file ..\..\sql-greed-gui\keystore\build.xml sign_bim_jar
::pause
::exit

	:: заливаем в GAE

::call %GRAILS_HOME%/bin/grails.bat set-version 1
::call %GRAILS_HOME%/bin/grails.bat app-engine package					:::: чета не работает
::call %APPENGINE_HOME%/bin/appcfg.cmd --email=curdes@gmail.com rollback war
call %APPENGINE_HOME%/bin/appcfg.cmd --email=curdes@gmail.com update war

	:: обновляем задачи крон

:: call %APPENGINE_HOME%/bin/appcfg.cmd --email=curdes@gmail.com update_cron /war