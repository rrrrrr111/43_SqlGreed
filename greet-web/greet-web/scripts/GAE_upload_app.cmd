chcp 1251
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
::::::::: HOME ::::::::::::::::::::::::::::::
:::::::::::::::::::::::::::::::::::::::::::::

::set JAVA_HOME=C:\Program Files\Java\jdk1.7.0_01
set ANT_HOME=C:\1_work\45_JavaLib\apache-ant-1.8.4
set APPENGINE_HOME=C:\1_work\45_JavaLib\appengine-java-sdk-1.8.4

:::::::::::::::::::::::::::::::::::::::::::::
:::::::::::::::::::::::::::::::::::::::::::::

	:: �������� � GAE

::call %GRAILS_HOME%/bin/grails.bat set-version 1
::call %GRAILS_HOME%/bin/grails.bat app-engine package					:::: ���� �� ��������
::call %APPENGINE_HOME%/bin/appcfg.cmd --email=curdes@gmail.com rollback war
call %APPENGINE_HOME%/bin/appcfg.cmd --email=curdes@gmail.com update shop4my

	:: ��������� ������ ����

:: call %APPENGINE_HOME%/bin/appcfg.cmd --email=curdes@gmail.com update_cron /shop4my