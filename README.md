# Web Automation 

## Libraries used

    * selenium-java client for interacting with Desktop browsers.
    * selenium-java appium client for interacting with Mobile browser.
    * Rest Assured for communicating with Translation apis and parsing response.
    * TestNG for running tests in parallel.
    * lombok for reducing boiler plate code.
    * log4j for logging
    * maven as build tool and managing dependencies

## Classes
    * BrowseFactory for instantiating drivers using factory design pattern.
    * ArticleScraper for scraping articles.
    * ConfigReader for reading the configuration data e.g. api keys, urls 
    * StringUtils for finding the duplicate words.
    * Translator for translating articles from one language to another.
    * Article class representing articles with properties like title, content, imageUrl.
    * CrossBrowserTest : Test class automating the entire workflow.

## Test Flow
   * Step 0: Go to baseUrl
   * Step 1: Ensure the language is spanish
   * Step 2: Scrape Articles
   * Step 3: Translate Titles
   * Step 4: Find & print repeated words in translated Headers
 
## Command to run tests
   * update browserStackUserName,browserStackAccessKey & translateApiKey in config.properties file
   * run command: mvn test -DsuiteXmlFile=testng.xml
