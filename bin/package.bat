@echo off
setlocal
REM UTF-8 output in Windows Console
chcp 65001 >nul

echo.
echo [INFO] 打包 Web 工程，生成 Jar 包文件。
echo.

REM 切到脚本所在目录
pushd "%~dp0"
REM 切到仓库根目录
pushd ".."

REM Package without tests to speed up local builds
call mvn clean package -Dmaven.test.skip=true

popd
popd
pause
endlocal
