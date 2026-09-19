package com.example.books;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import java.util.Map;

public class BooksHandler
        implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Override
    public APIGatewayProxyResponseEvent handleRequest(
            APIGatewayProxyRequestEvent event, Context context) {

        Map<String, String> params = event.getQueryStringParameters();
        String q = (params == null) ? null : params.get("q");
        context.getLogger().log("q=" + q);

        if (q == null || q.isBlank()) {
            return response(400, "{\"message\":\"q is required\"}");
        }

        String body = "{\"items\":[{\"id\":\"dummy\",\"volumeInfo\":{"
                + "\"title\":\"固定データ\",\"authors\":[\"テスト太郎\"],"
                + "\"publisher\":\"テスト出版\",\"publishedDate\":\"2024\","
                + "\"imageLinks\":{\"thumbnail\":null}}}]}";
        return response(200, body);
    }

    private APIGatewayProxyResponseEvent response(int status, String body) {
        return new APIGatewayProxyResponseEvent()
                .withStatusCode(status)
                .withHeaders(Map.of("Content-Type", "application/json; charset=UTF-8"))
                .withBody(body);
    }
}