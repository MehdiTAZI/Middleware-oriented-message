@echo off
TITLE 3 - COSEvent Server SUDAN
call jaco -Dlog4j.configuration=eventsudanlog4j.properties -Djacorb.home=C:\mezzodev\jacorb-2.3.1 -classpath "c:\mezzodev\mezzo-demo;c:\mezzodev\mezzo-demo;mezzo-cosevent-server.jar" fr.esiag.mezzodijava.mezzo.coseventserver.main.CosEventServer SUDAN_SERVER
pause