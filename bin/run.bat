@echo off
setlocal
REM UTF-8 output in Windows Console
chcp 65001 >nul

echo.
echo [INFO] 使用打包后的 Jar 运行 Web 工程。
echo.

REM 切到脚本所在目录
pushd "%~dp0"
REM 进入打包产物目录
pushd "..\ruoyi-admin\target"

REM JVM memory settings
set "JAVA_OPTS=-Xms256m -Xmx1024m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=512m"

REM Run the packaged application
java -jar %JAVA_OPTS% ruoyi-admin.jar

popd
popd
pause
endlocal
