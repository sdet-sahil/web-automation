package org.sdet.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties = new Properties();

    static {
        try {
            FileInputStream fis = new FileInputStream("/Users/sahilkashyap/work/web-automation/src/main/java/org/sdet/config/config.properties");
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            System.out.println("Property " + key + " is missing or not found.");
        }
        return value;
    }
}

