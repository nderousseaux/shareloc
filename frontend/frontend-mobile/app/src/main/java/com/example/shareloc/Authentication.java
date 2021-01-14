package com.example.shareloc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.shareloc.models.User;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import static com.example.shareloc.Api.SERVER_URL;

public class Authentication extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);
        Log.e("token in sharedPrefs", token == null ? "null" : token);

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