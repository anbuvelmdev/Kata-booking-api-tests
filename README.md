# 🏨 Hotel Booking API Automation Framework

[![Java](https://img.shields.io/badge/Java-17-blue)](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
[![Cucumber](https://img.shields.io/badge/Cucumber-BDD-green)](https://cucumber.io/)
[![Rest-Assured](https://img.shields.io/badge/Rest--Assured-API-blue)](https://rest-assured.io/)
[![Maven](https://img.shields.io/badge/Maven-Build-red)](https://maven.apache.org/)

---

## 📖 Project Overview

This framework is designed for **automated API testing** of the Hotel Booking application available at [https://automationintesting.online/](https://automationintesting.online/).  

It uses:

- **Java 17** as the programming language  
- **Cucumber** for BDD-style feature files  
- **Rest-Assured** for REST API automation  
- **JUnit 5** and **Cucumber JUnit Engine** for test execution  
- **Jackson** for JSON serialization/deserialization  
- **Lombok** for cleaner POJOs  
- **SLF4J + Logback** for logging  

The framework is **modular, maintainable, and easily extendable** with centralized configurations and utilities.

---

## 📂 Project Structure

```markdown
API_Testing_Kata/
├── 📂 src/
│   ├── 📂 main/java/com/booking/
│   │   ├── 📂 config/
│   │   ├── 📂 constants/
│   │   ├── 📂 pojo/
│   │   └── 📂 utils/
│   └── 📂 test/java/com/booking/
│       ├── 📂 context/
│       ├── 📂 constants/
│       ├── 📂 stepdefinitions/
│       └── 📂 hooks/
│   │   └── 📂 utils/
├── 📂 src/test/resources/
│   ├── 📂 config/
│   ├── 📂 features/
│   ├── 📂 spec/
│   └── 📂 testdata/
├── ⚙️ pom.xml
└── 📄 README.md
```

## ⚙️ Prerequisites
- **Java 17** or higher  
- **Maven 3.8+**  
- IDE: IntelliJ IDEA (recommended) or Eclipse  
- Optional: Postman for manual API verification

## 🛠️ Installation

### 1. Clone the repository:  
```bash
git clone https://github.com/your-username/API_Testing_Kata.git
cd API_Testing_Kata
```

### 2. Build the project:
```bash
mvn clean install
```

### 3. Run tests using Maven:
```bash
mvn test
```

### 4. Run a single feature file:
```bash
mvn test -Dcucumber.features="src/test/resources/features/auth.feature"
```

### 5. Run specific tagged tests:
```bash
mvn test -Dcucumber.filter.tags="@auth"
```

## 🧪 Running Tests
### Run all feature files:
```bash
mvn test
```

### Run a specific feature file (using tag):
```bash
mvn test -Dcucumber.filter.tags="@positive"
```

### Run tests from IntelliJ:
```
Right-click on the TestRunner class → Run → Run TestRunner.
```

## 🔐 Dummy Token Configuration
If authentication context isn’t available, the framework uses a dummy token fallback.

You can define it in your .env or config.properties:
```
DUMMY_TOKEN=dummy-token-123456
```
Or it will automatically use:
```
String token = "dummy-token-123456";
``` 

## 🧾 Test Data Management
All test data is consolidated in booking_test_data.json under /src/test/resources/testdata/.

Each key in the JSON corresponds to a test case:
```json
{
  "validBooking": {
    "firstname": "John",
    "lastname": "Doe",
    "email": "john@example.com"
  },
  "invalidBooking": {
    "firstname": "",
    "lastname": "Smith",
    "email": "invalid"
  }
}
```

## 📝 Features & Scenarios

### Booking API

- Create booking (with and without authentication)
- Update booking
- Delete booking
- Get booking by ID

### Auth API
- Login with valid credentials
- Handle authentication tokens

## 🔧 Key Framework Features
### Reusable TestContext
- Stores auth tokens, booking IDs, responses to share across step definitions

### Centralized Config & Constants
- ConfigReader reads from config.properties
- Global constants for keys, headers, endpoints

### POJO-based JSON mapping
- Jackson maps request/response JSON to strongly typed Java objects

### Low Cyclomatic Complexity
- Utility methods (e.g., JsonUtils) handle exception internally
- Single-responsibility methods

### Logging & Reporting
- SLF4J + Logback logs all API requests/responses
- Cucumber HTML + JSON reports

### Tag-based Execution
- Use @requiresAuth, @positive, @negative for flexible test selection

## ⚡ Example Usage
```
// Step Definition example
@Given("login using username {string} and password {string}")
public void login(String username, String password) {
    Response res = ApiUtils.authLogin("/auth/login",
                    Map.of(AuthConstants.USERNAME, username,
                           AuthConstants.PASSWORD, password));
    context.setAuthToken(res.jsonPath().getString(AuthConstants.TOKEN));
}
```

## Config & Test Data
```
- src/test/resources/config.properties – All configurable endpoints, credentials, and constants
- src/test/resources/testdata/ – JSON files for bookings, auth, and negative test scenarios
```