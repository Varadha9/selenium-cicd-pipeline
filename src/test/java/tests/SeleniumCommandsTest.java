package tests;

// WebDriverManager: automatically downloads and sets up the correct ChromeDriver binary
// so we don't need to manually download chromedriver.exe
import io.github.bonigarcia.wdm.WebDriverManager;

// JUnit 5 annotations for test lifecycle and ordering
import org.junit.jupiter.api.*;

// Core Selenium imports
import org.openqa.selenium.*;                          // WebDriver, By, Keys, Cookie, etc.
import org.openqa.selenium.chrome.ChromeDriver;        // Chrome-specific WebDriver implementation
import org.openqa.selenium.chrome.ChromeOptions;       // Used to configure Chrome browser settings

// Actions class: used for advanced keyboard and mouse interactions
import org.openqa.selenium.interactions.Actions;

// Explicit wait support: waits for specific conditions before proceeding
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

// Duration: used to specify time values for waits and timeouts
import java.time.Duration;

// Set: used to store multiple window handles (each open tab/window has a unique handle)
import java.util.Set;

// JUnit assertion methods: used to verify expected vs actual values in tests
import static org.junit.jupiter.api.Assertions.*;

// @TestMethodOrder: ensures tests run in the defined @Order sequence
// This is important because some tests depend on browser state from previous tests
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SeleniumCommandsTest {

    // WebDriver: the main interface to control the browser
    // Declared static so it is shared across all test methods in this class
    static WebDriver driver;

    // WebDriverWait: used for explicit waits — waits until a condition is true or timeout occurs
    // More reliable than Thread.sleep() because it polls dynamically
    static WebDriverWait wait;

    // @BeforeAll: runs ONCE before all tests — used to initialize the browser
    @BeforeAll
    static void setup() {
        // WebDriverManager automatically downloads the correct ChromeDriver version
        // matching the installed Chrome browser — no manual setup needed
        WebDriverManager.chromedriver().setup();

        // ChromeOptions: used to customize Chrome browser behavior before launching
        ChromeOptions options = new ChromeOptions();

        // --start-maximized: opens the browser in full screen so tests are visible
        // We do NOT use --headless here so the browser opens visually like IntelliJ runs
        options.addArguments("--start-maximized");

        // Launch Chrome browser with the configured options
        driver = new ChromeDriver(options);

        // WebDriverWait: sets a maximum wait time of 10 seconds for explicit waits
        // Used with ExpectedConditions to wait for elements/conditions dynamically
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // implicitlyWait: tells WebDriver to wait up to 5 seconds when finding elements
        // before throwing a NoSuchElementException — applies globally to all findElement calls
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        // pageLoadTimeout: maximum time to wait for a page to fully load
        // If the page doesn't load within 30 seconds, a TimeoutException is thrown
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
    }

    // @AfterAll: runs ONCE after all tests — used to close the browser and end the session
    @AfterAll
    static void teardown() {
        // driver.quit(): closes ALL open browser windows and ends the WebDriver session
        // Always call quit() instead of close() at the end to free up resources
        driver.quit();
    }

    // ─────────────────────────────────────────────────────────────────────────────
    // TEST 1: Browser & Navigation Commands
    // Purpose: Verify basic browser navigation — opening URLs, going back/forward, refresh
    // ─────────────────────────────────────────────────────────────────────────────
    @Test
    @Order(1) // Runs first
    void testBrowserNavigation() {

        // driver.get(): opens the specified URL in the current browser window
        // This is the most basic browser command — equivalent to typing a URL in the address bar
        driver.get("https://www.example.com");

        // driver.getTitle(): returns the title of the current page (text in the browser tab)
        // We use assertEquals to verify the page loaded correctly
        assertEquals("Example Domain", driver.getTitle());

        // driver.getCurrentUrl(): returns the full URL of the current page
        // Useful to verify redirects or that the correct page is loaded
        assertEquals("https://www.example.com/", driver.getCurrentUrl());

        // driver.navigate().to(): navigates to a new URL — similar to driver.get()
        // The difference: navigate().to() is part of the Navigation interface and supports history
        driver.navigate().to("https://www.wikipedia.org");

        // driver.navigate().back(): simulates clicking the browser's Back button
        // Goes to the previous page in the browser history (example.com)
        driver.navigate().back();

        // driver.navigate().forward(): simulates clicking the browser's Forward button
        // Goes to the next page in the browser history (wikipedia.org)
        driver.navigate().forward();

        // driver.navigate().refresh(): reloads the current page
        // Useful to test page reload behavior or reset page state
        driver.navigate().refresh();

        System.out.println("✅ Browser & Navigation Commands passed");
    }

    // ─────────────────────────────────────────────────────────────────────────────
    // TEST 2: Window Management Commands
    // Purpose: Demonstrate how to manage browser windows and tabs
    // ─────────────────────────────────────────────────────────────────────────────
    @Test
    @Order(2)
    void testWindowManagement() {
        driver.get("https://www.example.com");

        // driver.manage().window().maximize(): maximizes the browser window to fill the screen
        // Used to ensure elements are visible and not hidden due to small window size
        driver.manage().window().maximize();

        // driver.manage().window().getSize(): returns the current window size as a Dimension object
        // We verify the width is greater than 0 to confirm the window is open
        Dimension size = driver.manage().window().getSize();
        assertTrue(size.getWidth() > 0);

        // driver.manage().window().setSize(): sets the browser window to a specific width x height
        // Useful for testing responsive layouts at different screen resolutions
        driver.manage().window().setSize(new Dimension(1280, 720));

        // driver.manage().window().setPosition(): moves the browser window to a specific (x, y) position on screen
        // (0, 0) means top-left corner of the screen
        driver.manage().window().setPosition(new Point(0, 0));

        // driver.getWindowHandle(): returns the unique ID (handle) of the current window/tab
        // We save this as "parentWindow" so we can switch back to it later
        String parentWindow = driver.getWindowHandle();

        // driver.switchTo().newWindow(WindowType.TAB): opens a new browser tab and switches focus to it
        // WindowType.TAB opens a tab; WindowType.WINDOW opens a new browser window
        driver.switchTo().newWindow(WindowType.TAB);

        // Open a different website in the new tab (child window)
        driver.get("https://www.wikipedia.org");

        // driver.getWindowHandles(): returns a Set of all open window/tab handles
        // We use this to find the child window handle
        Set<String> allWindows = driver.getWindowHandles();

        // Loop through all window handles to identify the child window
        // The child window is any handle that is NOT the parent window handle
        String childWindow = "";
        for (String window : allWindows) {
            if (!window.equals(parentWindow)) {
                // Switch focus to the child window
                driver.switchTo().window(window);
                System.out.println("Child Window Title: " + driver.getTitle());

                // driver.close(): closes ONLY the currently focused window/tab
                // Different from driver.quit() which closes ALL windows
                driver.close();
            }
        }

        // Switch focus back to the parent window using its saved handle
        // After closing the child, we must explicitly switch back to the parent
        driver.switchTo().window(parentWindow);

        System.out.println("✅ Window Management Commands passed");
    }

    // ─────────────────────────────────────────────────────────────────────────────
    // TEST 3: Element Interaction Commands
    // Purpose: Find elements on the page and interact with them (type, click, clear)
    // ─────────────────────────────────────────────────────────────────────────────
    @Test
    @Order(3)
    void testElementInteraction() {
        driver.get("https://www.google.com");

        // driver.findElement(By.name()): locates an element using its HTML 'name' attribute
        // Returns the first matching WebElement on the page
        WebElement searchBox = driver.findElement(By.name("q"));

        // element.sendKeys(): simulates typing text into an input field
        // Used to enter text into search boxes, forms, etc.
        searchBox.sendKeys("Selenium WebDriver");

        // element.getAttribute("value"): retrieves the current value of an input field
        // We verify the text was typed correctly
        assertEquals("Selenium WebDriver", searchBox.getAttribute("value"));

        // element.clear(): clears all text from an input field
        // Used to reset a field before entering new text
        searchBox.clear();

        // driver.findElement(By.cssSelector()): locates an element using a CSS selector
        // More flexible than By.name() — can target elements by attribute, class, ID, etc.
        WebElement searchBoxCss = driver.findElement(By.cssSelector("input[name='q']"));

        // Type a new search query
        searchBoxCss.sendKeys("Java Selenium");

        // Keys.ENTER: simulates pressing the Enter key on the keyboard
        // Used to submit a search or form without clicking a button
        searchBoxCss.sendKeys(Keys.ENTER);

        // Explicit wait: waits until the page title contains "Java Selenium"
        // This ensures the search results page has loaded before we assert
        wait.until(ExpectedConditions.titleContains("Java Selenium"));
        assertTrue(driver.getTitle().contains("Java Selenium"));

        System.out.println("✅ Element Interaction Commands passed");
    }

    // ─────────────────────────────────────────────────────────────────────────────
    // TEST 4: Wait Commands
    // Purpose: Demonstrate implicit and explicit waits to handle dynamic page content
    // ─────────────────────────────────────────────────────────────────────────────
    @Test
    @Order(4)
    void testWaitCommands() {
        driver.get("https://www.example.com");

        // ExpectedConditions.visibilityOfElementLocated(): waits until the element is visible in the DOM
        // Used when elements load dynamically (e.g., after AJAX calls or animations)
        // wait.until() keeps polling every 500ms until the condition is true or timeout (10s) is reached
        WebElement heading = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.tagName("h1"))
        );

        // element.isDisplayed(): returns true if the element is visible on the page
        // Used to verify an element is actually shown to the user
        assertTrue(heading.isDisplayed());

        // ExpectedConditions.elementToBeClickable(): waits until the element is visible AND enabled
        // Used before clicking buttons or links to avoid ElementNotInteractableException
        WebElement link = wait.until(
            ExpectedConditions.elementToBeClickable(By.tagName("a"))
        );
        assertNotNull(link);

        // ExpectedConditions.titleIs(): waits until the page title exactly matches the given string
        // Useful to confirm navigation to the correct page
        wait.until(ExpectedConditions.titleIs("Example Domain"));

        System.out.println("✅ Wait Commands passed");
    }

    // ─────────────────────────────────────────────────────────────────────────────
    // TEST 5: Keyboard & Mouse Action Commands
    // Purpose: Simulate advanced user interactions like hover, drag, key combinations
    // ─────────────────────────────────────────────────────────────────────────────
    @Test
    @Order(5)
    void testKeyboardMouseActions() {
        driver.get("https://www.google.com");

        // Actions class: used for complex user interactions that go beyond simple click/type
        // Examples: hover, drag-and-drop, right-click, key combinations
        Actions actions = new Actions(driver);

        WebElement searchBox = driver.findElement(By.name("q"));

        // actions.moveToElement(): moves the mouse cursor to the center of the element
        // Used to trigger hover effects or ensure the element is in focus before interacting
        // .click(): clicks the element after moving to it
        // .perform(): executes all the chained actions
        actions.moveToElement(searchBox).click().perform();

        // actions.sendKeys(): types text using the Actions API
        // Useful when combined with other actions like key holds
        actions.sendKeys("Selenium Actions").perform();

        // actions.keyDown(Keys.CONTROL): holds down the Ctrl key
        // .sendKeys("a"): while Ctrl is held, press 'a' — this selects all text (Ctrl+A)
        // .keyUp(Keys.CONTROL): releases the Ctrl key
        // This simulates the keyboard shortcut Ctrl+A to select all text in the field
        actions.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).perform();

        // Keys.DELETE: simulates pressing the Delete key to remove selected text
        actions.sendKeys(Keys.DELETE).perform();

        // actions.doubleClick(): simulates a double mouse click on the element
        // Used to trigger double-click events (e.g., select a word in a text field)
        actions.doubleClick(searchBox).perform();

        System.out.println("✅ Keyboard & Mouse Action Commands passed");
    }

    // ─────────────────────────────────────────────────────────────────────────────
    // TEST 6: Cookies Management Commands
    // Purpose: Add, retrieve, and delete browser cookies to manage session data
    // ─────────────────────────────────────────────────────────────────────────────
    @Test
    @Order(6)
    void testCookiesManagement() {
        driver.get("https://www.example.com");

        // driver.manage().addCookie(): adds a new cookie to the current browser session
        // Cookies are used to store session tokens, user preferences, tracking data, etc.
        // new Cookie("name", "value"): creates a cookie with a name-value pair
        driver.manage().addCookie(new Cookie("testCookie", "testValue"));

        // driver.manage().getCookieNamed(): retrieves a specific cookie by its name
        // Returns null if the cookie does not exist
        Cookie cookie = driver.manage().getCookieNamed("testCookie");
        assertNotNull(cookie); // Verify the cookie was added successfully

        // cookie.getValue(): returns the value stored in the cookie
        assertEquals("testValue", cookie.getValue());

        // driver.manage().getCookies(): returns a Set of ALL cookies for the current domain
        // Used to inspect all active cookies in the session
        assertFalse(driver.manage().getCookies().isEmpty());

        // driver.manage().deleteCookieNamed(): deletes a specific cookie by name
        // Used to log out users or clear specific session data
        driver.manage().deleteCookieNamed("testCookie");

        // Verify the cookie was deleted — getCookieNamed returns null if not found
        assertNull(driver.manage().getCookieNamed("testCookie"));

        // driver.manage().deleteAllCookies(): deletes ALL cookies for the current domain
        // Used to reset browser state between tests or simulate a fresh session
        driver.manage().deleteAllCookies();
        assertTrue(driver.manage().getCookies().isEmpty());

        System.out.println("✅ Cookies Management Commands passed");
    }

    // ─────────────────────────────────────────────────────────────────────────────
    // TEST 7: JavaScript Execution Commands
    // Purpose: Run JavaScript directly in the browser to interact with the page
    //          beyond what standard Selenium commands allow
    // ─────────────────────────────────────────────────────────────────────────────
    @Test
    @Order(7)
    void testJavaScriptExecution() {
        driver.get("https://www.example.com");

        // JavascriptExecutor: interface that allows executing JavaScript code in the browser
        // We cast the WebDriver to JavascriptExecutor to access executeScript()
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // js.executeScript(): executes the given JavaScript and returns the result
        // "return document.title" — gets the page title via JavaScript
        // Useful when Selenium's getTitle() is not sufficient or for custom JS interactions
        String title = (String) js.executeScript("return document.title");
        assertEquals("Example Domain", title);

        // Scroll to the bottom of the page using JavaScript
        // window.scrollTo(x, y): scrolls to the given coordinates
        // document.body.scrollHeight: the total height of the page content
        // Used to load lazy-loaded content or test infinite scroll pages
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        // Scroll back to the top of the page
        // (0, 0) means top-left corner — x=0, y=0
        js.executeScript("window.scrollTo(0, 0)");

        // Get the current URL using JavaScript
        // "return window.location.href" — returns the full URL of the current page
        String url = (String) js.executeScript("return window.location.href");
        assertTrue(url.contains("example.com"));

        // Highlight an element by changing its CSS border using JavaScript
        // arguments[0] refers to the first argument passed after the script (the WebElement)
        // Used for visual debugging — makes the element stand out during test execution
        WebElement heading = driver.findElement(By.tagName("h1"));
        js.executeScript("arguments[0].style.border='3px solid red'", heading);

        // Click an element using JavaScript instead of Selenium's click()
        // Useful when an element is not interactable via normal Selenium click
        // (e.g., hidden behind another element or not in the viewport)
        WebElement link = driver.findElement(By.tagName("a"));
        js.executeScript("arguments[0].click()", link);

        System.out.println("✅ JavaScript Execution Commands passed");
    }

    // ─────────────────────────────────────────────────────────────────────────────
    // TEST 8: Screen Capture Commands
    // Purpose: Take screenshots of the browser to capture test evidence or debug failures
    // ─────────────────────────────────────────────────────────────────────────────
    @Test
    @Order(8)
    void testScreenCapture() {
        driver.get("https://www.example.com");

        // TakesScreenshot: interface that provides screenshot capability
        // We cast WebDriver to TakesScreenshot to access getScreenshotAs()
        TakesScreenshot ts = (TakesScreenshot) driver;

        // OutputType.BASE64: captures the screenshot as a Base64-encoded string
        // Useful for embedding screenshots in HTML reports or sending via API
        String base64 = ts.getScreenshotAs(OutputType.BASE64);
        assertNotNull(base64);
        assertFalse(base64.isEmpty());

        // OutputType.BYTES: captures the screenshot as a raw byte array
        // Useful for saving to a file or processing the image programmatically
        byte[] bytes = ts.getScreenshotAs(OutputType.BYTES);
        assertTrue(bytes.length > 0); // Verify screenshot data was captured

        System.out.println("✅ Screen Capture Commands passed");
    }

    // ─────────────────────────────────────────────────────────────────────────────
    // TEST 9: Session & Timeout Commands
    // Purpose: Configure WebDriver session settings and verify session information
    // ─────────────────────────────────────────────────────────────────────────────
    @Test
    @Order(9)
    void testSessionAndTimeouts() {

        // implicitlyWait: sets a global wait time for finding elements
        // WebDriver will poll the DOM for up to 5 seconds before throwing NoSuchElementException
        // Applied to ALL findElement() calls — no need to set it per element
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        // pageLoadTimeout: maximum time to wait for a page to fully load
        // If the page takes longer than 30 seconds, a TimeoutException is thrown
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        // scriptTimeout: maximum time to wait for an asynchronous JavaScript to finish executing
        // Used with driver.executeAsyncScript() — prevents scripts from hanging indefinitely
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(10));

        driver.get("https://www.example.com");

        // driver.getPageSource(): returns the complete HTML source code of the current page
        // Useful for verifying page content without interacting with DOM elements
        String source = driver.getPageSource();
        assertTrue(source.contains("Example Domain"));

        // driver.getWindowHandle(): returns the unique identifier (handle) of the current window
        // Each browser tab/window has a unique handle — used to switch between windows
        String handle = driver.getWindowHandle();
        assertNotNull(handle); // Verify a valid handle was returned

        // driver.getWindowHandles(): returns handles of ALL open windows/tabs
        // Returns a Set<String> — each string is a unique window handle
        assertFalse(driver.getWindowHandles().isEmpty());

        System.out.println("✅ Session & Timeout Commands passed");
    }
}
