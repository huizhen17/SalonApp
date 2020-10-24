package com.example.salonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void btnSignUp_onClick(View view) {
        Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void txtSignIn_onClick(View view) {
        Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
