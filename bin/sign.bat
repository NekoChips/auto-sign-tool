@echo off
cd %~dp0\..
set base_home=%cd%
set JAVA_OPTS=-server -Xms128m -Xmx256m -Xmn128m -Xss128k -Duser.dir=%base_home%
set SPRING_OPTS=


for /f "delims=\" %%a in ('dir /b /a-d /o-d "%base_home%\*.jar"') do (
  set app_name=%%a
)

java -jar %JAVA_OPTS% %base_home%\%app_name% %SPRING_OPTS% > %base_home%\bin\run.log