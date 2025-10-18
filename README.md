# Bookstore API Automation

REST API automation framework for testing the FakeRestAPI Bookstore using Rest Assured, TestNG, and Allure.

## Prerequisites

### Required
- **Java 11+**
  ```bash
  # Check version
  java -version
  ```

- **Maven 3.8+**
  ```bash
  # Check version
  mvn -version
  ```

### Optional
- **Docker** (for local containerized test execution)
  ```bash
  # Check version
  docker --version
  ```


## Project Setup

```bash
# Clone the repository
git clone https://github.com/bakcan/api_automation_assignment.git
cd api_automation_assignment
```


## Running Tests Locally

```bash
# Clean and run all tests
mvn clean test

# Run all tests and view Allure report
mvn clean test; mvn allure:serve

# Run a group of tests and view Allure report
mvn clean test -Dgroups="smoke"; mvn allure:serve
```

**Available groups:** `smoke`, `positive`, `negative`, `get`, `get_id`, `post`, `put`, `delete`

**Examples:**
- Smoke tests: `mvn clean test -Dgroups="smoke" && mvn allure:serve`
- POST tests: `mvn clean test -Dgroups="post" && mvn allure:serve`
- Positive tests: `mvn test -Dtest=BooksApiPositiveTests && mvn allure:serve`

---

## Running Tests in Docker

```bash
# Build Docker image
docker build -t bookstore-api-tests .

# Run with default configuration (from config.properties)
docker run --rm bookstore-api-tests
```

## CI/CD Pipeline

GitHub Actions workflow automatically:
1. Builds Docker image
2. Runs tests in Docker container
3. Generates and deploys Allure report to GitHub Pages

View the latest test report at: https://bakcan.github.io/api_automation_assignment/

