package com.example.moisosed;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Token {
    private String accessToken, accessTokenTime, refreshToken, refreshTokenTime;
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
    public String getAccessToken(){
        return accessToken;
    }
    public String getAccessTokenTime(){
        return accessTokenTime;
    }
    public String getRefreshToken(){
        return refreshToken;
    }
    public String getRefreshTokenTime(){
        return refreshTokenTime;
    }
    public void doRefreshToken() throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\r\n\t\t\"jsonrpc\": \"2.0\",\r\n\t\t\"method\": \"auth.refresh\",\r\n\t\t\"params\": {\r\n            \"refreshToken\": " + refreshToken +"\r\n        },\r\n        \"id\": null\r\n}");
        Request request = new Request.Builder()
                .url("http://cj50586.tmweb.ru")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        String result = response.body().string();
        setTokens(result);
    }
    public boolean checkTokens() throws IOException, JSONException {
        boolean isRelevant = false;
        Long unixTime = System.currentTimeMillis() / 1000;
        if(unixTime - Long.parseLong(accessTokenTime) < 10){
            doRefreshToken();
            if (unixTime - Long.parseLong(refreshTokenTime) < 10){
                UserProfile userProfile = new UserProfile();
                userProfile.openMainActivity();
            }
        }
        return isRelevant;
    }
}
