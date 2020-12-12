package com.example.moisosed;
import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.moisosed.Token.url;

public class RefreshToken extends AsyncTask<String, Void, Boolean> {
    public boolean success = false;


    @Override
    protected Boolean doInBackground(String... strings) {
        Token token = new Token();
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\r\n\t\t\"jsonrpc\": \"2.0\",\r\n\t\t\"method\": \"auth.refresh\",\r\n\t\t\"params\": {\r\n            \"refreshToken\": \"" + token.getRefreshToken() +"\"\r\n        },\r\n        \"id\": null\r\n}");
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                String result = response.body().string();
                if (result.contains("accessToken")) {
                    success = true;
                    token.setTokens(result);
                } else {
                    success = false;
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    return success;}



}
