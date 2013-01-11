
chcp 1251

	:: Генерация Web-сервиса
	:: 
	:: 

::set JAVA_HOME=C:\Program Files\Java\jdk1.7.0_01
set SCRIPTS_HOME="C:/1_work/43_SqlGreed/greet-web/greet-web/scripts/"
::set GRAILS_HOME=C:/1_work/40_STS/02_grails/grails-1.3.7
::set APPENGINE_HOME=C:/1_work/40_STS/01_appengine-java-sdk/appengine-java-sdk-1.6.0

cd %SCRIPTS_HOME%

set CPATH="../war/WEB-INF/classes/"
set SRC="../src/"
set WSDLDIR="http://churganovroman.appspot.com/wsdl/"
::set WSDLDIR="http://localhost:8080/wsdl/"
set CLASS="ru.roman.bim.server.service.data.DataProvider"
set SRV="{http://data.service.server.bim.roman.ru}DataProvider"
set PKG="ru.roman.bim.service.gae.wsclient"
set CLT_SRC=../../../bim-gui/src/
set CLT_OUT=../out/production/bim-gui/


call "%JAVA_HOME%/bin/wsimport.exe" -help >help_wsimport.txt
call "%JAVA_HOME%/bin/wsimport.exe" -version $1$2>RunWsImport.log
call "%JAVA_HOME%/bin/wsimport.exe" -verbose -b %WSDLDIR%DataProviderPortType_schema1.xsd -d %CLT_OUT% -s %CLT_SRC% -p %PKG% %WSDLDIR%DataProvider.wsdl

::pause


