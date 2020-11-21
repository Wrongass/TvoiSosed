package com.example.moisosed;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class Validation extends AppCompatActivity {
    private static final Pattern PASSWORD_DIGIT = Pattern.compile("^" + "(?=.*[0-9])" + ".{1,}" + "$");
    private static final Pattern PASSWORD_LETTERS = Pattern.compile("^" + "(?=.*[a-z,а-я])" + "(?=.*[A-Z,А-Я])" + ".{1,}" + "$");
    private static final Pattern PASSWORD_SPACE= Pattern.compile("^" + "(?=\\S+$)" + ".{1,}" + "$");
    private static final Pattern PASSWORD_COUNT= Pattern.compile("^" + ".{6,25}" + "$");
    private int red = Color.RED;
    public boolean validateEmail (TextInputLayout textInputEmail, EditText mail) {
        String email = mail.getText().toString();
        mail.setHintTextColor(red);
        if (email.isEmpty()){
            textInputEmail.setError("Поле должно быть заполнено");
            return false; } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            textInputEmail.setError("Введите корректный емайл");
            return false;
        } else {
            textInputEmail.setError(null);
            return true;
        }
    } 
    public boolean validatePassword (TextInputLayout textInputPass, EditText pass) {
        String password = pass.getText().toString();
        pass.setHintTextColor(red);
        if (password.isEmpty()){
            textInputPass.setError("Поле должно быть заполнено");
            return false;
        }
        else if (!PASSWORD_DIGIT.matcher(password).matches()){
            textInputPass.setError("Пароль должен содержать хотя бы одну цифру");
            return false;
        }
        else if (!PASSWORD_LETTERS.matcher(password).matches()){
            textInputPass.setError("Пароль должен содержать заглавные и строчные буквы");
            return false;
        }
        else if (!PASSWORD_SPACE.matcher(password).matches()){
            textInputPass.setError("Пароль НЕ должен содержать пробелы");
            return false;
        }
        else if (!PASSWORD_COUNT.matcher(password).matches()){
            textInputPass.setError("Пароль должен содержать от 6 до 25 символов");
            return false;
        }
        else {
            textInputPass.setError(null);
            return true;
        }
    }
    public boolean validateRepeatPassword (TextInputLayout textInputRepeatPass, EditText pass, EditText repeatPass) {
        String repeatPassword = repeatPass.getText().toString();
        String password = pass.getText().toString();
        repeatPass.setHintTextColor(red);
        if (repeatPassword.isEmpty()){
            textInputRepeatPass.setError("Поле должно быть заполнено");
            return false;
        }else if (!repeatPassword.equals(password)){
            textInputRepeatPass.setError("Пароли не совпадают");
            return false;
        }
        else {
            textInputRepeatPass.setError(null);
            return true;
        }
    }


}