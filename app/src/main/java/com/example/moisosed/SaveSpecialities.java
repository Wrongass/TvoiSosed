package com.example.moisosed;

import android.os.AsyncTask;


import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SaveSpecialities extends AsyncTask<Void, Void, String> {
    @Override
    protected String doInBackground(Void... voids) {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\r\n\t\t\"jsonrpc\": \"2.0\",\r\n\t\t\"method\": \"accounts.editspecialities\",\r\n\t\t\"params\": {\r\n            \"specialities\": [\r\n                {\r\n                    \"label\": \"animals\",\r\n                    \"status\": " + AccountSelfInfo.isAnimals() + "\r\n                },\r\n                {\r\n                    \"label\": \"children\",\r\n                    \"status\": " + AccountSelfInfo.isChildren() + "\r\n                },\r\n                {\r\n                    \"label\": \"music\",\r\n                    \"status\": " + AccountSelfInfo.isMusic() + "\r\n                },\r\n                {\r\n                    \"label\": \"russian_language\",\r\n                    \"status\": " + AccountSelfInfo.isRussian_language() + "\r\n                },\r\n                {\r\n                    \"label\": \"smoking\",\r\n                    \"status\": " + AccountSelfInfo.isSmoking() + "\r\n                }\r\n            ]\r\n        },\r\n        \"id\": null\r\n}");
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
