

	:: Генерация Web-сервиса
	:: 
	:: 

::set JAVA_HOME=C:\Program Files\Java\jdk1.6.0_29
set SCRIPTS_HOME="C:/1_work/43_SqlGreed/greet-web/greet-web/scripts/"
::set GRAILS_HOME=C:/1_work/40_STS/02_grails/grails-1.3.7
::set APPENGINE_HOME=C:/1_work/40_STS/01_appengine-java-sdk/appengine-java-sdk-1.6.0

cd %SCRIPTS_HOME%

set CPATH="../war/WEB-INF/classes/"
set SRC="../src/"
set WSDLDIR="../war/wsdl/"
set CLASS="ru.roman.bim.server.service.dataws.DataProvider"
set SRV="{http://dataws.service.server.bim.roman.ru}DataProvider"

call "%JAVA_HOME%/bin/wsgen.exe" -help $1 $2 >help_wsgen.txt
call "%JAVA_HOME%/bin/wsgen.exe" -version $1 $2 >RunWsGen.log
call "%JAVA_HOME%/bin/wsgen.exe" -verbose -cp %CPATH% %CLASS% -d %CPATH% -s %SRC% -r %WSDLDIR% -wsdl:soap1.1 -servicename %SRV% $1 $2 >>RunWsGen.log

::pause


