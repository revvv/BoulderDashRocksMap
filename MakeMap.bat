@echo off
setlocal
set INSTALL_DIR=%~dp0
java -cp "%INSTALL_DIR%bin;%INSTALL_DIR%bd-map.jar" bdmap.MakeMap %*
