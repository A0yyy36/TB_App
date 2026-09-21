package com.example.books;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import java.util.Map;

public class BooksHandler
        implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final GoogleBooksClient CLIENT =
            new GoogleBooksClient(System.getenv("GOOGLE_BOOKS_API_KEY"));

    @Override
    public APIGatewayProxyResponseEvent handleRequest(
            APIGatewayProxyRequestEvent event, Context context) {

        Map<String, String> params = event.getQueryStringParameters();
        String q = (params == null) ? null : params.get("q");
        context.getLogger().log("q=" + q);

        if (q == null || q.isBlank()) {
            return response(400, "{\"message\":\"q is required\"}");
        }
        try {
            return response(200, CLIENT.search(q, context.getLogger()));
        } catch (GoogleBooksClient.UpstreamException e) {
            return response(502, "{\"message\":\"upstream error\"}");
        } catch (IllegalStateException e) {
            context.getLogger().log("config error: " + e.getMessage());
            return response(500, "{\"message\":\"server error\"}");
        }
    }

    private APIGatewayProxyResponseEvent response(int status, String body) {
        return new APIGatewayProxyResponseEvent()
                .withStatusCode(status)
                .withHeaders(Map.of("Content-Type", "application/json; charset=UTF-8"))
                .withBody(body);
    }
}