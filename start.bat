@echo off
setlocal

echo === Backend - Navigating to backend service directory...
cd services

echo === Backend - Cleaning and packaging with Maven...
call mvn clean package -DskipTests

IF %ERRORLEVEL% NEQ 0 (
    echo Maven build failed. Aborting...
    exit /b %ERRORLEVEL%
)

echo === Backend - Starting Docker Compose...
docker-compose up --build -d

cd ..

echo === Frontend - Navigating to frontend service directory...
cd service-manager

echo === Frontend - Starting Docker Compose...
docker-compose up --build -d

echo === All services are up and running.

endlocal