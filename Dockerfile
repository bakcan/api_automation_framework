# Use Maven with OpenJDK 11 as base image
FROM maven:3.8.6-openjdk-11-slim

# Set working directory
WORKDIR /app

# Copy pom.xml first (for better Docker layer caching)
COPY pom.xml .

# Download dependencies (this layer will be cached if pom.xml doesn't change)
RUN mvn dependency:go-offline -B

# Copy the entire project
COPY . .

RUN mvn test-compile

CMD ["mvn", "test"]

