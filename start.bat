@echo off

javac @src\compile.list -d class
if errorlevel 1 (
    echo La compilation a echoue.
    exit /b 1
)

java -cp class retroconception.Controleur
if errorlevel 1 (
    echo L'execution a echoue.
    exit /b 1
)

echo L'execution s'est terminee avec succes.