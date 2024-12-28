package org.sdet.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sdet.driver.BrowserFactory;
import org.sdet.models.Article;
import org.sdet.utils.ArticleScraper;
import org.sdet.utils.ConfigReader;
import org.sdet.utils.StringUtils;
import org.sdet.utils.Translator;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;

public class CrossBrowserTest {
    // Create a logger instance for this class
    private static final Logger logger = LogManager.getLogger(CrossBrowserTest.class);
    private RemoteWebDriver driver;

    @BeforeTest
    @Parameters({"browser", "browserVersion","os", "osVersion", "deviceName"})
    public void setUp(String browser, @Optional String browserVersion, @Optional String os, String osVersion, @Optional String deviceName) throws Exception {

        // Set capabilities
        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability("browserName", browser);
        capabilities.setCapability("bstack:options", getBrowserStackOptions(browserVersion,os, osVersion,deviceName));


        // Create Remote WebDriver URL for BrowserStack
        BrowserFactory factory = new BrowserFactory();
        if(deviceName != null)
            driver = factory.createCloudDriver(capabilities, true);
        else {
            driver = factory.createCloudDriver(capabilities, false);
            driver.manage().window().maximize();
        }

        /* Initialize the WebDriver for local execution
        driver = factory.createDriver(BrowserType.CHROME, "local");*/
    }

    @Test
    public void testCrossPlatform() {
        try {
            // Step 0: Go to baseUrl
            logger.info("Navigating to home page and accepting cookies...");
            String baseUrl = ConfigReader.getProperty("baseUrl");
            driver.get(baseUrl);
            acceptCookies(driver);

            // Step 1: Ensure the language is spanish
            WebElement htmlTag = driver.findElement(By.tagName("html"));
            String langAttribute = htmlTag.getAttribute("lang");
            logger.info("Language attribute: {}", langAttribute);

            Assert.assertTrue(
                    langAttribute != null && langAttribute.toLowerCase().startsWith("es"),
                    "Website language is not set to Spanish."
            );
            logger.info("Successfully validated website's content is in Spanish!");

            // Step 2: Scrape Articles
            ArticleScraper scraper = new ArticleScraper();
            List<Article> articles = scraper.scrapeArticles(driver);

            // Step 3: Translate Titles
            Translator translator = new Translator();
            for (Article article : articles) {
                int count = 1;
                String translatedTitle = translator.translate(article.getTitle());
                //String translatedTitle = "Hey, How are you";
                article.setTranslatedTitle(translatedTitle);
                logger.info("Article {} header in english:", count);
                logger.info(translatedTitle);
            }

            // Step 4: Find repeated words in translated Headers
            StringUtils analyzer = new StringUtils();
            analyzer.printRepeatingWords(articles);
            logger.info("Workflow completed successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
    private static void acceptCookies(WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement agreeButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("didomi-notice-agree-button")));
        agreeButton.click();
    }
    
    private static HashMap<String, Object> getBrowserStackOptions(@Optional String browserVersion, @Optional String os, String osVersion, @Optional String deviceName){
        HashMap<String, Object> options = new HashMap<>();
        options.put("buildName", "Test git code");
        if(os != null) {
            options.put("os", os);
            options.put("browserVersion", browserVersion);
            options.put("osVersion", osVersion);
        } else if(deviceName != null) {
            options.put("osVersion", osVersion);
            options.put("deviceName", deviceName);
            options.put("platformName", "Android");
            options.put("consoleLogs", "info");
        }
        return options;
    }

   
}

