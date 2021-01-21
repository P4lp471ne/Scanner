package com.example.scanner.logic;

import com.example.scanner.logic.datatypes.responseTypes.Product;
import com.example.scanner.utils.CallbackProvider;
import com.example.scanner.view.Consumer;
import com.example.scanner.view.ProductsListCallback;
import com.example.scanner.view.ReqListCallback;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Logic implements Producer{
    private API api;
    private Scanner scanner;
    private String buffer;
    private Consumer consumer;

    public void setDoubleScan(boolean mode) {
        scanner.setDoubleScan(mode);
    }

    public Logic() throws IOException {
        api = new WarehouseAPI();
        scanner = new Scanner();
    }

    public void requestRequestsList(ReqListCallback callback) {
        api.getRequestsList(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callback.action(ServerResponseProcessor.parseReqListResponse(response));
            }
        });
    }

    public void requestProductRequestData(String requestId, ProductsListCallback productsListCallback) {
        Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                productsListCallback.action(ServerResponseProcessor.parseReqDataResponse(response));
            }
        };
        api.getRequestData(requestId, callback);
    }

    public void requestStart(String requestID, Runnable action) {
        api.startCollecting(requestID, CallbackProvider.createControlCallback((Response response) -> {
            if (ServerResponseProcessor.parseStartResponse(response)) {
                action.run();
                scanner = new Scanner();
                scanner.start();
            }
        }));
    }

    public void requestCancel(String requestID, Runnable action) {
        api.cancelCollecting(requestID, CallbackProvider.createControlCallback((Response response) -> {
            if (ServerResponseProcessor.parseStartResponse(response)) {
                action.run();
            }
        }));
    }

    public void requestFinish(String requestID, Runnable action) {
        api.finishCollecting(scanner.getReport(), CallbackProvider.createControlCallback((Response response) -> {
            if (ServerResponseProcessor.parseStartResponse(response)) {
                action.run();
            }
        }));
    }

    public void scan(String reqv) {
        if (scanner != null && scanner.isActive()) {
            if (scanner.isDoubleScan()) {
                if (buffer == null) {
                    buffer = reqv;
                    return;
                }
                reqv = buffer + reqv;
            }

            api.scan(reqv, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Product prod = ServerResponseProcessor.parseScanResult(response);
                    scanner.scan(prod);
                    consumer.listen(prod);
                }
            });
        }
    }

    @Override
    public void subscribe(Consumer consumer) {
        this.consumer = consumer;
    }
}
