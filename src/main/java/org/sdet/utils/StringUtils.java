package org.sdet.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sdet.models.Article;

import java.util.*;

public class StringUtils {
    private static final Logger logger = LogManager.getLogger(StringUtils.class);
    public void printRepeatingWords(List<Article> articles) {
        logger.info("Started Analyzing Translated Headers...");
        Map<String, Integer> wordCount = new HashMap<>();

        for (Article article : articles) {
            String[] words = article.getTranslatedTitle().split("\\s+");
            for (String word : words) {
                word = word.toLowerCase().replaceAll("[^a-z]", "");
                wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
            }
        }

        wordCount.entrySet().stream()
                .filter(entry -> entry.getValue() > 2)
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
    }
}

