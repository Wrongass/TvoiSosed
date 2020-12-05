package com.example.moisosed;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;




public class Token {
    private static SharedPreferences tokenPref;
    private static String accessToken, accessTokenTime, refreshToken, refreshTokenTime;
    public final static String url = "http://cj50586.tmweb.ru/test";

    public void setTokens(String result) throws JSONException {
        JSONObject jsonResponse = new JSONObject(result);
        JSONObject jsonResult = jsonResponse.getJSONObject("result");
        JSONObject jsonAccessToken = jsonResult.getJSONObject("accessToken");
        JSONObject jsonRefreshToken = jsonResult.getJSONObject("refreshToken");
        accessToken = jsonAccessToken.getString("token");
        accessTokenTime = jsonAccessToken.getString("expirationTime");
        refreshToken = jsonRefreshToken.getString("token");
        refreshTokenTime = jsonRefreshToken.getString("expirationTime");
    }

    public static String getAccessToken() {
        return accessToken;
    }

    public static void saveTokens(Context context){
        tokenPref = context.getSharedPreferences("RefreshToken", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = tokenPref.edit();
        editor.putString("refreshToken", refreshToken);
        editor.putString("refreshTokenTime", refreshTokenTime);
        editor.commit();
    }

    public void doRefreshToken() throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\r\n\t\t\"jsonrpc\": \"2.0\",\r\n\t\t\"method\": \"auth.refresh\",\r\n\t\t\"params\": {\r\n            \"refreshToken\": " + refreshToken +"\r\n        },\r\n        \"id\": null\r\n}");
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        String result = response.body().string();
        setTokens(result);
    }
    
    public boolean checkTokens(Context context) throws IOException, JSONException {
        boolean isRelevant = true;
        Long unixTime = System.currentTimeMillis() / 1000;
        if(Long.parseLong(accessTokenTime) - unixTime < 10){
            if (Long.parseLong(refreshTokenTime) - unixTime < 10){
                UserProfile userProfile = new UserProfile();
                userProfile.openMainActivity();
            } else {
                doRefreshToken();
            }
        } else {
            SharedPreferences tokenPref = context.getApplicationContext().getSharedPreferences("RefreshToken", Context.MODE_PRIVATE);
            refreshToken = tokenPref.getString("refreshToken", "");
            refreshTokenTime = tokenPref.getString("refreshTokenTime", "");
            if (Long.parseLong(refreshTokenTime) - unixTime < 10){
                UserProfile userProfile = new UserProfile();
                userProfile.openMainActivity();
            }

        }
        return isRelevant;
    }
}
