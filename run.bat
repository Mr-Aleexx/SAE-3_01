@echo off
REM Compilation
javac @compile.list -d bin
IF %ERRORLEVEL% NEQ 0 (
    echo La compilation a echoue.
    exit /b 1
)

REM Exécution
java -cp bin src.Controleur
IF %ERRORLEVEL% NEQ 0 (
    echo L'execution a echoue.
    exit /b 1
)

echo L'execution s'est terminee avec succes.