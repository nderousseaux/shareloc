package com.example.shareloc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;

public class Authentication extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);

        FragmentManager manager = getSupportFragmentManager();

        // No token, start signin
        if(token == null || token.isEmpty()) {
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.frameLayout, new FragSignin());
            transaction.commit();
        }

        // Check the token, start whoami
        else {
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.frameLayout, new FragWhoami());
            transaction.commit();
        }
    }
}