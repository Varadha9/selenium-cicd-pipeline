import pytest
from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.by import By


@pytest.fixture
def driver():
    options = Options()
    options.add_argument("--headless=new")
    options.add_argument("--no-sandbox")
    options.add_argument("--disable-dev-shm-usage")
    driver = webdriver.Chrome(options=options)
    yield driver
    driver.quit()


def test_page_title(driver):
    driver.get("https://www.example.com")
    assert "Example Domain" in driver.title


def test_heading_text(driver):
    driver.get("https://www.example.com")
    heading = driver.find_element(By.TAG_NAME, "h1")
    assert heading.text == "Example Domain"
