@echo off
setlocal

echo === Stopping Backend containers...
cd services
docker-compose down

cd ..

echo === Stopping Frontend containers...
cd service-manager
docker-compose down

cd ..

echo === All containers stopped.

endlocal