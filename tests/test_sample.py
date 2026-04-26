import unittest
import time
from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.action_chains import ActionChains
from selenium.webdriver.common.window import WindowTypes


def create_driver():
    options = Options()
    options.add_argument("--start-maximized")
    return webdriver.Chrome(options=options)


class TestBrowserCommands(unittest.TestCase):
    """Browser & Navigation Commands"""

    def setUp(self):
        self.driver = create_driver()

    def tearDown(self):
        time.sleep(1)
        self.driver.quit()

    def test_navigation_commands(self):
        driver = self.driver

        # driver.get() - navigate to URL
        driver.get("https://www.example.com")
        self.assertIn("Example Domain", driver.title)
        print(f"Title: {driver.title}")
        print(f"URL: {driver.getCurrentUrl() if hasattr(driver, 'getCurrentUrl') else driver.current_url}")

        # navigate to another page
        driver.get("https://www.wikipedia.org")
        time.sleep(1)

        # navigate back
        driver.back()
        time.sleep(1)
        self.assertIn("Example Domain", driver.title)

        # navigate forward
        driver.forward()
        time.sleep(1)

        # refresh
        driver.refresh()
        time.sleep(1)

    def test_window_management(self):
        driver = self.driver

        driver.get("https://www.google.com")
        print(f"Window size: {driver.get_window_size()}")
        print(f"Window position: {driver.get_window_position()}")

        # set window size
        driver.set_window_size(1024, 768)
        time.sleep(1)

        # maximize
        driver.maximize_window()
        time.sleep(1)


class TestWindowSwitching(unittest.TestCase):
    """Window & Tab Switching Commands"""

    def setUp(self):
        self.driver = create_driver()

    def tearDown(self):
        time.sleep(1)
        self.driver.quit()

    def test_switch_between_tabs(self):
        driver = self.driver

        # Open Google in parent window
        driver.get("https://www.google.com")
        parent_window = driver.current_window_handle
        print(f"Parent Window ID: {parent_window}")

        # Open new tab and switch to it
        driver.switch_to.new_window(WindowTypes.TAB)
        driver.get("https://www.wikipedia.org")
        time.sleep(1)

        # Get all window handles
        all_windows = driver.window_handles
        child_window = [w for w in all_windows if w != parent_window][0]
        print(f"Child Window ID: {child_window}")

        time.sleep(2)

        # Switch back to parent
        driver.switch_to.window(parent_window)
        print("Switched back to Parent Window")
        self.assertIn("Google", driver.title)
        time.sleep(1)


class TestElementInteraction(unittest.TestCase):
    """Element Interaction & Action Commands"""

    def setUp(self):
        self.driver = create_driver()
        self.wait = WebDriverWait(self.driver, 10)

    def tearDown(self):
        time.sleep(1)
        self.driver.quit()

    def test_find_and_interact_elements(self):
        driver = self.driver

        driver.get("https://www.google.com")

        # find by name, send keys, clear
        search_box = self.wait.until(
            EC.visibility_of_element_located((By.NAME, "q"))
        )
        search_box.click()
        search_box.send_keys("Selenium Python")
        time.sleep(1)
        search_box.clear()

        # send keys with keyboard shortcut
        search_box.send_keys("Python automation")
        search_box.send_keys(Keys.RETURN)
        time.sleep(2)

        # find by CSS selector
        results = driver.find_elements(By.CSS_SELECTOR, "h3")
        print(f"Search results found: {len(results)}")
        self.assertGreater(len(results), 0)

    def test_javascript_execution(self):
        driver = self.driver

        driver.get("https://www.example.com")

        # JS: scroll to bottom
        driver.execute_script("window.scrollTo(0, document.body.scrollHeight)")
        time.sleep(1)

        # JS: scroll to top
        driver.execute_script("window.scrollTo(0, 0)")
        time.sleep(1)

        # JS: get page title via JS
        title = driver.execute_script("return document.title")
        print(f"JS Title: {title}")
        self.assertEqual(title, "Example Domain")

        # JS: highlight element
        heading = driver.find_element(By.TAG_NAME, "h1")
        driver.execute_script("arguments[0].style.border='3px solid red'", heading)
        time.sleep(1)

        # JS: click element
        link = driver.find_element(By.TAG_NAME, "a")
        driver.execute_script("arguments[0].click()", link)
        time.sleep(1)


