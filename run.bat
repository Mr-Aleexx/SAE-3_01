@echo off

rem Compilation
javac @compile.list -d bin
if %errorlevel% neq 0 (
    echo La compilation a echoue.
    exit /b 1
)

rem Execution
java -cp bin Controleur
if %errorlevel% neq 0 (
    echo L'execution a echoue.
    exit /b 1
)

echo L'execution s'est terminee avec succes.
