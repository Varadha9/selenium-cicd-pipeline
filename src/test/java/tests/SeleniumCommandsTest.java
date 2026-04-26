package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SeleniumCommandsTest {

    static WebDriver driver;
    static WebDriverWait wait;

    @BeforeAll
    static void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");   // visible browser, maximized
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
    }

    @AfterAll
    static void teardown() {
        driver.quit();
    }

    // ── Browser & Navigation Commands ──────────────────────────────────────────

    @Test
    @Order(1)
    void testBrowserNavigation() {
        // Browser Commands
        driver.get("https://www.example.com");
        assertEquals("Example Domain", driver.getTitle());
        assertEquals("https://www.example.com/", driver.getCurrentUrl());

        // Navigation Commands
        driver.navigate().to("https://www.wikipedia.org");
        driver.navigate().back();
        driver.navigate().forward();
        driver.navigate().refresh();

        System.out.println("✅ Browser & Navigation Commands passed");
    }

    // ── Window Management Commands ──────────────────────────────────────────────

    @Test
    @Order(2)
    void testWindowManagement() {
        driver.get("https://www.example.com");

        // Window size & position
        driver.manage().window().maximize();
        Dimension size = driver.manage().window().getSize();
        assertTrue(size.getWidth() > 0);

        driver.manage().window().setSize(new Dimension(1280, 720));
        driver.manage().window().setPosition(new Point(0, 0));

        // Open new tab, switch, then close
        String parentWindow = driver.getWindowHandle();
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get("https://www.wikipedia.org");

        Set<String> allWindows = driver.getWindowHandles();
        for (String window : allWindows) {
            if (!window.equals(parentWindow)) {
                driver.switchTo().window(window);
                System.out.println("Child Window Title: " + driver.getTitle());
                driver.close();
            }
        }
        driver.switchTo().window(parentWindow);

        System.out.println("✅ Window Management Commands passed");
    }

    // ── Element Interaction & Action Commands ───────────────────────────────────

    @Test
    @Order(3)
    void testElementInteraction() {
        driver.get("https://www.google.com");

        // Find by name, send keys, clear
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("Selenium WebDriver");
        assertEquals("Selenium WebDriver", searchBox.getAttribute("value"));
        searchBox.clear();

        // Find by CSS selector
        WebElement searchBoxCss = driver.findElement(By.cssSelector("input[name='q']"));
        searchBoxCss.sendKeys("Java Selenium");
        searchBoxCss.sendKeys(Keys.ENTER);

        // Wait for results
        wait.until(ExpectedConditions.titleContains("Java Selenium"));
        assertTrue(driver.getTitle().contains("Java Selenium"));

        System.out.println("✅ Element Interaction Commands passed");
    }

    // ── Wait Commands ───────────────────────────────────────────────────────────

    @Test
    @Order(4)
    void testWaitCommands() {
        driver.get("https://www.example.com");

        // Explicit wait - visibility
        WebElement heading = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.tagName("h1"))
        );
        assertTrue(heading.isDisplayed());

        // Explicit wait - element clickable
        WebElement link = wait.until(
            ExpectedConditions.elementToBeClickable(By.tagName("a"))
        );
        assertNotNull(link);

        // Wait for title
        wait.until(ExpectedConditions.titleIs("Example Domain"));

        System.out.println("✅ Wait Commands passed");
    }

    // ── Keyboard & Mouse Action Commands ────────────────────────────────────────

    @Test
    @Order(5)
    void testKeyboardMouseActions() {
        driver.get("https://www.google.com");

        Actions actions = new Actions(driver);
        WebElement searchBox = driver.findElement(By.name("q"));

        // Move to element and click
        actions.moveToElement(searchBox).click().perform();

        // Send keys via Actions
        actions.sendKeys("Selenium Actions").perform();

        // Select all and delete
        actions.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).perform();
        actions.sendKeys(Keys.DELETE).perform();

        // Double click
        actions.doubleClick(searchBox).perform();

        System.out.println("✅ Keyboard & Mouse Action Commands passed");
    }

    // ── Cookies Management Commands ─────────────────────────────────────────────

    @Test
    @Order(6)
    void testCookiesManagement() {
        driver.get("https://www.example.com");

        // Add cookie
        driver.manage().addCookie(new Cookie("testCookie", "testValue"));

        // Get cookie
        Cookie cookie = driver.manage().getCookieNamed("testCookie");
        assertNotNull(cookie);
        assertEquals("testValue", cookie.getValue());

        // Get all cookies
        assertFalse(driver.manage().getCookies().isEmpty());

        // Delete cookie
        driver.manage().deleteCookieNamed("testCookie");
        assertNull(driver.manage().getCookieNamed("testCookie"));

        // Delete all cookies
        driver.manage().deleteAllCookies();
        assertTrue(driver.manage().getCookies().isEmpty());

        System.out.println("✅ Cookies Management Commands passed");
    }

    // ── JavaScript Execution Commands ────────────────────────────────────────────

    @Test
    @Order(7)
    void testJavaScriptExecution() {
        driver.get("https://www.example.com");
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Get page title via JS
        String title = (String) js.executeScript("return document.title");
        assertEquals("Example Domain", title);

        // Scroll to bottom
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        // Scroll to top
        js.executeScript("window.scrollTo(0, 0)");

        // Get current URL via JS
        String url = (String) js.executeScript("return window.location.href");
        assertTrue(url.contains("example.com"));

        // Highlight element
        WebElement heading = driver.findElement(By.tagName("h1"));
        js.executeScript("arguments[0].style.border='3px solid red'", heading);

        // Click via JS
        WebElement link = driver.findElement(By.tagName("a"));
        js.executeScript("arguments[0].click()", link);

        System.out.println("✅ JavaScript Execution Commands passed");
    }

    // ── Screen Capture Commands ──────────────────────────────────────────────────

    @Test
    @Order(8)
    void testScreenCapture() {
        driver.get("https://www.example.com");
        TakesScreenshot ts = (TakesScreenshot) driver;

        // Capture as Base64
        String base64 = ts.getScreenshotAs(OutputType.BASE64);
        assertNotNull(base64);
        assertFalse(base64.isEmpty());

        // Capture as bytes
        byte[] bytes = ts.getScreenshotAs(OutputType.BYTES);
        assertTrue(bytes.length > 0);

        System.out.println("✅ Screen Capture Commands passed");
    }

    // ── Session & Timeout Commands ───────────────────────────────────────────────

    @Test
    @Order(9)
    void testSessionAndTimeouts() {
        // Timeout commands
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(10));

        driver.get("https://www.example.com");

        // Page source
        String source = driver.getPageSource();
        assertTrue(source.contains("Example Domain"));

        // Window handle
        String handle = driver.getWindowHandle();
        assertNotNull(handle);

        // All handles
        assertFalse(driver.getWindowHandles().isEmpty());

        System.out.println("✅ Session & Timeout Commands passed");
    }
}
