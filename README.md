# Selenium Tests in CI/CD Pipeline

Automated browser testing with Selenium integrated into a GitHub Actions CI/CD pipeline.

## Setup

```bash
pip install -r requirements.txt
```

## Run Tests Locally

```bash
pytest tests/ -v
```

## CI/CD

Tests run automatically on every **push** or **pull request** to `main` via GitHub Actions.

## Project Structure

```
├── tests/
│   └── test_sample.py       # Selenium test cases
├── .github/
│   └── workflows/
│       └── selenium-ci.yml  # GitHub Actions pipeline
└── requirements.txt
```
