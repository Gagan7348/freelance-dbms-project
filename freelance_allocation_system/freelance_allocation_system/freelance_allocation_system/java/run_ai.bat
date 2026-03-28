@echo off
set "BIN_PATH=bin"
set "LIB_PATH=lib"
set "MAIN_CLASS=Main"

echo Starting Freelance Project Allocation System (SQLite Mode)...
java -cp "%BIN_PATH%;%LIB_PATH%\sqlite-jdbc.jar" %MAIN_CLASS%
