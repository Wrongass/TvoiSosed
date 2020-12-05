package com.example.moisosed;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private Button login, reg;
    private EditText mail, pass;
    private TextInputLayout textInputEmail, textInputPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = (Button) findViewById(R.id.login);
        mail = (EditText) findViewById(R.id.mail);
        pass = (EditText) findViewById(R.id.pass);
        textInputEmail = findViewById(R.id.log_mail_error);
        textInputPass = findViewById(R.id.log_pass_error);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mail.getText().toString();
                String password = pass.getText().toString();
                Validation validation = new Validation();
//                if (validation.validateEmail(textInputEmail, mail) & validation.validatePassword(textInputPass, pass)) {
                    new LoginUser().execute(email, password);
//                }
            }
        });
        reg = (Button) findViewById(R.id.reg2);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegistration();
            }
        });
    }

    public void openRegistration() {
        Intent intent = new Intent(this, Registration.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public class LoginUser extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String email = strings[0];
            String password = strings[1];
            OkHttpClient okHttpClient = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\r\n\t\t\"jsonrpc\": \"2.0\",\r\n\t\t\"method\": \"auth.login\",\r\n\t\t\"params\": {\r\n            \"login\": \"" + email + "\",\r\n            \"password\": \"" + password + "\",\r\n            \"longLiving\": true\r\n        },\r\n        \"id\": null\r\n}");
            Request request = new Request.Builder()
                    .url(Token.url)
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .build();
            try {
                Response response = okHttpClient.newCall(request).execute();
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    if (result.contains("token")) {
                        Token token = new Token();
                        token.setTokens(result);
                        token.saveTokens(getApplicationContext());
                        Intent i = new Intent(MainActivity.this, UserProfile.class);
                        startActivity(i);
                        overridePendingTransition(0, 0);
                    } else if (result.contains("\"code\":2")){
                        new Thread() {
                            public void run() {
                                MainActivity.this.runOnUiThread(new Runnable() {
                                    public void run() {
                                        textInputEmail.setError("Неверный email");
                                    }
                                });
                            }
                        }.start();
                    } else if (result.contains("\"code\":101")){
                        new Thread() {
                            public void run() {
                                MainActivity.this.runOnUiThread(new Runnable() {
                                    public void run() {
                                        textInputPass.setError("Неверный пароль");
                                    }
                                });
                            }
                        }.start();
                    }
                } else {
                    new Thread() {
                        public void run() {
                            MainActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    textInputEmail.setError("Сервер недоступен, попробуйте позже");
                                    Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }.start();
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
