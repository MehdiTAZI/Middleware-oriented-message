@echo off
MODE CON: COLS=120 LINES=300
TITLE 5 - Channel Agent Administrator
set "CLASSPATH=.;sensorsdatasupplier.jar"
call jaco fr.esiag.mezzodijava.nuclear.sensorsdatasupplier.CAAgentProcess SUDAN_SERVER ARGENTINA_SERVER
pause