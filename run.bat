@echo off
echo Compiling ATM Interface...
javac -sourcepath src -d out src\app\Main.java
if %ERRORLEVEL% equ 0 (
    echo Launching ATM Application...
    java -cp out app.Main
) else (
    echo Compilation failed.
    pause
)
