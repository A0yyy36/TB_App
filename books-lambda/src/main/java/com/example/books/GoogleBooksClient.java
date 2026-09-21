package com.example.books;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class GoogleBooksClient {

    private static final String ENDPOINT = "https://www.googleapis.com/books/v1/volumes";

    // ウォーム時に接続を使い回すため、1回だけ作る
    private static final HttpClient HTTP = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(3))
            .build();

    // & や = を \u0026 にエスケープしない
    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();

    private final String apiKey;

    public GoogleBooksClient(String apiKey) {
        this.apiKey = apiKey;
    }

    public String search(String q, LambdaLogger logger) {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("GOOGLE_BOOKS_API_KEY is not set");
        }
        URI uri = URI.create(ENDPOINT
                + "?q=" + URLEncoder.encode(q, StandardCharsets.UTF_8)
                + "&maxResults=20");
        HttpRequest request = HttpRequest.newBuilder(uri)
                .timeout(Duration.ofSeconds(5))
                .header("x-goog-api-key", apiKey)   // キーはURLに入れない
                .GET()
                .build();
        try {
            HttpResponse<String> res = HTTP.send(
                    request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            if (res.statusCode() != 200) {
                // 本文は出さない。ステータスと reason だけ残す
                logger.log("upstream status=" + res.statusCode()
                        + " reason=" + extractReason(res.body()));
                throw new UpstreamException("upstream status " + res.statusCode(), null);
            }
            return convert(res.body());
        } catch (IOException | JsonParseException e) {
            logger.log("upstream error: " + e.getClass().getSimpleName());
            throw new UpstreamException("upstream failure", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new UpstreamException("interrupted", e);
        }
    }

    // Googleを呼ばずにテストできるよう、変換だけ独立させる
    static String convert(String googleJson) {
        Response in = GSON.fromJson(googleJson, Response.class);
        Response out = new Response();
        out.items = new ArrayList<>();               // 0件でも items:[] にする
        if (in != null && in.items != null) {
            for (Item item : in.items) {
                if (item.volumeInfo != null && item.volumeInfo.imageLinks != null) {
                    String t = item.volumeInfo.imageLinks.thumbnail;
                    if (t != null && t.startsWith("http://")) {
                        item.volumeInfo.imageLinks.thumbnail = "https://" + t.substring(7);
                    }
                }
                out.items.add(item);
            }
        }
        return GSON.toJson(out);
    }

    private static String extractReason(String body) {
        try {
            JsonObject err = JsonParser.parseString(body).getAsJsonObject().getAsJsonObject("error");
            JsonArray details = err.getAsJsonArray("details");
            if (details != null) {
                for (JsonElement d : details) {
                    JsonObject o = d.getAsJsonObject();
                    if (o.has("reason")) return o.get("reason").getAsString();
                }
            }
            return err.get("status").getAsString();
        } catch (RuntimeException e) {
            return "unknown";
        }
    }

    public static class UpstreamException extends RuntimeException {
        public UpstreamException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    // DTOには残したい項目だけを書く。書かなかった項目は自動で落ちる
    static class Response { List<Item> items; }
    static class Item { String id; VolumeInfo volumeInfo; }
    static class VolumeInfo {
        String title;
        List<String> authors;
        String publisher;
        String publishedDate;
        ImageLinks imageLinks;
    }
    static class ImageLinks { String thumbnail; }
}