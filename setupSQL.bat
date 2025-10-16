@echo off

REM Imposta le credenziali del database
SET MYSQL_USER=username
SET MYSQL_PASSWORD=password

REM Specifica il percorso del client MySQL e del file SQL
SET MYSQL_PATH="C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe"
SET SQL_FILE="C:\Users\invy_\Desktop\PROGRAMMA_FUNZIONANTE(QUELLO VERO)\setup.sql"

REM Esegui il file SQL
%MYSQL_PATH% -u %MYSQL_USER% -p%MYSQL_PASSWORD% < %SQL_FILE%

echo Comandi SQL eseguiti con successo!
pause
