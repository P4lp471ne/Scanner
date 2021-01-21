package com.example.scanner.logic;

import com.example.scanner.logic.datatypes.requestTypes.Report;
import com.example.scanner.utils.PropertiesLoader;

import java.io.IOException;

import okhttp3.Callback;

class WarehouseAPI implements API {
    String urlBase;
    private String token;

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
        HttpClientWrapper.aget(urlBase + requestID + "/start", callback, token);
    }

    @Override
    public void cancelCollecting(String requestID, Callback callback) {
        HttpClientWrapper.aget(urlBase + requestID + "/cancel", callback, token);
    }

    public void finishCollecting(Report report, Callback callback) {
        HttpClientWrapper.apost(urlBase + report.getId() + "/finnish",
                null, callback, token);
    }

    public void scan(String barcode, Callback callback) {
        HttpClientWrapper.aget(urlBase + "/scan", callback, token);
    }
}
