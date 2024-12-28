package org.sdet.driver;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.sdet.utils.ConfigReader;

import java.net.URL;

public class BrowserFactory {

    public WebDriver createDriver(BrowserType browser, String environment, MutableCapabilities capabilities) throws Exception {
        WebDriver driver = null;

        // Local WebDriver setup
        if (environment.equalsIgnoreCase("local")) {
            driver = browser.createDriver();
        }
        else {
            throw new IllegalArgumentException("Unsupported environment: " + environment);
        }

        return driver;
    }

    public RemoteWebDriver createCloudDriver(MutableCapabilities caps, boolean isMobile) throws Exception {
        // BrowserStack credentials
        String username = ConfigReader.getProperty("browserStackUserName");  // Replace with your username
        String accessKey = ConfigReader.getProperty("browserStackAccessKey");   // Replace with your access key
        String hubUrl = "https://" + username + ":" + accessKey + "@hub-cloud.browserstack.com/wd/hub";
        URL remoteUrl = new URL(hubUrl);
        RemoteWebDriver driver;
        if(isMobile)
            driver = new AndroidDriver(remoteUrl, caps);
        else {
            driver = new RemoteWebDriver(remoteUrl, caps);
            driver.manage().window().maximize();
        }
        return driver;
    }
}