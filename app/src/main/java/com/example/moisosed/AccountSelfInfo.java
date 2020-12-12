package com.example.moisosed;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AccountSelfInfo extends AsyncTask<String, Void, Boolean> {
    private static String id, name, surname, age, socialUrl, sex, about, specialities, email, phone = null;
    private static boolean animals, children, music, russian_language, smoking, registered;



    @Override
    protected Boolean doInBackground(String... strings) {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\r\n\t\t\"jsonrpc\": \"2.0\",\r\n\t\t\"method\": \"accounts.get\",\r\n\t\t\"params\": {\r\n            \r\n        },\r\n        \"id\": null\r\n}");
        Request request = new Request.Builder()
                .url(Token.url)
                .method("POST", body)
                .addHeader("Authorization", "Bearer " + Token.getAccessToken())
                .addHeader("Content-Type", "application/json")
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                String result = response.body().string();
                if (result.contains("name")) {
                    jsonParsing(result);
                    if (name == null) {
                        registered = false;
                    } else {
                        registered = true;
                    }
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return registered;
    }


    private void jsonParsing (String result) throws JSONException {
        JSONObject jsonResponse = new JSONObject(result);
        JSONArray resultArray = jsonResponse.getJSONArray("result");
        JSONObject jsonResult = resultArray.getJSONObject(0);
        id = jsonResult.getString("id");
        name = jsonResult.getString("name");
        surname = jsonResult.getString("surname");
        age = jsonResult.getString("age");
        socialUrl = jsonResult.getString("socialUrl");
        sex = jsonResult.getString("sex");
        about = jsonResult.getString("about");
        email = jsonResult.getString("email");
        phone = jsonResult.getString("phone");
        JSONArray specialitiesArray = jsonResult.getJSONArray("specialities");
        if (specialitiesArray.getJSONObject(0).getString("status") == "true"){
            animals = true;
        } else { animals = false;}
        if (specialitiesArray.getJSONObject(1).getString("status") == "true"){
            children = true;
        } else { children = false;}
        if (specialitiesArray.getJSONObject(2).getString("status") == "true"){
            music = true;
        } else { music = false;}
        if (specialitiesArray.getJSONObject(3).getString("status") == "true"){
            russian_language = true;
        } else { russian_language = false;}
        if (specialitiesArray.getJSONObject(4).getString("status") == "true"){
            smoking = true;
        } else { smoking = false;}
    }
    public static void setAllNull(){
        id = null;
        name = null;
        surname = null;
        age = null;
        socialUrl = null;
        sex = null;
        phone = null;
        about = null;
        animals = false;
        children = false;
        music = false;
        russian_language = false;
        smoking = false;
    }

    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        AccountSelfInfo.id = id;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        AccountSelfInfo.name = name;
    }

    public static String getSurname() {
        return surname;
    }

    public static void setSurname(String surname) {
        AccountSelfInfo.surname = surname;
    }

    public static String getAge() {
        return age;
    }

    public static void setAge(String age) {
        AccountSelfInfo.age = age;
    }

    public static String getSocialUrl() {
        return socialUrl;
    }

    public static void setSocialUrl(String socialUrl) {
        AccountSelfInfo.socialUrl = socialUrl;
    }

    public static String getSex() {
        return sex;
    }

    public static void setSex(String sex) {
        AccountSelfInfo.sex = sex;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        AccountSelfInfo.email = email;
    }

    public static String getPhone() {
        return phone;
    }

    public static void setPhone(String phone) {
        AccountSelfInfo.phone = phone;
    }

    public static String getAbout() { return about; }

    public static void setAbout(String about) { AccountSelfInfo.about = about; }

    public static boolean isAnimals() {
        return animals;
    }

    public static void setAnimals(boolean animals) {
        AccountSelfInfo.animals = animals;
    }

    public static boolean isChildren() {
        return children;
    }

    public static void setChildren(boolean children) {
        AccountSelfInfo.children = children;
    }

    public static boolean isMusic() {
        return music;
    }

    public static void setMusic(boolean music) {
        AccountSelfInfo.music = music;
    }

    public static boolean isRussian_language() {
        return russian_language;
    }

    public static void setRussian_language(boolean russian_language) { AccountSelfInfo.russian_language = russian_language; }

    public static boolean isSmoking() {
        return smoking;
    }

    public static void setSmoking(boolean smoking) {
        AccountSelfInfo.smoking = smoking;
    }

}
