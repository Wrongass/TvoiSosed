package com.example.moisosed;
import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class UserProfile extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private static boolean successRefreshToken;
    private static TextView profile_name, profile_about, profile_socialUrl, profile_phone, profile_email;
    private static TextView animals_profile, children_profile, music_profile, russian_language_profile, smoking_profile;
    private static AccountSelfInfo accountSelfInfo = new AccountSelfInfo();

    private BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Token token = new Token();
        try {
            token.checkTokens(getApplicationContext());
        } catch (JSONException | InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
        AccountSelfInfo task = new AccountSelfInfo();
        task.execute();
        try {
            if(!task.get(10, TimeUnit.SECONDS)){
                openUserProfileEdit();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        navigation = findViewById(R.id.bottom_navigation);
        navigation.setSelectedItemId(R.id.profile);

        setInfo();

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.search:
                        openSearch();
                        break;
                    case R.id.profile:
                        openUserProfile();
                        break;
                    case R.id.favorite:
                        openFavorite();
                        break;
                    case R.id.ads:
                        openAds();
                        break;
                    case R.id.message:
                        openMessage();
                        break;
                }
                return false;
            }
        });
    }

    public void showPopup(View v){
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }

    public void openSearch() {
        Intent intent = new Intent(this, List.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void openUserProfile() {
        Intent intent = new Intent(this, UserProfile.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void openUserProfileEdit() {
        Intent intent = new Intent(this, UserProfileEdit.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void openFavorite() {
        Intent intent = new Intent(this, Favorite.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void openAds() {
        Intent intent = new Intent(this, Ads.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void openMessage() {
        Intent intent = new Intent(this, MessageActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public boolean doRefreshToken() throws InterruptedException, ExecutionException, TimeoutException {
        RefreshToken task = new RefreshToken();
        task.execute();
        successRefreshToken = task.get(10, TimeUnit.SECONDS);
        return successRefreshToken;
    }

    public void setInfo(){
        profile_name = (TextView) findViewById(R.id.profile_name);
        profile_about = (TextView) findViewById(R.id.profile_about);
        profile_socialUrl = (TextView) findViewById(R.id.profile_socialUrl);
        profile_phone = (TextView) findViewById(R.id.profile_phone);
        profile_email = (TextView) findViewById(R.id.profile_email);

        animals_profile = (TextView) findViewById(R.id.animals_profile);
        children_profile = (TextView) findViewById(R.id.children_profile);
        music_profile = (TextView) findViewById(R.id.music_profile);
        russian_language_profile = (TextView) findViewById(R.id.russian_language_profile);
        smoking_profile = (TextView) findViewById(R.id.smoking_profile);

        profile_name.setText(accountSelfInfo.getName() + " " + accountSelfInfo.getSurname());
        profile_about.setText(accountSelfInfo.getAbout());
        profile_socialUrl.setText(accountSelfInfo.getSocialUrl());
        profile_phone.setText(accountSelfInfo.getPhone());
        profile_email.setText(accountSelfInfo.getEmail());

        if(accountSelfInfo.isAnimals()){
            animals_profile.setText("Есть");
        } else {
            animals_profile.setText("Нет");
        }
        if(accountSelfInfo.isChildren()){
            children_profile.setText("Есть");
        } else {
            children_profile.setText("Нет");
        }
        if(accountSelfInfo.isMusic()){
            music_profile.setText("Слушаю без наушников");
        } else {
            music_profile.setText("Слушаю в наушниках");
        }
        if(accountSelfInfo.isRussian_language()){
            russian_language_profile.setText("Родной");
        } else {
            russian_language_profile.setText("Второстепенный");
        }
        if(accountSelfInfo.isSmoking()){
            smoking_profile.setText("Курю");
        } else {
            smoking_profile.setText("Не курю");
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit_profile:
                openUserProfileEdit();
                return true;
            case R.id.exit:
                Token token = new Token();
                token.setRefreshTokenTime("0");
                token.setAccessTokenTime("0");
                token.saveTokens(this);
                openMainActivity();
                return true;
        }
        return false;
    }
}