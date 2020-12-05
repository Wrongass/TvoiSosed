package com.example.moisosed;

import android.content.Intent;
import android.graphics.Rect;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import org.json.JSONException;

import java.io.IOException;
import java.util.EventListener;

public class UserProfileEdit extends AppCompatActivity {
    private BottomNavigationView navigation;
    private int checkAge = 50;
    private TextInputLayout textInputPhoneError, name_error_edit_profile, surname_error_edit_profile, edit_profile_age_error, email_error_edit_profile;
    private EditText edit_profile_name, edit_profile_about_myself, edit_profile_surname, edit_profile_age, edit_profile_socialUrl, edit_profile_email, edit_profile_phoneNumber;
    private TextView counterAboutMyself, counterSocialUrl;
    private RadioGroup sex_edit_profile;
    private RadioButton male_edit_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_edit);

        Button saveChanges = (Button) findViewById(R.id.edit_profile_save_changes);
        textInputPhoneError = (TextInputLayout) findViewById(R.id.edit_profile_phone_error);
        edit_profile_about_myself = (EditText) findViewById(R.id.edit_profile_about_myself);
        counterAboutMyself = (TextView) findViewById(R.id.edit_profile_counter_about_myself);
        counterSocialUrl = (TextView) findViewById(R.id.socialUrl_counter_edit_profile);
        edit_profile_name = (EditText) findViewById(R.id.edit_profile_name);
        edit_profile_surname = (EditText) findViewById(R.id.edit_profile_surname);
        edit_profile_age = (EditText) findViewById(R.id.edit_profile_age);
        edit_profile_socialUrl = (EditText) findViewById(R.id.edit_profile_socialUrl);
        edit_profile_email = (EditText) findViewById(R.id.edit_profile_email);
        edit_profile_phoneNumber = (EditText) findViewById(R.id.edit_profile_phoneNumber);
        male_edit_profile = (RadioButton) findViewById(R.id.male_edit_profile);
        sex_edit_profile = (RadioGroup) findViewById(R.id.sex_edit_profile);
        name_error_edit_profile = (TextInputLayout) findViewById(R.id.name_error_edit_profile);
        surname_error_edit_profile = (TextInputLayout) findViewById(R.id.surname_error_edit_profile);
        edit_profile_age_error = (TextInputLayout) findViewById(R.id.edit_profile_age_error);
        email_error_edit_profile = (TextInputLayout) findViewById(R.id.email_error_edit_profile);

        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveAccountSelfInfo saveAccountSelfInfo = new SaveAccountSelfInfo();
                Validation validation = new Validation();
                AccountSelfInfo accountSelfInfo = new AccountSelfInfo();
                if (accountSelfInfo.getAbout().length() == 0) {
                    accountSelfInfo.setAbout(" ");
                }
                if (accountSelfInfo.getSocialUrl().length() == 0) {
                    accountSelfInfo.setSocialUrl(" ");
                }
                if (R.id.male_edit_profile == sex_edit_profile.getCheckedRadioButtonId()) {
                    accountSelfInfo.setSex("male");
                } else {
                    accountSelfInfo.setSex("female");
                }


                accountSelfInfo.setName(edit_profile_name.getText().toString());
                accountSelfInfo.setSurname(edit_profile_surname.getText().toString());
                accountSelfInfo.setAge(edit_profile_age.getText().toString());
                accountSelfInfo.setPhone(edit_profile_phoneNumber.getText().toString());
                accountSelfInfo.setSocialUrl(edit_profile_socialUrl.getText().toString());
                accountSelfInfo.setAbout(edit_profile_about_myself.getText().toString());
                if (validation.validatePhoneNumber(textInputPhoneError, edit_profile_phoneNumber)
                        & validation.validateName(name_error_edit_profile, edit_profile_name)
                        & validation.validateName(surname_error_edit_profile, edit_profile_surname)
                        & validation.validateEmptyData(edit_profile_age_error, edit_profile_age)
//                        & validation.validateEmail(email_error_edit_profile, edit_profile_email)
                ) {
                    new SaveAccountSelfInfo().execute();
                }
            }
        });


        edit_profile_about_myself.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                counterAboutMyself.setText(s.toString().length() + "/3000");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        edit_profile_socialUrl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                counterSocialUrl.setText(s.toString().length() + "/100");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        edit_profile_age.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 0) {
                    checkAge = 50;
                } else {
                    checkAge = Integer.parseInt(s.toString());
                }
            }
        });

        KeyboardVisibilityEvent.setEventListener(UserProfileEdit.this,
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        if (checkAge < 18 && !isOpen) {
                            edit_profile_age.setText("18");
                        } else if (checkAge > 99 && !isOpen) {
                            edit_profile_age.setText("99");
                        }
                    }
                });


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
}