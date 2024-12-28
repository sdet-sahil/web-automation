package org.sdet.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.given;

public class Translator {
    public  String translate(String text) {
        // Base URI
        RestAssured.baseURI = "https://rapid-translate-multi-traduction.p.rapidapi.com";
        String requestBody = String.format(
                "{\"from\":\"es\",\"to\":\"en\",\"q\":\"%s\"}", text
        );
        String apiKey = ConfigReader.getProperty("translateApiKey");

        // API Request
        Response response = given()
                .header("Content-Type", "application/json")
                .header("x-rapidapi-host", "rapid-translate-multi-traduction.p.rapidapi.com")
                .header("x-rapidapi-key", apiKey)
                .body(requestBody)
                .when()
                .post("/t")
                .then()
                .statusCode(200) // Validate HTTP status code
                .extract()
                .response();

        // Parse JSON Array response
        List<String> translatedTextList = response.jsonPath().getList("$");
        return translatedTextList.get(0);
        }
    }



