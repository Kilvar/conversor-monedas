package com.exchangerate.utils;

import com.exchangerate.base.Currency;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ExchangeApiCaller {

    public List<Currency> requestPairConversion(String baseCurrency, String baseCode, String targetCurrency, String targetCode, String amount){

        String API_ADDRESS = "https://v6.exchangerate-api.com/v6/";
        String API_KEY = ""; // Your API KEY here

        StringBuilder sb;
        sb = new StringBuilder(API_ADDRESS);

        sb.append(API_KEY)
                .append("/pair")
                .append("/").append(baseCode)
                .append("/").append(targetCode)
                .append("/").append(amount);

        String requestData = sb.toString();

        JsonObject apiResponse = sendRequest(requestData);

        if (apiResponse.get("result").getAsString().equals("error")){
            throw new RuntimeException("Error de consulta de la API. Verifique la API key.");
        }

        String result = apiResponse.get("conversion_result").getAsString();

        var resultList = new ArrayList<Currency>();

        resultList.add(new Currency(baseCurrency, baseCode, amount));
        resultList.add(new Currency(targetCurrency, targetCode, result));

        return resultList;

    }

    private JsonObject sendRequest( String requestData ) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(requestData))
                    .build();

            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            return new Gson().fromJson(response.body(), JsonObject.class);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Ocurrió un error inesperado. Verifique su conexión a internet.");
        }

    }

}



