# End-to-End Test Automation Framework  
*AutomationExercise Platform*

This repository contains a comprehensive End-to-End Test Automation Framework developed as a final group project.  
It covers both *UI (Frontend)* and *API (Backend)* automation for the Automation Exercise platform.

---

## 🔗 Target Application
- *Frontend (UI):* https://automationexercise.com  
- *UI Test Cases Reference:* https://automationexercise.com/test_cases  
- *Backend (API):* https://automationexercise.com/api_list  

---

## 🛠 Automation Stack (Mandatory)
- Java  
- Selenium WebDriver  
- TestNG  
- RestAssured  
- Maven  
- Allure Reporting  
- Page Object Model (POM)  

---

## 🧱 Framework Design
- Page Object Model (POM) is used for UI tests.  
- UI and API tests are clearly separated.  
- Supports parallel execution using TestNG.  
- Uses *explicit waits only* (no hard-coded sleeps).  
- Test data utilities for dynamic data (e.g., unique email generation).  

---
```
## 📂 Project Structure
├── .allure
├── .mvn
├── allure-results
├── src
│ └── test
│ └── java
│ ├── api
│ │ ├── client
│ │ └── test
│ ├── core
│ │ ├── base
│ │ ├── config
│ │ ├── driver
│ │ ├── listeners
│ │ └── utils
│ └── ui
│ ├── pages
│ └── tests
├── resources
├── target
├── pom.xml
└── mvnw / mvnw.cmd
```
---

## 👥 Group Members & Test Case Ownership

### ✅ UI Test Cases

| Member  | Test Case ID | Test Case Name |
|--------|--------------|----------------|
| *Anzori* | TC01 | Register User |
|          | TC04 | Logout User |
|          | TC05 | Register User with existing email |
| *Nini* | TC09 | Search Product |
|          | TC12 | Add Products in Cart |
|          | TC16 | Place Order: Login before Checkout |
| *Ani*  | TC21 | Add review on product |
|          | TC24 | Download Invoice after purchase order |
| **Dachi**| TC03 | Login User with incorrect email and password |
|          | TC06 | Contact Us Form |
| **Leqso**| TC10  | Verify Subscription in home page |
|          | TC18  | View Category Products |

---

### ✅ API Test Cases

| Member  | Test Case ID | Test Case Name |
|--------|--------------|----------------|
| *Nini* | API TC05 | POST To Search Product |
|          | API TC14 | PUT Method To Update User Account |
| *Ani*  | API TC03 | Get All Brands List |
|          | API TC11 | POST To Create/Register User Account |
| *Anzori* | API TC01 | Get All Products List |
|           | API TC02 | POST To All Products List |
| *Dachi* | API TC12 | DELETE Method To Delete User Account |
|           | API TC09 | DELETE To Verify Login |
| *Leqso* | API TC08 | POST To Verify Login without email parameter |
|         | API TC13 | PUT METHOD To Update User Account |

---

## ⚙️ Framework Design

- Page Object Model (POM) is used for UI tests  
- UI and API tests are clearly separated  
- Supports parallel execution with TestNG  
- Uses *explicit waits only* (no hard-coded sleeps)  
- Centralized WebDriver and API configuration  
- Reusable utilities and base test classes  

---

## 📊 Reporting (Allure)

All test executions generate *Allure Reports* including:

- ✔️ Test steps  
- 📸 Screenshots for failed UI tests  
- 📦 API request & response attachments  

---

## ▶️ How to Run Tests

### 1️⃣ Run all tests
```bash
mvn clean test
