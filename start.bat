@echo off
echo Navigating to backend service directory...
cd services

echo Cleaning and packaging with Maven...
call mvn clean package -DskipTests

IF %ERRORLEVEL% NEQ 0 (
    echo Maven build failed. Aborting...
    exit /b %ERRORLEVEL%
)

echo Starting Docker Compose...
docker-compose up --build