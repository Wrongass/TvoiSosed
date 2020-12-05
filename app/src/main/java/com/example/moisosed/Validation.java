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
    private static final Pattern PHONE_COUNT= Pattern.compile("^" + ".{11,20}" + "$");
    private static final Pattern ONLY_NUMBERS= Pattern.compile("^[0-9]*$");
    private static final Pattern ONLY_LETTERS = Pattern.compile("^[A-Za-zА-Яа-я]+$");
    private static final Pattern NAME_COUNT= Pattern.compile("^" + ".{2,30}" + "$");


    private int red = Color.RED;
    public boolean validateEmail (TextInputLayout emailError, EditText mail) {
        String email = mail.getText().toString();
        mail.setHintTextColor(red);
        if (email.isEmpty()){
            emailError.setError("Поле должно быть заполнено");
            return false; } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailError.setError("Введите корректный емайл");
            return false;
        } else {
            emailError.setError(null);
        }
        return true;
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
        }
        return true;
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
        }
        return true;
    }

    public boolean validatePhoneNumber (TextInputLayout textInputPhones, EditText phone) {
        String phoneNumber = phone.getText().toString();
        phone.setHintTextColor(red);
        if (phoneNumber.isEmpty()) {
            textInputPhones.setError("Поле должно быть заполнено");
            return false;
        } else if (!PASSWORD_SPACE.matcher(phoneNumber).matches()) {
            textInputPhones.setError("Номер НЕ должен содержать пробелы");
            return false;
        } else if (!ONLY_NUMBERS.matcher(phoneNumber).matches()) {
            textInputPhones.setError("Номер должен содержать только цифры");
            return false;
        } else if (!PHONE_COUNT.matcher(phoneNumber).matches()) {
            textInputPhones.setError("Номер должен содержать от 11 до 20 символов");
            return false;
        }
        textInputPhones.setError(null);
        return true;
    }

    public boolean validateName (TextInputLayout name_error_edit_profiles, EditText edit_profile_name) {
        String name = edit_profile_name.getText().toString();
        edit_profile_name.setHintTextColor(red);
        if (name.isEmpty()) {
            name_error_edit_profiles.setError("Поле должно быть заполнено");
            return false;
        } else if (!PASSWORD_SPACE.matcher(name).matches()) {
            name_error_edit_profiles.setError("Имя НЕ должно содержать пробелы");
            return false;
        } else if (!ONLY_LETTERS.matcher(name).matches()) {
            name_error_edit_profiles.setError("Имя должно содержать только буквы");
            return false;
        } else if (!NAME_COUNT.matcher(name).matches()) {
            name_error_edit_profiles.setError("Имя должно содержать от 2 до 30 символов");
            return false;
        }
        name_error_edit_profiles.setError(null);
        return true;
    }

    public boolean validateEmptyData (TextInputLayout emptyError, EditText emptyString) {
        String empty = emptyString.getText().toString();
        emptyString.setHintTextColor(red);
        if (empty.isEmpty()) {
            emptyError.setError("Поле должно быть заполнено");
            return false;
        }
        emptyError.setError(null);
        return true;
    }


}