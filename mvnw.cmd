@echo off
setlocal ENABLEDELAYEDEXPANSION
set BASE_DIR=%~dp0
set WRAPPER_DIR=%BASE_DIR%.mvn\wrapper
set WRAPPER_JAR=%WRAPPER_DIR%\maven-wrapper.jar
set PROPS_FILE=%WRAPPER_DIR%\maven-wrapper.properties

if not exist "%WRAPPER_DIR%" mkdir "%WRAPPER_DIR%"

for /f "usebackq tokens=*" %%A in (`powershell -NoProfile -Command "$l=(Get-Content '%PROPS_FILE%' -ErrorAction SilentlyContinue) -match '^wrapperUrl='; if(-not $l){exit 1}; ($l -replace '^wrapperUrl=','').Trim()"`) do set WRAPPER_URL=%%A

if "%WRAPPER_URL%"=="" (
  echo ERROR: Could not find 'wrapperUrl=' in %PROPS_FILE%
  exit /b 1
)

if not exist "%WRAPPER_JAR%" (
  echo Downloading Maven Wrapper from %WRAPPER_URL%
  powershell -NoProfile -Command "Invoke-WebRequest -UseBasicParsing -Uri '%WRAPPER_URL%' -OutFile '%WRAPPER_JAR%'"
  if errorlevel 1 (
    echo ERROR: Failed to download Maven Wrapper jar.
    exit /b 1
  )
)

java %MAVEN_OPTS% -classpath "%WRAPPER_JAR%" org.apache.maven.wrapper.MavenWrapperMain %*
