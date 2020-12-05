package com.example.moisosed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Registration extends AppCompatActivity {

    private Button reg;
    private EditText mail_reg, pass_reg, repeat_pass_reg;
    private TextInputLayout textInputEmail, textInputPass, textInputRepeatPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        reg = (Button) findViewById(R.id.reg2);
        mail_reg = (EditText) findViewById(R.id.mail_reg);
        pass_reg = (EditText) findViewById(R.id.pass_reg);
        repeat_pass_reg = (EditText) findViewById(R.id.repeat_pass_reg);
        textInputEmail = findViewById(R.id.reg_mail_error);
        textInputPass = findViewById(R.id.reg_pass_error);
        textInputRepeatPass = findViewById(R.id.reg_repeat_pass_error);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mail_reg.getText().toString();
                String password = pass_reg.getText().toString();
                Validation validation = new Validation();
//                if (validation.validateEmail(textInputEmail, mail_reg) & validation.validatePassword(textInputPass, pass_reg) & validation.validateRepeatPassword(textInputRepeatPass,  pass_reg, repeat_pass_reg)) {
                    new RegistrationUser().execute(email, password);
//                }
            }
        });

    }


    public class RegistrationUser extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... strings){
            String email = strings[0];
            String password = strings[1];
            OkHttpClient okHttpClient = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\r\n\t\t\"jsonrpc\": \"2.0\",\r\n\t\t\"method\": \"auth.register\",\r\n\t\t\"params\": {\r\n            \"login\": \"" + email + "\",\r\n            \"password\": \"" + password + "\",\r\n            \"longLiving\": true\r\n        },\r\n        \"id\": null\r\n}");
            Request request = new Request.Builder()
                    .url(Token.url)
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .build();
            try {
                Response response = okHttpClient.newCall(request).execute();
                if (response.isSuccessful()){
                    String result = response.body().string();
                    if(result.contains("token")){
                        Token token = new Token();
                        token.setTokens(result);
                        Intent i = new Intent(Registration.this, UserProfile.class);
                        startActivity(i);
                        overridePendingTransition(0, 0);
                        finish();
                    } else if (result.contains("\"code\":9")){
                        new Thread() {
                            public void run() {
                                Registration.this.runOnUiThread(new Runnable() {
                                    public void run() {
                                        textInputEmail.setError("Указанный email уже зарегистрирован");
                                    }
                                });
                            }
                        }.start();
                    } else{
                        new Thread() {
                            public void run() {
                                Registration.this.runOnUiThread(new Runnable() {
                                    public void run() {
                                        textInputEmail.setError("Сервер недоступен, попробуйте позднее");
                                    }
                                });
                            }
                        }.start();
                    }
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}