package org.sdet.utils;
import org.openqa.selenium.*;
import org.sdet.models.Article;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;

public class HttpUtil {
    public static void downloadImage(String urlString, String savePath) {
        try (InputStream in = new URL(urlString).openStream()) {
            Files.copy(in, new File(savePath).toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
