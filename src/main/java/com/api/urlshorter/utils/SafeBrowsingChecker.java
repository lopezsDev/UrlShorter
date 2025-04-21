package com.api.urlshorter.utils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SafeBrowsingChecker {

    private static final String API_KEY = System.getenv("SAFE_BROWSING_API_KEY");

    public static boolean isSafeUrl(String url) {
        try {
            if (API_KEY == null || API_KEY.isBlank()) {
                System.err.println("API_KEY no está definida");
                return false;
            }

            HttpClient client = HttpClient.newHttpClient();
            String json = """
                {
                  "client": {
                    "clientId": "urlshortener",
                    "clientVersion": "1.0"
                  },
                  "threatInfo": {
                    "threatTypes": ["MALWARE", "SOCIAL_ENGINEERING"],
                    "platformTypes": ["ANY_PLATFORM"],
                    "threatEntryTypes": ["URL"],
                    "threatEntries": [{"url": "%s"}]
                  }
                }
                """.formatted(url);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://safebrowsing.googleapis.com/v4/threatMatches:find?key=" + API_KEY))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return !response.body().contains("matches");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
