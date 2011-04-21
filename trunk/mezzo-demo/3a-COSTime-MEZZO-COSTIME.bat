@echo off
TITLE 3a - COSTime MEZZO COSTIME
call jaco -Djava.util.logging.config.file=c:\mezzodev\mezzo-demo\timeserver.properties -Djacorb.home=C:\mezzodev\jacorb-2.3.1 -classpath "c:\mezzodev\mezzo-demo;c:\mezzodev\mezzo-demo\mezzo-costime-server.jar" fr.esiag.mezzodijava.mezzo.costimeserver.main.CosTimeServer MEZZO-COSTIME
pause