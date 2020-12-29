package com.example.moisosed;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;




public class Token {
    private static SharedPreferences tokenPref;
    private static String accessToken;
    private static String accessTokenTime;
    private static String refreshToken;
    private static String refreshTokenTime;
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

    
    public boolean checkTokens(Context context) throws JSONException, InterruptedException, ExecutionException, TimeoutException {
        boolean isRelevant = true;
        Long unixTime = System.currentTimeMillis() / 1000;
        if (accessTokenTime != null && refreshTokenTime != null){
        if(Long.parseLong(accessTokenTime) - unixTime < 10) {
            if (Long.parseLong(refreshTokenTime) - unixTime < 10) {
                isRelevant = false;
            } else {
                UserProfile userProfile = new UserProfile();
                if (!userProfile.doRefreshToken()){
                    isRelevant = false;
                } else {
                    saveTokens(context);
                }
            }
        }
        } else {
            SharedPreferences tokenPref = context.getApplicationContext().getSharedPreferences("RefreshToken", Context.MODE_PRIVATE);
            refreshToken = tokenPref.getString("refreshToken", null);
            refreshTokenTime = tokenPref.getString("refreshTokenTime", null);
            if (refreshTokenTime != null) {
                if (Long.parseLong(refreshTokenTime) - unixTime < 10) {
                    isRelevant = false;
                } else {
                    UserProfile userProfile = new UserProfile();
                    if (!userProfile.doRefreshToken()){
                        isRelevant = false;
                    } else {
                        saveTokens(context);
                    }
                }
            } else {
                isRelevant = false;
            }

        }
        return isRelevant;
    }

    public static void setAccessToken(String accessToken) {
        Token.accessToken = accessToken;
    }

    public static String getAccessTokenTime() {
        return accessTokenTime;
    }

    public static void setAccessTokenTime(String accessTokenTime) {
        Token.accessTokenTime = accessTokenTime;
    }

    public static String getRefreshToken() {
        return refreshToken;
    }

    public static void setRefreshToken(String refreshToken) {
        Token.refreshToken = refreshToken;
    }

    public static String getRefreshTokenTime() {
        return refreshTokenTime;
    }

    public static void setRefreshTokenTime(String refreshTokenTime) {
        Token.refreshTokenTime = refreshTokenTime;
    }
}
