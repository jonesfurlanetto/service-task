set -e

echo "=== Parando containers do Backend..."
cd services
docker-compose down
cd ..

echo "=== Parando containers do Frontend..."
cd service-manager
docker-compose down
cd ..

echo "=== Todos os containers foram parados com sucesso."
