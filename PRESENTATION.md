# Selenium WebDriver + CI/CD Pipeline — Presentation Guide

![Build Status](https://github.com/Varadha9/selenium-cicd-pipeline/actions/workflows/selenium-ci.yml/badge.svg)
![Java](https://img.shields.io/badge/Java-11-orange)
![Selenium](https://img.shields.io/badge/Selenium-4.18.1-green)
![TestNG](https://img.shields.io/badge/TestNG-7.9.0-blue)
![Maven](https://img.shields.io/badge/Maven-3.x-red)

---

## What is This Project?

This project is a **complete browser automation framework** built using Java and Selenium WebDriver.
It automatically controls a real Chrome browser — opens websites, types in search boxes, clicks buttons,
takes screenshots — and runs all these tests automatically on every code push using GitHub Actions CI/CD.

> Think of it like this: instead of a person manually opening a browser and checking if a website works,
> this code does it automatically in seconds.

---

## Why Did We Build This?

| Problem | Our Solution |
|---------|-------------|
| Manual testing is slow and repetitive | Automated tests run in ~20 seconds |
| "Works on my machine" problem | Tests run on a clean cloud server every time |
| No one knows if new code breaks something | Pipeline fails immediately and blocks the merge |
| Testing takes hours before each release | 9 tests cover all major browser operations automatically |

---

## Technology Stack — What We Used and Why

### 1. Java 11 — Programming Language
- All test logic is written in Java
- Industry standard for Selenium automation
- Strong typing helps catch errors before running

### 2. Selenium WebDriver 4.18.1 — Browser Automation
- The core library that controls the Chrome browser
- Sends commands to the browser: open URL, click, type, scroll, screenshot
- Most widely used browser automation tool in the industry

### 3. TestNG 7.9.0 — Test Framework
- Manages how tests are organized and run
- Provides annotations like `@BeforeSuite`, `@AfterSuite`, `@Test`
- Controls test execution order using `priority`
- Provides `Assert` methods to verify expected vs actual results
- Generates test reports automatically

### 4. WebDriverManager 5.7.0 — Driver Management
- Automatically downloads the correct ChromeDriver version
- Without this, you would need to manually download ChromeDriver and match it to your Chrome version
- One line of code: `WebDriverManager.chromedriver().setup()` — handles everything

### 5. Apache Maven 3.x — Build Tool
- Manages all dependencies (downloads Selenium, TestNG, WebDriverManager automatically)
- Single command `mvn test` compiles and runs all tests
- `pom.xml` is the config file that lists all dependencies and build settings

### 6. GitHub Actions — CI/CD Pipeline
- Automatically triggers on every `git push` to the main branch
- Sets up a fresh Ubuntu server, installs Java + Chrome, runs all 9 tests
- Shows ✅ green badge if all pass, ❌ red if any fail

### 7. ChromeDriver — Browser Driver
- Acts as a bridge between Selenium code and the Chrome browser
- Selenium sends commands → ChromeDriver translates → Chrome executes
- Automatically managed by WebDriverManager

---

## Project Architecture

```
Your Java Code  →  Selenium WebDriver  →  ChromeDriver  →  Chrome Browser
     ↑                                                            ↓
  TestNG                                                    Website Under Test
  manages test
  execution
     ↑
  GitHub Actions
  triggers on push
```

---

## Locators Used — How We Find Elements on Web Pages

Locators tell Selenium WHERE to find an element on the page (button, input box, link, etc.)

| Locator | Syntax Used in Code | Where We Used It | What It Does |
|---------|-------------------|-----------------|-------------|
| `By.name()` | `By.name("q")` | Test 3, Test 5 | Finds the search box by its `name` attribute |
| `By.cssSelector()` | `By.cssSelector("input[name='q']")` | Test 3 | Finds element using CSS selector syntax |
| `By.tagName()` | `By.tagName("h1")` | Test 4, Test 7 | Finds element by its HTML tag |

> In our project we used `By.name`, `By.cssSelector`, and `By.tagName` as the most reliable locators
> for the websites we tested.

**Other locators available in Selenium (not used here but important to know):**

| Locator | Example | Use When |
|---------|---------|----------|
| `By.id()` | `By.id("username")` | Element has a unique `id` attribute |
| `By.className()` | `By.className("btn-primary")` | Element has a CSS class |
| `By.xpath()` | `By.xpath("//input[@type='text']")` | Complex DOM navigation |
| `By.linkText()` | `By.linkText("Sign In")` | Exact text of a hyperlink |
| `By.partialLinkText()` | `By.partialLinkText("Sign")` | Partial text of a hyperlink |

---

## TestNG Annotations Used — How Tests Are Managed

| Annotation | Used In | What It Does |
|------------|---------|-------------|
| `@BeforeSuite` | `setup()` | Runs ONCE before all tests — launches Chrome browser |
| `@AfterSuite` | `teardown()` | Runs ONCE after all tests — closes Chrome browser |
| `@Test(priority = n)` | Each test method | Marks a method as a test, runs in order 1 → 9 |

**Why `@BeforeSuite` instead of `@BeforeClass`?**
`@BeforeSuite` runs once for the entire XML suite — perfect since all 9 tests share one browser session.

**Why `priority`?**
Tests depend on browser state — Test 2 expects the browser already open from Test 1.
Priority ensures they always run in the correct sequence: 1, 2, 3 ... 9.

---

## Wait Strategies Used

One of the most important concepts in Selenium — websites load dynamically, so you must wait for elements.

| Wait Type | Code Used | Where | Why |
|-----------|-----------|-------|-----|
| Implicit Wait | `implicitlyWait(Duration.ofSeconds(5))` | `setup()` | Global wait — applies to every `findElement()` call |
| Explicit Wait | `new WebDriverWait(driver, Duration.ofSeconds(10))` | Tests 3, 4, 5 | Waits for a specific condition before proceeding |
| Page Load Timeout | `pageLoadTimeout(Duration.ofSeconds(30))` | `setup()`, Test 9 | Max time allowed for a page to fully load |
| Script Timeout | `scriptTimeout(Duration.ofSeconds(10))` | Test 9 | Max time for async JavaScript to finish |

**ExpectedConditions used:**
- `visibilityOfElementLocated()` — waits until element appears on screen
- `elementToBeClickable()` — waits until element is visible AND clickable
- `titleContains()` — waits until page title contains a specific word

---

## 9 Tests — What Each One Does

| # | Test Name | Website | What It Demonstrates |
|---|-----------|---------|----------------------|
| 1 | `testBrowserNavigation` | google.com → wikipedia.org | `get()`, `getTitle()`, `getCurrentUrl()`, `navigate().back/forward/refresh()` |
| 2 | `testWindowManagement` | wikipedia.org + youtube.com | Multiple tabs, `switchTo()`, `getWindowHandle()`, `close()` |
| 3 | `testElementInteraction` | duckduckgo.com | `findElement()`, `sendKeys()`, `clear()`, `getAttribute()`, `Keys.ENTER` |
| 4 | `testWaitCommands` | quotes.toscrape.com | Implicit wait, explicit wait, `ExpectedConditions` |
| 5 | `testKeyboardMouseActions` | duckduckgo.com | `Actions` API, `moveToElement()`, `doubleClick()`, `keyDown/keyUp()` |
| 6 | `testCookiesManagement` | quotes.toscrape.com | `addCookie()`, `getCookieNamed()`, `deleteCookieNamed()`, `deleteAllCookies()` |
| 7 | `testJavaScriptExecution` | wikipedia.org | `JavascriptExecutor`, scroll page, highlight element, JS click |
| 8 | `testScreenCapture` | wikipedia.org | `TakesScreenshot`, `getScreenshotAs(BASE64)`, `getScreenshotAs(BYTES)` |
| 9 | `testSessionAndTimeouts` | quotes.toscrape.com | Session timeouts, `getPageSource()`, `getWindowHandles()` |

---

## Why DuckDuckGo for Search Tests?

> "Why not Google for the search tests?"

Google shows a **cookie consent popup** in many regions (especially India/EU).
This popup blocks the search box — Selenium cannot type into it.

DuckDuckGo:
- Has NO consent popup
- Search box is always immediately available
- Uses the same `name="q"` attribute as Google — so the Selenium code is identical
- Perfectly reliable for demonstrating search interactions

---

## CI/CD Pipeline — How It Works

```
You type:  git push origin main
                    ↓
         GitHub receives the push
                    ↓
      GitHub Actions workflow triggers
                    ↓
     Fresh Ubuntu server is created
                    ↓
         Java 11 is installed
                    ↓
         Chrome is installed
                    ↓
         mvn test runs all 9 tests
                    ↓
         ✅ All pass → Green badge
         ❌ Any fail → Red badge + PR blocked
```

**Headless mode in CI:**
On GitHub Actions there is no screen/monitor. Chrome needs special flags to run without a display:
```java
if (System.getenv("CI") != null) {
    options.addArguments("--headless=new");      // no screen needed
    options.addArguments("--no-sandbox");         // required in cloud containers
    options.addArguments("--disable-dev-shm-usage"); // prevents memory crashes
    options.addArguments("--disable-gpu");        // no GPU on cloud servers
    options.addArguments("--window-size=1920,1080"); // replaces maximize
}
```
Locally: Chrome opens visibly. In CI: Chrome runs silently in background.

---

## Key Files Explained

| File | Purpose |
|------|---------|
| `SeleniumCommandsTest.java` | All 9 test methods with full Selenium commands |
| `pom.xml` | Maven config — declares Selenium, TestNG, WebDriverManager dependencies |
| `testng.xml` | TestNG suite config — registers the test class for execution |
| `selenium-ci.yml` | GitHub Actions pipeline — defines the automated CI/CD steps |
| `.gitignore` | Excludes `target/` build folder from Git |

---

## How to Run

```bash
# Run all 9 tests
mvn test

# Run one specific test
mvn test -Dtest=SeleniumCommandsTest#testBrowserNavigation
```

**Expected output:**
```
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
BUILD SUCCESS
```

---

## Common Questions & Answers

**Q: What is WebDriver?**
WebDriver is an interface in Selenium that represents the browser. `ChromeDriver` is its implementation for Chrome. It provides all the methods like `get()`, `findElement()`, `quit()`.

**Q: What is the difference between `driver.close()` and `driver.quit()`?**
`close()` closes only the current tab. `quit()` closes ALL tabs and ends the entire browser session.

**Q: What is the difference between implicit and explicit wait?**
Implicit wait applies globally to every element search. Explicit wait is applied to one specific condition at a time — much more precise and reliable.

**Q: Why Maven?**
Without Maven, you would need to manually download Selenium JAR, TestNG JAR, WebDriverManager JAR and add them to the classpath. Maven does all of this automatically with just the `pom.xml` config.

**Q: What is `testng.xml` for?**
It tells TestNG which test classes to run. Maven Surefire plugin reads this file and passes it to TestNG to execute the correct tests.

**Q: Why use `@BeforeSuite` instead of creating the driver inside each test?**
Creating a new browser for each test would make the suite take 9× longer. One browser session is shared across all 9 tests for speed and efficiency.

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

## Project Links

- **GitHub Repository:** https://github.com/Varadha9/selenium-cicd-pipeline
- **Live CI/CD Results:** https://github.com/Varadha9/selenium-cicd-pipeline/actions

---

*Built with Java 11 + Selenium WebDriver 4.18.1 + TestNG 7.9.0 + Maven + GitHub Actions*
