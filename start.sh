set -e

echo "=== Backend - Navegando até a pasta 'services'..."
cd services

echo "=== Backend - Build do projeto com Maven..."
mvn clean package -DskipTests

echo "=== Backend - Subindo containers com Docker Compose..."
docker-compose up --build -d

cd ..

echo "=== Frontend - Navegando até a pasta 'service-manager'..."
cd service-manager

echo "=== Frontend - Subindo containers com Docker Compose..."
docker-compose up --build -d

cd ..

echo "=== Todos os serviços estão em execução com sucesso!"
