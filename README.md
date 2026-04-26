# Selenium Tests in CI/CD Pipeline

![Build Status](https://github.com/Varadha9/selenium-cicd-pipeline/actions/workflows/selenium-ci.yml/badge.svg)
![Java](https://img.shields.io/badge/Java-11-orange)
![Selenium](https://img.shields.io/badge/Selenium-4.18.1-green)
![JUnit](https://img.shields.io/badge/JUnit-5.10.2-blue)
![Maven](https://img.shields.io/badge/Maven-3.x-red)
![License](https://img.shields.io/badge/License-MIT-yellow)

A complete **Java + Selenium WebDriver** automated browser testing project integrated into a **GitHub Actions CI/CD pipeline**. The browser opens visibly during local execution — exactly like running tests in IntelliJ IDEA — while the pipeline runs tests automatically on every code push using headless Chrome on Ubuntu.

---

## Table of Contents

- [Overview](#overview)
- [What is Selenium?](#what-is-selenium)
- [What is CI/CD?](#what-is-cicd)
- [Why Integrate Selenium with CI/CD?](#why-integrate-selenium-with-cicd)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [How to Run Tests](#how-to-run-tests)
- [Test Coverage](#test-coverage)
- [Detailed Test Descriptions](#detailed-test-descriptions)
- [GitHub Actions CI/CD Pipeline](#github-actions-cicd-pipeline)
- [Pipeline Flow](#pipeline-flow)
- [Selenium Commands Reference](#selenium-commands-reference)
- [Troubleshooting](#troubleshooting)
- [Project Repository](#project-repository)

---

## Overview

This project demonstrates how to build a **production-ready Selenium automation framework** in Java and integrate it into a **CI/CD pipeline** using GitHub Actions. Every time code is pushed to the `main` branch, the pipeline automatically:

1. Sets up a clean Ubuntu environment
2. Installs Java 11 and Google Chrome
3. Runs all 9 Selenium test methods
4. Reports pass/fail results directly on GitHub

This is the industry-standard approach used by QA Engineers and DevOps teams to ensure web application quality on every code change.

---

## What is Selenium?

**Selenium WebDriver** is an open-source browser automation framework that allows you to control web browsers programmatically. It is the most widely used tool for automated web testing in the industry.

**Key capabilities:**
- Open URLs and navigate between pages
- Find and interact with HTML elements (click, type, select)
- Handle browser windows, tabs, alerts, and frames
- Execute JavaScript directly in the browser
- Take screenshots for test evidence
- Manage cookies and browser sessions
- Simulate keyboard and mouse actions

**Supported browsers:** Chrome, Firefox, Edge, Safari  
**Supported languages:** Java, Python, C#, JavaScript, Ruby

---

## What is CI/CD?

**CI/CD** stands for **Continuous Integration / Continuous Deployment**. It is a software engineering practice where every code change is automatically built, tested, and optionally deployed using automated pipelines.

| Term | Meaning |
|------|---------|
| **Continuous Integration (CI)** | Every push triggers automated build and test |
| **Continuous Deployment (CD)** | Passing builds are automatically deployed |
| **GitHub Actions** | The CI/CD platform used in this project |
| **Pipeline** | A series of automated steps that run on every commit |

**Why it matters:** Without CI/CD, developers manually run tests before merging — which is slow, inconsistent, and error-prone. With CI/CD, tests run automatically in a clean environment on every single commit.

---

## Why Integrate Selenium with CI/CD?

| Benefit | Description |
|---------|-------------|
| **Instant Feedback** | Developers know within minutes if their change broke something |
| **Consistency** | Tests run in the same environment every time — no "works on my machine" |
| **Automation** | No manual test execution required after every commit |
| **Traceability** | Every test run is logged and linked to a specific Git commit |
| **Quality Gate** | Failing tests block merges to the main branch |
| **Parallel Execution** | Multiple test suites can run simultaneously in the cloud |
| **Cost Effective** | GitHub Actions provides free minutes for public repositories |

---

## Technology Stack

| Layer | Tool / Technology | Version | Purpose |
|-------|------------------|---------|---------|
| Language | Java | 11 | Primary programming language |
| Build Tool | Apache Maven | 3.x | Dependency management and build |
| Test Framework | JUnit 5 (Jupiter) | 5.10.2 | Test lifecycle and assertions |
| Browser Automation | Selenium WebDriver | 4.18.1 | Browser control and interaction |
| Driver Management | WebDriverManager | 5.7.0 | Auto-downloads ChromeDriver |
| Browser | Google Chrome | Latest | Browser under test |
| CI/CD Platform | GitHub Actions | - | Automated pipeline execution |
| Repository | GitHub | - | Source code hosting |
| OS (CI) | Ubuntu Latest | - | Cloud runner environment |

---

## Project Structure

```
selenium-cicd-pipeline/
│
├── src/
│   └── test/
│       └── java/
│           └── tests/
│               └── SeleniumCommandsTest.java   ← All 9 Selenium test methods
│
├── .github/
│   └── workflows/
│       └── selenium-ci.yml                     ← GitHub Actions CI/CD pipeline definition
│
├── target/                                     ← Maven build output (auto-generated, not in Git)
│   └── surefire-reports/                       ← Test execution reports
│
├── pom.xml                                     ← Maven project config & dependencies
├── .gitignore                                  ← Files excluded from Git
└── README.md                                   ← This file
```

**Key files explained:**

- **`SeleniumCommandsTest.java`** — Contains all 9 test methods covering every major Selenium command category. Each method is fully commented explaining what each command does and why it is used.
- **`selenium-ci.yml`** — Defines the GitHub Actions workflow. Triggers on push/PR to main, sets up Java + Chrome, and runs `mvn test`.
- **`pom.xml`** — Maven configuration file. Declares Selenium, WebDriverManager, and JUnit 5 as dependencies.
- **`.gitignore`** — Excludes `target/` and `.class` files from being committed to Git.

---

## Prerequisites

Before running this project locally, ensure the following are installed:

### 1. Java 11 or higher
```bash
java -version
# Expected: java version "11.x.x" or higher
```
Download: https://adoptium.net

### 2. Apache Maven
```bash
mvn -version
# Expected: Apache Maven 3.x.x
```
Download: https://maven.apache.org/download.cgi

### 3. Google Chrome Browser
Download: https://www.google.com/chrome

> **Note:** You do NOT need to manually download ChromeDriver. WebDriverManager handles this automatically by detecting your installed Chrome version and downloading the matching driver.

### 4. Git
```bash
git --version
# Expected: git version 2.x.x
```
Download: https://git-scm.com

---

## Installation & Setup

### Step 1 — Clone the Repository
```bash
git clone https://github.com/Varadha9/selenium-cicd-pipeline.git
cd selenium-cicd-pipeline
```

### Step 2 — Verify Java and Maven
```bash
java -version
mvn -version
```

### Step 3 — Download Dependencies
Maven downloads all dependencies automatically on first run. You can also pre-download them:
```bash
mvn dependency:resolve
```

---

## How to Run Tests

### Run All Tests Locally
```bash
mvn test
```

Chrome browser will open visibly, maximize to full screen, and run all 9 tests automatically. You will see the browser navigating to different websites in real time.

### Run a Specific Test Method
```bash
mvn test -Dtest=SeleniumCommandsTest#testBrowserNavigation
```

### Run with Detailed Output
```bash
mvn test -B
```

### Expected Console Output
```
-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running tests.SeleniumCommandsTest

✅ Browser & Navigation Commands passed
✅ Window Management Commands passed
✅ Element Interaction Commands passed
✅ Wait Commands passed
✅ Keyboard & Mouse Action Commands passed
✅ Cookies Management Commands passed
✅ JavaScript Execution Commands passed
✅ Screen Capture Commands passed
✅ Session & Timeout Commands passed

Tests run: 9, Failures: 0, Errors: 0, Skipped: 0
Time elapsed: ~65 seconds

BUILD SUCCESS
```

### View Test Reports
After running, Maven generates XML and TXT reports in:
```
target/surefire-reports/
```

---

## Test Coverage

| # | Test Method | Website Used | Selenium Commands Covered |
|---|-------------|-------------|--------------------------|
| 1 | `testBrowserNavigation` | example.com, wikipedia.org | `get()`, `getTitle()`, `getCurrentUrl()`, `navigate().to()`, `navigate().back()`, `navigate().forward()`, `navigate().refresh()` |
| 2 | `testWindowManagement` | example.com, wikipedia.org | `maximize()`, `setSize()`, `setPosition()`, `getSize()`, `getWindowHandle()`, `getWindowHandles()`, `switchTo().newWindow()`, `switchTo().window()`, `close()` |
| 3 | `testElementInteraction` | duckduckgo.com | `findElement(By.name())`, `findElement(By.cssSelector())`, `sendKeys()`, `clear()`, `getAttribute()`, `Keys.ENTER` |
| 4 | `testWaitCommands` | example.com | `implicitlyWait()`, `WebDriverWait`, `visibilityOfElementLocated()`, `elementToBeClickable()`, `titleIs()` |
| 5 | `testKeyboardMouseActions` | duckduckgo.com | `Actions`, `moveToElement()`, `click()`, `doubleClick()`, `keyDown()`, `keyUp()`, `sendKeys()`, `perform()` |
| 6 | `testCookiesManagement` | example.com | `addCookie()`, `getCookieNamed()`, `getCookies()`, `deleteCookieNamed()`, `deleteAllCookies()` |
| 7 | `testJavaScriptExecution` | example.com | `JavascriptExecutor`, `executeScript()`, scroll, highlight element, JS click |
| 8 | `testScreenCapture` | example.com | `TakesScreenshot`, `getScreenshotAs(BASE64)`, `getScreenshotAs(BYTES)` |
| 9 | `testSessionAndTimeouts` | example.com | `implicitlyWait()`, `pageLoadTimeout()`, `scriptTimeout()`, `getPageSource()`, `getWindowHandle()`, `getWindowHandles()` |

---

## Detailed Test Descriptions

### Test 1 — Browser & Navigation Commands
**Purpose:** Verify basic browser navigation — opening URLs, reading page info, and using browser history.

- Opens `example.com` and verifies the page title and URL
- Navigates to `wikipedia.org` using `navigate().to()`
- Goes back to `example.com` using `navigate().back()`
- Goes forward to `wikipedia.org` using `navigate().forward()`
- Reloads the page using `navigate().refresh()`

---

### Test 2 — Window Management Commands
**Purpose:** Demonstrate how to manage multiple browser windows and tabs.

- Maximizes the browser window
- Gets and verifies the window size
- Resizes the window to 1280x720
- Moves the window to position (0,0)
- Saves the parent window handle
- Opens a new tab with `switchTo().newWindow(WindowType.TAB)`
- Opens Wikipedia in the new tab
- Loops through all window handles to find the child tab
- Closes the child tab with `close()`
- Switches back to the parent window

---

### Test 3 — Element Interaction Commands
**Purpose:** Find elements on the page and interact with them.

- Opens DuckDuckGo (no consent popup — reliable search box)
- Waits for the search box to be visible
- Types "Selenium WebDriver" using `sendKeys()`
- Verifies the typed text using `getAttribute("value")`
- Clears the field using `clear()`
- Finds the same element using CSS selector
- Types "Java Selenium" and presses Enter
- Waits for and verifies the search results page title

---

### Test 4 — Wait Commands
**Purpose:** Demonstrate implicit and explicit waits for handling dynamic content.

- Uses `WebDriverWait` with `ExpectedConditions.visibilityOfElementLocated()` to wait for the `<h1>` heading
- Verifies the heading is displayed using `isDisplayed()`
- Uses `elementToBeClickable()` to wait for a link to be ready
- Uses `titleIs()` to wait for an exact page title match

---

### Test 5 — Keyboard & Mouse Action Commands
**Purpose:** Simulate advanced user interactions using the Actions API.

- Creates an `Actions` instance for advanced interactions
- Uses `moveToElement().click().perform()` to hover and click
- Types text using `actions.sendKeys()`
- Simulates Ctrl+A (select all) using `keyDown(CONTROL).sendKeys("a").keyUp(CONTROL)`
- Deletes selected text using `Keys.DELETE`
- Performs a double-click using `doubleClick()`

---

### Test 6 — Cookies Management Commands
**Purpose:** Add, retrieve, and delete browser cookies.

- Adds a cookie named "testCookie" with value "testValue"
- Retrieves the cookie by name and verifies its value
- Gets all cookies and verifies the list is not empty
- Deletes the specific cookie and verifies it is gone
- Deletes all cookies and verifies the cookie jar is empty

---

### Test 7 — JavaScript Execution Commands
**Purpose:** Execute JavaScript directly in the browser for interactions beyond standard Selenium.

- Casts WebDriver to `JavascriptExecutor`
- Gets the page title via `executeScript("return document.title")`
- Scrolls to the bottom of the page
- Scrolls back to the top
- Gets the current URL via JavaScript
- Highlights the `<h1>` element with a red border
- Clicks a link using JavaScript (bypasses normal click restrictions)

---

### Test 8 — Screen Capture Commands
**Purpose:** Take screenshots for test evidence and debugging.

- Casts WebDriver to `TakesScreenshot`
- Captures screenshot as Base64 string (for HTML reports / APIs)
- Captures screenshot as byte array (for file saving / image processing)
- Verifies both captures contain data

---

### Test 9 — Session & Timeout Commands
**Purpose:** Configure WebDriver session timeouts and verify session information.

- Sets `implicitlyWait` to 5 seconds (global element wait)
- Sets `pageLoadTimeout` to 30 seconds (max page load time)
- Sets `scriptTimeout` to 10 seconds (max async JS execution time)
- Gets and verifies the full HTML page source
- Gets and verifies the current window handle
- Gets and verifies all open window handles

---

## GitHub Actions CI/CD Pipeline

The pipeline is defined in `.github/workflows/selenium-ci.yml` and runs automatically on every push or pull request to the `main` branch.

```yaml
name: Selenium CI

on:
  push:
    branches: [main]        # Trigger on every push to main
  pull_request:
    branches: [main]        # Trigger on every PR targeting main

jobs:
  test:
    runs-on: ubuntu-latest  # Use a fresh Ubuntu cloud runner

    steps:
      # Step 1: Download the repository code onto the runner
      - name: Checkout code
        uses: actions/checkout@v4

      # Step 2: Install Java 11 (Temurin/Eclipse Adoptium distribution)
      - name: Set up Java
        uses: actions/setup-java@v4
        with:
          java-version: "11"
          distribution: "temurin"

      # Step 3: Install Google Chrome on the Ubuntu runner
      - name: Set up Chrome
        uses: browser-actions/setup-chrome@v1

      # Step 4: Run all Selenium tests using Maven
      # -B = batch mode (clean output, no interactive prompts)
      - name: Run Selenium Tests
        run: mvn test -B
```

**View live pipeline results:**  
https://github.com/Varadha9/selenium-cicd-pipeline/actions

---

## Pipeline Flow

```
Developer pushes code to GitHub
            |
            v
  GitHub Actions triggered automatically
            |
            v
  Step 1: Checkout repository code
            |
            v
  Step 2: Install Java 11 (Temurin)
            |
            v
  Step 3: Install Google Chrome (headless)
            |
            v
  Step 4: mvn test -B
            |
     ┌──────┴──────┐
     |              |
   PASS            FAIL
     |              |
     v              v
  Green ✅      Red ❌
  checkmark     X mark
  on GitHub     on GitHub
                PR blocked
                from merging
```

---

## Selenium Commands Reference

### Browser Commands
| Command | Description |
|---------|-------------|
| `driver.get(url)` | Open a URL in the browser |
| `driver.getTitle()` | Get the current page title |
| `driver.getCurrentUrl()` | Get the current page URL |
| `driver.getPageSource()` | Get the full HTML source of the page |
| `driver.quit()` | Close all windows and end the session |
| `driver.close()` | Close only the current window/tab |

### Navigation Commands
| Command | Description |
|---------|-------------|
| `driver.navigate().to(url)` | Navigate to a URL (supports history) |
| `driver.navigate().back()` | Go back one page in browser history |
| `driver.navigate().forward()` | Go forward one page in browser history |
| `driver.navigate().refresh()` | Reload the current page |

### Element Locator Strategies
| Strategy | Example | Use When |
|----------|---------|----------|
| `By.id()` | `By.id("username")` | Element has a unique ID |
| `By.name()` | `By.name("q")` | Element has a name attribute |
| `By.className()` | `By.className("btn")` | Element has a class |
| `By.tagName()` | `By.tagName("h1")` | Targeting by HTML tag |
| `By.cssSelector()` | `By.cssSelector("input[type='text']")` | Complex CSS targeting |
| `By.xpath()` | `By.xpath("//button[@id='submit']")` | Complex DOM traversal |
| `By.linkText()` | `By.linkText("Click here")` | Exact link text |
| `By.partialLinkText()` | `By.partialLinkText("Click")` | Partial link text |

### Wait Strategies
| Type | Command | Use When |
|------|---------|----------|
| Implicit Wait | `driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5))` | Global default wait for all elements |
| Explicit Wait | `new WebDriverWait(driver, Duration.ofSeconds(10))` | Wait for specific condition |
| Visibility | `ExpectedConditions.visibilityOfElementLocated(By)` | Element must be visible |
| Clickable | `ExpectedConditions.elementToBeClickable(By)` | Element must be clickable |
| Title | `ExpectedConditions.titleIs("Page Title")` | Wait for exact page title |
| URL | `ExpectedConditions.urlContains("keyword")` | Wait for URL to contain text |

### Window Management
| Command | Description |
|---------|-------------|
| `driver.manage().window().maximize()` | Maximize browser window |
| `driver.manage().window().setSize(new Dimension(w, h))` | Set window size |
| `driver.manage().window().setPosition(new Point(x, y))` | Set window position |
| `driver.getWindowHandle()` | Get current window ID |
| `driver.getWindowHandles()` | Get all open window IDs |
| `driver.switchTo().newWindow(WindowType.TAB)` | Open new tab |
| `driver.switchTo().window(handle)` | Switch to specific window |

---

## Troubleshooting

### `mvn` command not found
Maven is not on your system PATH. Either install Maven globally or use the Maven bundled with IntelliJ:
```
C:\Program Files\JetBrains\IntelliJ IDEA ...\plugins\maven\lib\maven3\bin\mvn.cmd test
```

### `ChromeDriver` version mismatch
WebDriverManager handles this automatically. If you see a version error, clear the cache:
```bash
# Delete cached drivers
rmdir /s /q %USERPROFILE%\.wdm
```
Then run `mvn test` again — WebDriverManager will download the correct version.

### `NoSuchElementException` on Google
Google shows a cookie consent popup in some regions that blocks the search box. This project uses **DuckDuckGo** for element interaction tests to avoid this issue.

### Tests pass locally but fail in CI
Check that the CI workflow installs the same Chrome version. The `browser-actions/setup-chrome@v1` action installs the latest stable Chrome — same as WebDriverManager downloads.

### `JAVA_HOME` not set
Set JAVA_HOME before running Maven:
```bash
# Windows
set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-11.x.x
set PATH=%JAVA_HOME%\bin;%PATH%

# macOS/Linux
export JAVA_HOME=/usr/lib/jvm/java-11-openjdk
export PATH=$JAVA_HOME/bin:$PATH
```

---

## Project Repository

- **GitHub:** https://github.com/Varadha9/selenium-cicd-pipeline
- **Actions (CI/CD):** https://github.com/Varadha9/selenium-cicd-pipeline/actions
- **Issues:** https://github.com/Varadha9/selenium-cicd-pipeline/issues

---

## Academic Information

| Field | Details |
|-------|---------|
| University | MIT ADT University, Pune |
| Department | Information Technology |
| Subject | Automation Testing |
| Course Code | 23IT3311 |
| Class | T.Y. SMAD |
| Academic Year | 2025-26 (Sem VI) |

---

*Built with Java + Selenium WebDriver + JUnit 5 + GitHub Actions*
