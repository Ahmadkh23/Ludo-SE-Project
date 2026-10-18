@echo off
rem Starts EduLudo. Builds the JAR first if it does not exist yet.
cd /d "%~dp0"
if not exist dist\EduLudo.jar call build.bat || exit /b 1
start "" javaw -jar dist\EduLudo.jar
