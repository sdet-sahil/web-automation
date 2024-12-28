package org.sdet.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.sdet.utils.ConfigReader;

public enum BrowserType {
    CHROME {
        @Override
        public WebDriver createDriver() {
            System.setProperty("webdriver.chrome.driver", ConfigReader.getProperty("chromeDriverPath"));
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            return new ChromeDriver(options);
        }
    },
    FIREFOX {
        @Override
        public WebDriver createDriver() {
            System.setProperty("webdriver.gecko.driver", ConfigReader.getProperty("geckoDriverPath"));
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--start-maximized");
            return new FirefoxDriver(options);
        }
    },
    SAFARI {
        @Override
        public WebDriver createDriver() {
            return new SafariDriver();
        }
    };

    // Abstract method to be implemented by each enum constant
    public abstract WebDriver createDriver();
}
