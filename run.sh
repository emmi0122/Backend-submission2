
#!/bin/bash
echo "Packaging Maven Project..."
mvn clean package
echo "Package complete, running project..."
java -jar target/cardemo-0.0.1-SNAPSHOT.jar