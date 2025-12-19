# Java UI Test Automation Framework

A comprehensive Selenium WebDriver-based test automation framework using TestNG, Cucumber BDD, and Maven.

## Framework Architecture

This framework follows industry best practices and includes:

- **Page Object Model (POM)** design pattern
- **Cucumber BDD** for behavior-driven testing
- **TestNG** for test management and parallel execution
- **RestAssured** for API testing (hybrid framework ready)
- **Maven** for dependency and build management
- **Log4j2** for logging
- **ExtentReports** for detailed reporting
- **WebDriverManager** for automatic driver management

## Project Structure

```
ui-automation-framework/
│
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/automation/
│   │           ├── base/
│   │           │   └── BasePage.java
│   │           ├── driver/
│   │           │   └── DriverManager.java
│   │           ├── pages/
│   │           │   └── LoginPage.java
│   │           └── utils/
│   │               ├── ConfigReader.java
│   │               ├── LoggerUtil.java
│   │               ├── ScreenshotUtil.java
│   │               └── WaitUtils.java
│   │
│   └── test/
│       ├── java/
│       │   └── com/automation/
│       │       ├── base/
│       │       │   └── BaseTest.java
│       │       ├── listeners/
│       │       │   └── TestListener.java
│       │       ├── runners/
│       │       │   └── TestNGCucumberRunner.java
│       │       ├── stepdefinitions/
│       │       │   ├── Hooks.java
│       │       │   └── LoginSteps.java
│       │       └── tests/
│       │           ├── api/
│       │           │   └── APITest.java
│       │           └── ui/
│       │               └── LoginTest.java
│       │
│       └── resources/
│           ├── config/
│           │   └── config.properties
│           ├── features/
│           │   └── Login.feature
│           └── log4j2.xml
│
├── test-output/
│   ├── reports/
│   └── screenshots/
│
├── logs/
│
├── pom.xml
├── testng.xml
└── README.md
```

## Prerequisites

- Java JDK 11 or higher
- Maven 3.6 or higher
- Chrome/Firefox browser installed

## Installation & Setup

1. Clone the repository
2. Navigate to project directory
3. Install dependencies:
   ```bash
   mvn clean install -DskipTests
   ```

## Running Tests

### Run all tests via TestNG XML:
```bash
mvn clean test
```

### Run specific test tags:
```bash
mvn clean test -Dcucumber.filter.tags="@smoke"
```

### Run tests on specific browser:
```bash
mvn clean test -Dbrowser=chrome
```

### Run tests in headless mode:
Update `config.properties`: `headless=true`

### Run specific test class:
```bash
mvn test -Dtest=LoginTest
```

## Configuration

Update `src/test/resources/config/config.properties` to customize:

- Browser settings (chrome, firefox, edge)
- Base URLs for application and API
- Timeout values
- Reporting paths
- Parallel execution settings

## Features

### Cross-Browser Support
- Chrome
- Firefox
- Edge
- Configurable headless mode

### Parallel Execution
Configured in `testng.xml` with thread-count parameter

### Reporting
- ExtentReports for detailed HTML reports
- Cucumber JSON reports
- TestNG reports
- Screenshots on test failure

### Logging
- Log4j2 configuration
- Console and file logging
- Automatic log rotation

### Waits
- Explicit waits utility
- Configurable timeout values
- No hard-coded Thread.sleep()

### BDD Support
- Cucumber feature files
- Step definitions
- Background scenarios
- Data-driven testing with Examples

### API Testing
- RestAssured integration
- Sample API test included
- Hybrid UI + API framework ready

## Best Practices Implemented

1. Page Object Model for maintainability
2. Singleton pattern for utilities
3. ThreadLocal for parallel execution safety
4. Explicit waits instead of implicit waits
5. Centralized configuration management
6. Comprehensive logging
7. Screenshot capture on failures
8. Reusable utility methods
9. Proper exception handling
10. Clean separation of concerns

## CI/CD Integration

This framework can be easily integrated with:
- Jenkins
- GitHub Actions
- GitLab CI
- Azure DevOps

Sample Jenkins command:
```bash
mvn clean test -Dbrowser=chrome -Dcucumber.filter.tags="@smoke"
```

## Adding New Tests

### To add a new page:
1. Create page class in `src/main/java/com/automation/pages/`
2. Extend `BasePage`
3. Use `@FindBy` annotations for elements

### To add a new test:
1. Create test class in `src/test/java/com/automation/tests/`
2. Extend `BaseTest`
3. Use page objects to interact with application

### To add a new Cucumber scenario:
1. Create/update feature file in `src/test/resources/features/`
2. Create corresponding step definitions in `src/test/java/com/automation/stepdefinitions/`

## Reporting

After test execution, reports are generated in:
- `test-output/reports/ExtentReport.html` - ExtentReports
- `test-output/reports/cucumber-reports.html` - Cucumber HTML
- `test-output/testng-results.xml` - TestNG XML
- `test-output/screenshots/` - Failure screenshots

## Support

For issues or questions, please check the logs in the `logs/` directory.

## License

This framework is provided as-is for automation testing purposes.
