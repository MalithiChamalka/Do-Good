package com.example.dogood;

import android.util.Patterns;

public class emailValidate {

    public static boolean isValidEmail(CharSequence target) {
        return Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

}
