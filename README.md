# DemoQA Web Automation Framework

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Selenium](https://img.shields.io/badge/Selenium-4.27.0-green.svg)](https://www.selenium.dev/)
[![JUnit](https://img.shields.io/badge/JUnit-5.10.1-blue.svg)](https://junit.org/junit5/)
[![Maven](https://img.shields.io/badge/Maven-3.8+-red.svg)](https://maven.apache.org/)

A comprehensive, enterprise-grade Selenium WebDriver automation framework built with Java, following international coding standards, Page Object Model (POM) pattern, and Object-Oriented Programming (OOP) principles.

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Project Structure](#project-structure)
- [Setup and Installation](#setup-and-installation)
- [Coding Standards](#coding-standards)
- [Architecture and Design Patterns](#architecture-and-design-patterns)
- [Writing Tests](#writing-tests)
- [Running Tests](#running-tests)
- [Configuration](#configuration)
- [Best Practices](#best-practices)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)
- [License](#license)

## 🎯 Overview

This framework is designed to provide a robust, maintainable, and scalable solution for web automation testing. It adheres to international coding standards including:

- **Java Code Conventions** (Oracle Java Code Conventions)
- **SOLID Principles** (Single Responsibility, Open/Closed, Liskov Substitution, Interface Segregation, Dependency Inversion)
- **Page Object Model (POM)** pattern
- **Design Patterns** (Builder, Factory, Singleton)
- **Clean Code** principles
- **Test-Driven Development (TDD)** practices

## ✨ Features

- ✅ **Page Object Model (POM)** - Maintainable and reusable page objects
- ✅ **Fluent Interface** - Readable and chainable method calls
- ✅ **Builder Pattern** - Flexible test data creation
- ✅ **Factory Pattern** - Centralized test data management
- ✅ **Comprehensive Logging** - Log4j2 integration with structured logging
- ✅ **Multi-Browser Support** - Chrome, Firefox, Edge
- ✅ **Configuration Management** - Environment-based configuration via `.env` and JSON
- ✅ **Parallel Execution** - Maven Surefire parallel test execution
- ✅ **Test Reporting** - HTML test reports generation
- ✅ **Exception Handling** - Robust error handling and reporting

## 📦 Prerequisites

Before you begin, ensure you have the following installed:

- **Java Development Kit (JDK)** 17 or higher
  - Download from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.org/)
  - Verify installation: `java -version`

- **Apache Maven** 3.8.0 or higher
  - Download from [Maven](https://maven.apache.org/download.cgi)
  - Verify installation: `mvn -version`

- **IDE** (Recommended: IntelliJ IDEA, Eclipse, or VS Code)
  - IntelliJ IDEA: [Download](https://www.jetbrains.com/idea/download/)
  - Eclipse: [Download](https://www.eclipse.org/downloads/)

- **Web Browsers**
  - Google Chrome (latest version)
  - Mozilla Firefox (latest version)
  - Microsoft Edge (latest version)

- **WebDriver Drivers** (Automatically managed by Selenium 4.x)
  - ChromeDriver (auto-downloaded)
  - GeckoDriver (auto-downloaded)
  - EdgeDriver (auto-downloaded)

## 📁 Project Structure

```
DemoQAWed/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── core/                    # Core framework classes
│   │   │   │   ├── BaseTest.java        # Base test class with setup/teardown
│   │   │   │   ├── Basepage.java        # Base page class with common methods
│   │   │   │   ├── DriverManager.java   # WebDriver lifecycle management
│   │   │   │   ├── TestSettings.java    # Configuration management
│   │   │   │   └── AutoScrollListener.java
│   │   │   ├── models/                  # Data model classes (POJOs)
│   │   │   │   └── TextboxFormData.java # Form data model with Builder pattern
│   │   │   ├── pages/                   # Page Object Model classes
│   │   │   │   └── demoQA/
│   │   │   │       └── Elements/
│   │   │   │           └── TextboxPage.java
│   │   │   ├── util/                    # Utility classes
│   │   │   │   ├── Helper.java          # Helper methods
│   │   │   │   └── Constants.java       # Project constants
│   │   │   ├── enums/                   # Enumeration classes
│   │   │   ├── steps/                   # Step definitions (if using BDD)
│   │   │   └── types/                   # Custom type definitions
│   │   └── resources/
│   │       ├── TestData.json            # Test data configuration
│   │       └── log4j2.xml               # Logging configuration
│   └── test/
│       ├── java/
│       │   ├── data/                    # Test data factories
│       │   │   └── TestDataFactory.java # Factory for creating test data
│       │   └── Elements/                # Test classes
│       │       └── TextboxTest.java    # Test implementation
│       └── resources/
│           └── junit-platform.properties
├── target/                              # Build output directory
│   ├── classes/                         # Compiled classes
│   ├── test-classes/                    # Compiled test classes
│   ├── logs/                            # Log files
│   └── reports/                         # Test reports
├── pom.xml                              # Maven project configuration
├── .gitignore                           # Git ignore rules
└── README.md                            # This file
```

## 🚀 Setup and Installation

### 1. Clone the Repository

```bash
git clone <repository-url>
cd DemoQAWed
```

### 2. Configure Environment Variables

Create a `.env` file in the project root (optional):

```env
TEST_ENV=DEMOQA
BROWSER=chrome
HEADLESS=false
SCREEN_RESOLUTION=1920,1080
```

### 3. Verify Test Data Configuration

Ensure `src/main/resources/TestData.json` contains your environment configuration:

```json
{
  "DEMOQA": {
    "base_url": "https://demoqa.com/"
  }
}
```

### 4. Build the Project

```bash
mvn clean install
```

### 5. Verify Setup

Run a sample test to verify everything is configured correctly:

```bash
mvn test -Dtest=TextboxTest#verifyValidData
```

## 📐 Coding Standards

This project adheres to international coding standards and best practices.

### Java Code Conventions

#### Naming Conventions

- **Classes**: PascalCase (e.g., `TextboxPage`, `TextboxFormData`)
- **Methods**: camelCase (e.g., `enterFullName()`, `verifyOutputEmail()`)
- **Variables**: camelCase (e.g., `testData`, `textboxPage`)
- **Constants**: UPPER_SNAKE_CASE (e.g., `BASE_URL`, `WAIT_ELEMENT`)
- **Packages**: lowercase (e.g., `pages.demoQA.elements`)

#### Code Formatting

- **Indentation**: 4 spaces (no tabs)
- **Line Length**: Maximum 120 characters
- **Braces**: Opening brace on the same line (K&R style)
- **Blank Lines**: One blank line between methods, two between sections

#### Example:

```java
public class TextboxPage extends Basepage {
    
    private static final class Selectors {
        static final By FULL_NAME_INPUT = By.id("userName");
        // ...
    }
    
    public TextboxPage enterFullName(String fullName) {
        logger.info("Entering Full Name: {}", fullName);
        enterText(Selectors.FULL_NAME_INPUT, fullName);
        return this;
    }
}
```

### Documentation Standards

All public classes and methods must include JavaDoc comments:

```java
/**
 * Page Object Model for the DemoQA Textbox page.
 * Implements the Page Object Model pattern following Selenium best practices.
 * 
 * @author Automation Team
 */
public class TextboxPage extends Basepage {
    
    /**
     * Enters the full name into the full name input field.
     * 
     * @param fullName The full name to enter
     * @return This page instance for method chaining (fluent interface)
     */
    public TextboxPage enterFullName(String fullName) {
        // Implementation
    }
}
```

### Access Modifiers

- **Public**: Only for classes and methods that need external access
- **Protected**: For methods used by subclasses
- **Private**: For internal implementation details
- **Package-private**: Only when necessary for internal package communication

## 🏗️ Architecture and Design Patterns

### Page Object Model (POM)

The framework follows the Page Object Model pattern, where each web page is represented by a Java class.

#### Page Object Structure

```java
public class TextboxPage extends Basepage {
    
    // 1. Private static inner class for selectors (Encapsulation)
    private static final class Selectors {
        static final By FULL_NAME_INPUT = By.id("userName");
        // ...
    }
    
    // 2. Constructor
    public TextboxPage() {
        super();
        openSite(DEMOQA_TEXTBOX_URL);
    }
    
    // 3. Action methods (return this for fluent interface)
    public TextboxPage enterFullName(String fullName) {
        // Implementation
        return this;
    }
    
    // 4. Verification methods
    public TextboxPage verifyOutputName(String expectedValue) {
        // Implementation
        return this;
    }
    
    // 5. Combined methods for efficiency
    public TextboxPage fillForm(TextboxFormData formData) {
        // Implementation
        return this;
    }
}
```

#### POM Best Practices

1. **Encapsulation**: All locators must be in a private static inner class
2. **Fluent Interface**: Action methods return `this` for method chaining
3. **Single Responsibility**: Each page object represents one page
4. **Reusability**: Common methods in `Basepage` class
5. **No Assertions in Page Objects**: Assertions belong in test classes

### Object-Oriented Programming Principles

#### SOLID Principles

1. **Single Responsibility Principle (SRP)**
   - Each class has one reason to change
   - Example: `TextboxPage` only handles textbox page interactions

2. **Open/Closed Principle (OCP)**
   - Open for extension, closed for modification
   - Example: Extend `Basepage` instead of modifying it

3. **Liskov Substitution Principle (LSP)**
   - Subtypes must be substitutable for their base types
   - Example: All page classes extend `Basepage` and can be used interchangeably

4. **Interface Segregation Principle (ISP)**
   - Clients should not depend on methods they don't use
   - Example: Separate interfaces for different page functionalities

5. **Dependency Inversion Principle (DIP)**
   - Depend on abstractions, not concretions
   - Example: Use `WebDriver` interface, not specific implementations

### Design Patterns

#### 1. Builder Pattern

Used for creating complex objects (test data):

```java
TextboxFormData data = TextboxFormData.builder()
    .withFullName("John Doe")
    .withEmail("john.doe@example.com")
    .withCurrentAddress("123 Main St")
    .withPermanentAddress("456 Oak Ave")
    .build();
```

#### 2. Factory Pattern

Used for creating test data objects:

```java
TextboxFormData data = TestDataFactory.createValidTextboxData();
```

#### 3. Singleton Pattern

Used for WebDriver management (via `DriverManager`):

```java
WebDriver driver = DriverManager.getDriver();
```

## ✍️ Writing Tests

### Test Class Structure

```java
@DisplayName("Textbox Page Tests")
public class TextboxTest extends BaseTest {
    
    private TextboxPage textboxPage;
    private TextboxFormData testData;
    
    @BeforeEach
    public void setUpPages() {
        textboxPage = new TextboxPage();
        testData = TestDataFactory.createValidTextboxData();
    }
    
    @Test
    @DisplayName("Verify form submission with valid data")
    public void verifyValidData() {
        textboxPage
            .fillForm(testData)
            .submitForm()
            .verifyOutputContainerVisible()
            .verifyAllOutputFields(testData);
    }
}
```

### Test Method Guidelines

1. **Naming**: Use descriptive names (e.g., `verifyFormSubmissionWithValidData`)
2. **Single Responsibility**: Each test should verify one scenario
3. **Arrange-Act-Assert (AAA)**: Structure tests clearly
4. **Independence**: Tests should not depend on each other
5. **Idempotency**: Tests should produce the same results when run multiple times

### Test Data Management

#### Using Model Classes

```java
// Create test data using Builder pattern
TextboxFormData data = TextboxFormData.builder()
    .withFullName("John Doe")
    .withEmail("john.doe@example.com")
    .build();
```

#### Using Factory Pattern

```java
// Use factory methods for common test scenarios
TextboxFormData validData = TestDataFactory.createValidTextboxData();
TextboxFormData invalidData = TestDataFactory.createInvalidEmailData();
```

### Fluent Interface Usage

```java
// Chain methods for readable test flow
textboxPage
    .enterFullName("John Doe")
    .enterEmail("john@example.com")
    .enterCurrentAddress("123 Main St")
    .enterPermanentAddress("456 Oak Ave")
    .submitForm()
    .verifyOutputContainerVisible()
    .verifyOutputName("John Doe");
```

## 🏃 Running Tests

### Run All Tests

```bash
mvn test
```

### Run Specific Test Class

```bash
mvn test -Dtest=TextboxTest
```

### Run Specific Test Method

```bash
mvn test -Dtest=TextboxTest#verifyValidData
```

### Run Tests with Custom Configuration

```bash
# Run with specific browser
mvn test -Dbrowser=firefox

# Run in headless mode
mvn test -Dheadless=true

# Run with specific environment
mvn test -Denv=DEMOQA

# Combine multiple options
mvn test -Dbrowser=chrome -Dheadless=true -Denv=DEMOQA
```

### Generate Test Reports

```bash
# Generate HTML reports
mvn surefire-report:report

# View reports
# Open: target/site/surefire-report.html
```

### Parallel Execution

Tests run in parallel by default (configured in `pom.xml`):

```xml
<forkCount>3</forkCount>
<reuseForks>true</reuseForks>
```

## ⚙️ Configuration

### Environment Configuration

Configuration priority (highest to lowest):
1. System Properties (`-Dproperty=value`)
2. `.env` file
3. `TestData.json` defaults

### System Properties

| Property | Description | Example |
|----------|-------------|---------|
| `env` | Test environment | `-Denv=DEMOQA` |
| `browser` | Browser type | `-Dbrowser=chrome` |
| `headless` | Headless mode | `-Dheadless=true` |
| `resolution` | Screen resolution | `-Dresolution=1920,1080` |

### Browser Configuration

Supported browsers:
- `chrome` (default)
- `firefox`
- `edge`

### Logging Configuration

Logging is configured in `src/main/resources/log4j2.xml`. Logs are written to:
- `target/logs/automation.log` - General automation logs
- `target/logs/test-execution.log` - Test execution logs
- `target/logs/errors.log` - Error logs

## 💡 Best Practices

### Page Object Model

1. ✅ **DO**: Keep locators in a private static inner class
2. ✅ **DO**: Use ID-based selectors when possible (more stable)
3. ✅ **DO**: Implement fluent interface for method chaining
4. ✅ **DO**: Separate action methods from verification methods
5. ❌ **DON'T**: Include assertions in page objects
6. ❌ **DON'T**: Hardcode test data in page objects

### Test Design

1. ✅ **DO**: Write independent, isolated tests
2. ✅ **DO**: Use descriptive test method names
3. ✅ **DO**: Follow AAA pattern (Arrange-Act-Assert)
4. ✅ **DO**: Use `@DisplayName` for better test reporting
5. ❌ **DON'T**: Share state between tests
6. ❌ **DON'T**: Write tests that depend on execution order

### Code Quality

1. ✅ **DO**: Write self-documenting code
2. ✅ **DO**: Add JavaDoc comments for public APIs
3. ✅ **DO**: Follow single responsibility principle
4. ✅ **DO**: Use meaningful variable and method names
5. ❌ **DON'T**: Use magic numbers or strings
6. ❌ **DON'T**: Write methods longer than 20 lines

### Error Handling

1. ✅ **DO**: Use explicit waits instead of implicit waits
2. ✅ **DO**: Handle exceptions appropriately
3. ✅ **DO**: Log errors with context
4. ❌ **DON'T**: Use Thread.sleep() for synchronization
5. ❌ **DON'T**: Ignore exceptions silently

## 🔧 Troubleshooting

### Common Issues

#### Issue: WebDriver not found

**Solution**: Ensure WebDriver drivers are in PATH or use Selenium 4.x (auto-downloads drivers)

#### Issue: Tests fail with timeout

**Solution**: Increase wait timeouts in `TestSettings.java` or check element selectors

#### Issue: Tests fail in headless mode

**Solution**: Some applications may behave differently in headless mode. Test in both modes.

#### Issue: Parallel execution conflicts

**Solution**: Ensure tests are independent and don't share state

### Debug Mode

Enable debug logging by modifying `log4j2.xml`:

```xml
<Logger name="pages" level="DEBUG"/>
```

## 🤝 Contributing

### Contribution Guidelines

1. **Fork the repository**
2. **Create a feature branch**: `git checkout -b feature/your-feature-name`
3. **Follow coding standards**: Adhere to the coding standards outlined in this README
4. **Write tests**: Ensure all new features have corresponding tests
5. **Update documentation**: Update README or relevant documentation
6. **Commit changes**: Use descriptive commit messages
7. **Push to branch**: `git push origin feature/your-feature-name`
8. **Create Pull Request**: Provide a clear description of changes

### Code Review Checklist

- [ ] Code follows Java coding conventions
- [ ] All public methods have JavaDoc comments
- [ ] Tests are written and passing
- [ ] No hardcoded values
- [ ] Proper error handling
- [ ] Logging is appropriate
- [ ] Code is properly formatted

### Commit Message Format

```
<type>: <subject>

<body>

<footer>
```

Types: `feat`, `fix`, `docs`, `style`, `refactor`, `test`, `chore`

Example:
```
feat: Add fluent interface to TextboxPage

- Implement method chaining for better readability
- Add combined methods for form filling and verification
- Update tests to use fluent interface

Closes #123
```

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 📞 Support

For questions, issues, or contributions, please:
- Open an issue on GitHub
- Contact the automation team
- Refer to the project documentation

## 🙏 Acknowledgments

- Selenium WebDriver team
- JUnit 5 team
- Apache Maven team
- All contributors to this project

---

**Last Updated**: 2024
**Version**: 1.0-SNAPSHOT
**Maintainer**: Automation Team

