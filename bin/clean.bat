@echo off
setlocal
REM UTF-8 output in Windows Console
chcp 65001 >nul

echo.
echo [INFO] 清理工程 target 生成路径。
echo.

REM 切到脚本所在目录
pushd "%~dp0"
REM 切到仓库根目录
pushd ".."

REM Clean build artifacts
call mvn clean

popd
popd
pause
endlocal
