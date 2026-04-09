# 🧪 Amazon Purchase Flow Test (Playwright + TestNG)

## 📌 Overview

This project automates an **end-to-end Amazon purchase flow** using:

* **Playwright (Java)** for browser automation
* **TestNG** for test execution
* **Maven** for build and dependency management


## 🧰 Tech Stack

* Java
* Playwright
* TestNG
* Maven

---

## 📂 Project Structure

```
src/test/java/org/example/
    └── AmazonPurchaseFlowTest.java
pom.xml
```

---

## ▶️ How to Run the Test

### ✅ Prerequisites

* Java (JDK 8 or above)
* Maven installed
* Internet connection

---

### 🔹 Step 1: Install Dependencies

```
mvn clean install
```

---

### 🔹 Step 2: Run Tests

```
mvn test
```

---

## ⚙️ Key Features

### ✔ Auto Wait Handling

Uses Playwright's built-in waiting mechanisms:

* `waitForSelector`
* `waitForLoadState`

---

### ✔ Price Validation Logic

* Removes unwanted characters (`₹`, `,`, etc.)
* Uses `BigDecimal` for accurate price handling

---

### ✔ Multi-Tab Handling

Uses:

```java
page.waitForPopup()
```

---

### ✔ Assertions

* Cart subtotal validation
* Product visibility
* Quantity validation

---

## 🧹 Cleanup

Browser is closed after test execution using:

```java
@AfterTest
```

---

## 📌 Author

Akash Negi

---

## 💡 Notes

* Test runs in **headed mode** (`headless = false`)
* Make sure browser launches properly before execution