class TestWaitCommands(unittest.TestCase):
    """Wait Commands"""

    def setUp(self):
        self.driver = create_driver()

    def tearDown(self):
        time.sleep(1)
        self.driver.quit()

    def test_explicit_wait(self):
        driver = self.driver
        wait = WebDriverWait(driver, 10)

        driver.get("https://www.google.com")

        # wait until element is visible
        search = wait.until(EC.visibility_of_element_located((By.NAME, "q")))
        self.assertTrue(search.is_displayed())

        # wait until element is clickable
        search = wait.until(EC.element_to_be_clickable((By.NAME, "q")))
        search.send_keys("Selenium")

        # wait until title contains
        wait.until(EC.title_contains("Google"))
        print(f"Page title: {driver.title}")

    def test_implicit_wait(self):
        driver = self.driver

        # implicit wait
        driver.implicitly_wait(10)
        driver.get("https://www.example.com")

        heading = driver.find_element(By.TAG_NAME, "h1")
        self.assertEqual(heading.text, "Example Domain")


class TestCookieCommands(unittest.TestCase):
    """Cookie Management Commands"""

    def setUp(self):
        self.driver = create_driver()

    def tearDown(self):
        time.sleep(1)
        self.driver.quit()

    def test_cookie_management(self):
        driver = self.driver

        driver.get("https://www.example.com")

        # add cookie
        driver.add_cookie({"name": "test_cookie", "value": "hello123"})

        # get specific cookie
        cookie = driver.get_cookie("test_cookie")
        print(f"Cookie: {cookie}")
        self.assertEqual(cookie["value"], "hello123")

        # get all cookies
        all_cookies = driver.get_cookies()
        print(f"Total cookies: {len(all_cookies)}")

        # delete specific cookie
        driver.delete_cookie("test_cookie")
        self.assertIsNone(driver.get_cookie("test_cookie"))

        # delete all cookies
        driver.delete_all_cookies()
        self.assertEqual(len(driver.get_cookies()), 0)


class TestScreenCapture(unittest.TestCase):
    """Screen Capture Commands"""

    def setUp(self):
        self.driver = create_driver()

    def tearDown(self):
        time.sleep(1)
        self.driver.quit()

    def test_screenshot(self):
        driver = self.driver

        driver.get("https://www.example.com")
        time.sleep(1)

        # save screenshot to file
        driver.save_screenshot("screenshot.png")
        print("Screenshot saved as screenshot.png")

        # get screenshot as base64
        base64_screenshot = driver.get_screenshot_as_base64()
        self.assertIsNotNone(base64_screenshot)
        print(f"Base64 screenshot length: {len(base64_screenshot)}")


class TestActionChains(unittest.TestCase):
    """Keyboard & Mouse Action Commands"""

    def setUp(self):
        self.driver = create_driver()
        self.wait = WebDriverWait(self.driver, 10)

    def tearDown(self):
        time.sleep(1)
        self.driver.quit()

    def test_action_chains(self):
        driver = self.driver
        actions = ActionChains(driver)

        driver.get("https://www.google.com")

        search = self.wait.until(EC.element_to_be_clickable((By.NAME, "q")))

        # move to element and click
        actions.move_to_element(search).click().perform()
        time.sleep(0.5)

        # send keys via actions
        actions.send_keys("Selenium WebDriver").perform()
        time.sleep(1)

        # Ctrl+A to select all, then type new text
        actions.key_down(Keys.CONTROL).send_keys("a").key_up(Keys.CONTROL).perform()
        time.sleep(0.5)
        actions.send_keys("Python testing").perform()
        time.sleep(1)

        # press Enter
        actions.send_keys(Keys.RETURN).perform()
        time.sleep(2)


if __name__ == "__main__":
    unittest.main(verbosity=2)
