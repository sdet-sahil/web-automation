package org.sdet.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.sdet.models.Article;

import java.util.ArrayList;
import java.util.List;

public class ArticleScraper {
    private static final Logger logger = LogManager.getLogger(ArticleScraper.class);
    public List<Article> scrapeArticles(WebDriver driver) {
        List<Article> articles = new ArrayList<>();
        try {
           logger.info("Navigate to the Opinion section of the website...");
            driver.get("https://elpais.com/opinion/");
            Thread.sleep(4000);
            List<WebElement> articleElements = driver.findElements(By.tagName("article"));

            for (int i = 0; i < Math.min(5, articleElements.size()); i++) {
                logger.info("*******Article {} ********", i);
                Article article = new Article();
                WebElement articleElement = articleElements.get(i);

                article.setTitle(articleElement.findElement(By.tagName("h2")).getText());
                article.setContent(articleElement.findElement(By.tagName("p")).getText());
               logger.info("Header in spanish");
               logger.info(article.getTitle());
                logger.info("Content in spanish");
                logger.info(article.getContent());

                try {
                    String imageUrl = articleElement.findElement(By.tagName("img")).getAttribute("src");
                    article.setImageUrl(imageUrl);
                    logger.info("************Image URL************");
                    logger.info(article.getImageUrl());
                    long time = System.currentTimeMillis();
                    HttpUtil.downloadImage(imageUrl, "images/article_" + i +"_"+ time + ".jpg");
                } catch (NoSuchElementException e) {
                    logger.info("No image found for article {}", i);
                }
                articles.add(article);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
        return articles;
    }
}
