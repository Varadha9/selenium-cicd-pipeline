# Selenium Tests in CI/CD Pipeline

Java + Selenium automated browser testing integrated into a GitHub Actions CI/CD pipeline.

## Requirements
- Java 11+
- Maven

## Run Tests Locally

```bash
mvn test
```

> Browser will open visibly and run all tests automatically.

## CI/CD

Tests run automatically on every **push** or **pull request** to `main` via GitHub Actions (headless Chrome on Ubuntu).

## Project Structure

```
├── src/test/java/tests/
│   └── SeleniumCommandsTest.java   # All Selenium command types
├── .github/workflows/
│   └── selenium-ci.yml             # GitHub Actions pipeline
└── pom.xml
```

## Test Coverage

| Test | Selenium Commands Used |
|------|----------------------|
| Browser & Navigation | get, navigate, title, url |
| Window Management | maximize, newWindow, switchTo, close |
| Element Interaction | findElement, sendKeys, clear, click |
| Wait Commands | implicitlyWait, WebDriverWait, ExpectedConditions |
| Keyboard & Mouse | Actions, moveToElement, keyDown, doubleClick |
| Cookies | addCookie, getCookie, deleteCookie |
| JavaScript | executeScript, scroll, highlight, click |
| Screen Capture | getScreenshotAs (Base64, Bytes) |
| Session & Timeouts | pageLoadTimeout, scriptTimeout, getPageSource |
