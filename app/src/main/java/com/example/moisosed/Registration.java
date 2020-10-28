package com.example.moisosed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Registration extends AppCompatActivity {

    private Button reg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        reg = (Button) findViewById(R.id.reg);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUserProfile1();
            }
        });
    }
    public void openUserProfile1(){
        Intent intent1 = new Intent(this, UserProfile.class);
        startActivity(intent1);
        overridePendingTransition(0, 0);
    }
}