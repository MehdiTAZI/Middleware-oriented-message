set "DEMO_DIR=C:\mezzodev\mezzo-demo"
set "M2_REPO=C:\mezzodev\maven_repository\fr\esiag\mezzodijava"
set "WORK_DIR=C:\mezzodev\mezzo_workspace"

cd %WORK_DIR%\mezzo-it
call mvn clean install -Dmaven.test.skip -P shade
copy %M2_REPO%\mezzo-it\0.0.1-SNAPSHOT\mezzo-it-0.0.1-SNAPSHOT.jar %DEMO_DIR%\mezzoit.jar

pause