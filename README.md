# Bookstore API Automation

REST API automation framework for testing the FakeRestAPI Bookstore using Rest Assured, TestNG, and Allure.

## Running Tests Locally

```bash
# Clean and run all tests
mvn clean test

# Run tests and view Allure report
mvn clean test allure:serve
```

## Running Tests in Docker

```bash
# Build Docker image
docker build -t bookstore-api-tests .

# Run with default configuration (from config.properties)
docker run --rm bookstore-api-tests

# Run with custom environment variables
docker run --rm \
  -e BASE_URL=https://custom-api.example.com \
  -e BASE_PATH=/api/v1 \
  bookstore-api-tests
```

## CI/CD Pipeline

GitHub Actions workflow automatically:
1. Builds Docker image
2. Runs tests in Docker container
3. Generates Allure test report and deploys in Github actions

