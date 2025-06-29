echo "Cleaning and packaging backend service with Maven..."
mvn -f services/pom.xml clean package -DskipTests

if [ $? -ne 0 ]; then
    echo "Maven build failed. Aborting..."
    exit 1
fi

echo "Starting Docker Compose..."
docker-compose -f services/docker-compose.yml up --build