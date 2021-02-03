package com.example.scanner.logic;

import okhttp3.*;

public class HttpClientWrapper {
    private static OkHttpClient client = new OkHttpClient();

    public static void aget(String url, Callback callback, String token) {
        if (token == null) token = "";
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", token)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public static void apost(String path, RequestBody formBody, Callback callback, String token) {
        if (token == null) token = "";
        Request request = new Request.Builder()
                .url(path)
                .addHeader("Authorization", token)
                .addHeader("Content-Type", "application/json")
                .post(formBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }
}
