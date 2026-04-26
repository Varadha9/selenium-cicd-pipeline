import unittest
from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.by import By


class TestExample(unittest.TestCase):

    def setUp(self):
        options = Options()
        options.add_argument("--headless=new")
        options.add_argument("--no-sandbox")
        options.add_argument("--disable-dev-shm-usage")
        self.driver = webdriver.Chrome(options=options)

    def tearDown(self):
        self.driver.quit()

    def test_page_title(self):
        self.driver.get("https://www.example.com")
        self.assertIn("Example Domain", self.driver.title)

    def test_heading_text(self):
        self.driver.get("https://www.example.com")
        heading = self.driver.find_element(By.TAG_NAME, "h1")
        self.assertEqual(heading.text, "Example Domain")


if __name__ == "__main__":
    unittest.main()
