# Bookstore API Automation

REST API automation framework for testing the FakeRestAPI Bookstore using Rest Assured, TestNG, and Allure.

## Running Tests Locally

```bash
# Clean and run all tests
mvn clean test

# Run all tests and view Allure report
mvn clean test; mvn allure:serve

# Run a group of tests and view Allure report
mvn clean test -Dgroups="smoke"; mvn allure:serve
```

## Running Tests in Dockerß

```bash
# Build Docker imageß
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
3. Generates and deploys Allure report to GitHub Pages

View the latest test report at: https://bakcan.github.io/api_automation_assignment/

