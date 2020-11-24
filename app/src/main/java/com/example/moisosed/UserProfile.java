package com.example.moisosed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;

import java.io.IOException;

public class UserProfile extends AppCompatActivity {

    private BottomNavigationView navigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Token token = new Token();
        try {
            token.checkTokens();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        navigation = findViewById(R.id.bottom_navigation);
        navigation.setSelectedItemId(R.id.profile);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
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
    public void openUserProfile(){
        Intent intent = new Intent(this, UserProfile.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
    public void openFavorite(){
        Intent intent = new Intent(this, Favorite.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
    public void openAds(){
        Intent intent = new Intent(this, Ads.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
    public void openMessage(){
        Intent intent = new Intent(this, MessageActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}