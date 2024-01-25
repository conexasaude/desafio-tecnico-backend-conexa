
echo "building jar"
sleep 1

mvn clean package spring-boot:run -DskipTests

echo "build completed"



docker-compose up -d --build    

echo "Application is running on docker!"
echo "COMPLETED..."