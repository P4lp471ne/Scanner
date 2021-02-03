package com.example.scanner.logic;

import com.example.scanner.logic.datatypes.requestTypes.Report;
import com.example.scanner.utils.PropertiesLoader;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import java.io.IOException;

class WarehouseAPI implements API {
    String urlBase;
    private String token;
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    WarehouseAPI() throws IOException {
        urlBase = PropertiesLoader.getInstance().getProperty("URL");
        token = PropertiesLoader.getInstance().getProperty("TOKEN");
    }

    public void getRequestsList(Callback callback) {
        HttpClientWrapper.aget(urlBase + "/product_request/short", callback, token);
    }

    @Override
    public void getRequestData(String requestID, Callback callback) {
        HttpClientWrapper.aget(urlBase + "/product_request/" + requestID, callback, token);
    }

    @Override
    public void startCollecting(String requestID, Callback callback) {
        HttpClientWrapper.aget(urlBase + "/product_request/" + requestID + "/start", callback, token);
    }

    @Override
    public void cancelCollecting(String requestID, Callback callback) {
        HttpClientWrapper.aget(urlBase + "/product_request/" + requestID + "/cancel", callback, token);
    }

    public void finishCollecting(Report report, Callback callback) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            HttpClientWrapper.apost(urlBase + "/product_request/" + report.getId() + "/finish",
                    RequestBody.create(JSON, objectMapper.writeValueAsString(report)), callback, token);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void scan(String barcode, Callback callback) {
        RequestBody body = RequestBody.create(JSON, "{\"barcode\": \"" + barcode + "\"}");
        HttpClientWrapper.apost(urlBase + "/scan", body, callback, token);
    }
}
