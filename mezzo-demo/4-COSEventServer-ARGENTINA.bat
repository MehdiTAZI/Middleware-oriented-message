@echo off
TITLE 4 - COSEvent Server ARGENTINA
call jaco -Dlog4j.configuration=eventargentinalog4j.properties -Djacorb.home=C:\mezzodev\jacorb-2.3.1 -classpath "c:\mezzodev\mezzo-demo;c:\mezzodev\mezzo-demo;mezzo-cosevent-server.jar" fr.esiag.mezzodijava.mezzo.coseventserver.main.CosEventServer ARGENTINA_SERVER
pause