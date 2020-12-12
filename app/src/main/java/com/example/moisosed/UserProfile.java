package com.example.moisosed;
import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class UserProfile extends AppCompatActivity {
    private static String id, name, surname, age, socialUrl, sex, specialities, email, phone;
    private static boolean animals;
    private static boolean children;
    private static boolean music;
    private static boolean russian_language;
    private static boolean smoking;
    private static boolean successRefreshToken;
    private static boolean registered;

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
            // добавить восклицательный знак чтобы работало
            if(task.get(10, TimeUnit.SECONDS)){
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

}