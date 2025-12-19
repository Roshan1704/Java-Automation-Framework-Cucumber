# Testing Guide

## Table of Contents
1. [Getting Started](#getting-started)
2. [Running Tests](#running-tests)
3. [Writing Tests](#writing-tests)
4. [Best Practices](#best-practices)
5. [Troubleshooting](#troubleshooting)

## Getting Started

### Prerequisites
- Java 11 or higher
- Maven 3.6+
- Chrome/Firefox browser
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

### Initial Setup
```bash
# Clone repository
git clone <repository-url>

# Navigate to project
cd ui-automation-framework

# Install dependencies
mvn clean install -DskipTests

# Run sample test
mvn test -Dtest=LoginTest
```

## Running Tests

### Execute All Tests
```bash
mvn clean test
```

### Run Specific Test Suite
```bash
mvn test -DsuiteXmlFile=testng-smoke.xml
```

### Run Tests by Group
```bash
mvn test -Dgroups=smoke
mvn test -Dgroups=regression
mvn test -Dgroups="smoke,api"
```

### Run Tests on Different Browser
```bash
mvn test -Dbrowser=chrome
mvn test -Dbrowser=firefox
mvn test -Dbrowser=edge
```

### Run Tests in Headless Mode
Update config.properties:
```properties
headless=true
