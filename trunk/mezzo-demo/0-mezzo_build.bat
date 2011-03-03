set "DEMO_DIR=C:\mezzodev\mezzo-demo"
set "M2_REPO=C:\mezzodev\maven_repository\fr\esiag\mezzodijava"
set "WORK_DIR=C:\mezzodev\mezzo_workspace"

cd %WORK_DIR%\mezzo-parent
call mvn clean install -Dmaven.test.skip -P shade
cd %WORK_DIR%\nuclear-parent
call mvn clean install -Dmaven.test.skip -P shade

cd %DEMO_DIR%
del %DEMO_DIR%\*.jar
copy %M2_REPO%\mezzo-cosevent-server\0.0.1-SNAPSHOT\mezzo-cosevent-server-0.0.1-SNAPSHOT.jar mezzo-cosevent-server.jar 
copy %M2_REPO%\sensorsdatasupplier\0.0.1-SNAPSHOT\sensorsdatasupplier-0.0.1-SNAPSHOT.jar sensorsdatasupplier.jar
copy %M2_REPO%\systemstatemonitor\0.0.1-SNAPSHOT\systemstatemonitor-0.0.1-SNAPSHOT.jar systemstatemonitor.jar
copy %M2_REPO%\sensor\0.0.1-SNAPSHOT\sensor-0.0.1-SNAPSHOT.jar sensor.jar
copy %M2_REPO%\mezzo-costime-server\0.0.1-SNAPSHOT\mezzo-costime-server-0.0.1-SNAPSHOT.jar mezzo-costime-server.jar

pause