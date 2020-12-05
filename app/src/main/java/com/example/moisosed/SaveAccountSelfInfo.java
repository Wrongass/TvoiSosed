package com.example.moisosed;

import android.os.AsyncTask;


import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SaveAccountSelfInfo extends AsyncTask<Void, Void, String> {
private String a, b;
    @Override
    protected String doInBackground(Void... voids) {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType,         "{\r\n\t\t\"jsonrpc\": \"2.0\",\r\n\t\t\"method\": \"accounts.edit\",\r\n\t\t\"params\": {\r\n            \"name\": \"" + AccountSelfInfo.getName() + "\",\r\n            \"surname\": \"" + AccountSelfInfo.getSurname() + "\",\r\n            \"age\": " + AccountSelfInfo.getAge() +  ",\r\n            \"phone\": \"" + AccountSelfInfo.getPhone() + "\",\r\n            \"about\": \"" + AccountSelfInfo.getAbout() + "\",\r\n            \"sex\": \"" + AccountSelfInfo.getSex() + "\",\r\n            \"socialUrl\": \"" + AccountSelfInfo.getSocialUrl() + "\"\r\n        },\r\n        \"id\": null\r\n}");
        Request request = new Request.Builder()
                .url(Token.url)
                .method("POST", body)
                .header("Authorization", "Bearer " + Token.getAccessToken())
                .addHeader("Content-Type", "application/json")
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                String result = response.body().string();
                if (result.contains("\"succes\":true")) {
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
