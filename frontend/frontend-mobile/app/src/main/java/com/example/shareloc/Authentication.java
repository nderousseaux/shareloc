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

        // Check the token
        else {
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.frameLayout, new FragStart());
            transaction.commit();

            RequestQueue queue = Volley.newRequestQueue(this);
            String url = SERVER_URL + "whoami";
            Log.e("url", url);

            JsonObjectRequest whoamiRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
                Log.e("success Response", response.toString());

                // On stocke les informations de l'utilisateur
                try {
                    User.getInstance().setUser(response.getString("login"), response.getString("lastname"),response.getString("firstname"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // On démarre l'activité principale
                Intent mainActivity = new Intent(Authentication.this, MainActivity.class);
                startActivity(mainActivity);
            }, error -> {
                Log.e("error Response", error.toString());

                // On affiche un message d'erreur
                Toast.makeText(this, "Error : Authentication failed", Toast.LENGTH_SHORT).show();

                // On affiche signin
                FragmentTransaction transactionBis = manager.beginTransaction();
                transactionBis.replace(R.id.frameLayout, new FragSignin());
                transactionBis.commit();
            }) {
                // On met le token dans le header de la requête
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Bearer " + token);
                    return headers;
                }
            };
            queue.add(whoamiRequest);
        }
    }
}