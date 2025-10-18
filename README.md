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

# Run all tests with default configuration (from config.properties)
docker run --rm bookstore-api-tests

# Run all positive tests (8 tests)
docker run --rm bookstore-api-tests mvn test -Dtest=BooksApiPositiveTests
```

## CI/CD Pipeline

### Automatic Execution
GitHub Actions automatically runs **all tests** on:
- Push to `main` branch
- Pull requests to `main` branch

### Manual Execution with Test Group Selection
Run specific test groups without pushing code:

1. Go to: **Actions** → **Bookstore API Automation Tests**
2. Click **Run workflow** button (top right)
3. Select test group from dropdown:
   - `all` - All 33 tests (default)
   - `positive` - 8 positive tests
   - `negative` - 25 negative tests
   - `smoke` - 2 smoke tests
4. Click **Run workflow**

### View Test Reports
Latest Allure report: https://bakcan.github.io/api_automation_assignment/

