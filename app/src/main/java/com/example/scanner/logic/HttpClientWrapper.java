package com.example.scanner.logic;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

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
                .post(formBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }
}
