
	:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	:: ��������� ������ ����������
	:: �������� � ���������� � ������
	:: ������ ���������� �� GAE
        ::
	:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	::
	:: !!! � ������� appengine-java-sdk\bin\appcfg.cmd ������ ��������� ���� � ������������ java.exe
	:: �������� :
	::     set JAVA_HOME="C:\Program Files\Java\jdk1.7.0_11"
	::     %JAVA_HOME%\bin\java -Xmx1100m ...
	::
	:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::	


:::::::::::::::::::::::::::::::::::::::::::::
::::::::: WORK ::::::::::::::::::::::::::::::
:::::::::::::::::::::::::::::::::::::::::::::

set JAVA_HOME=C:\Program Files\Java\jdk1.7.0_11
set ANT_HOME=C:\01_work\008_LIB\ant\apache-ant-1.8.2\
set APPENGINE_HOME=C:\01_work\008_LIB\appengine-java-sdk-1.8.4\

:::::::::::::::::::::::::::::::::::::::::::::
:::::::::::::::::::::::::::::::::::::::::::::

	:: ��������� ���������� jar

::call %ANT_HOME%\bin\ant -file ..\..\sql-greed-gui\keystore\build.xml sign_jar
call %ANT_HOME%\bin\ant -file ..\..\sql-greed-gui\keystore\build.xml sign_bim_jar
::pause
::exit

	:: �������� � GAE

::call %GRAILS_HOME%/bin/grails.bat set-version 1
::call %GRAILS_HOME%/bin/grails.bat app-engine package					:::: ���� �� ��������
::call %APPENGINE_HOME%/bin/appcfg.cmd --email=curdes@gmail.com rollback war
call %APPENGINE_HOME%/bin/appcfg.cmd --email=curdes@gmail.com update war

	:: ��������� ������ ����

:: call %APPENGINE_HOME%/bin/appcfg.cmd --email=curdes@gmail.com update_cron /war