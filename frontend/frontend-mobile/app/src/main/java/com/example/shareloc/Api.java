package com.example.shareloc;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import static android.content.Context.MODE_PRIVATE;

public class Api {

    public static String SERVER_URL = "http://192.168.0.11:8080/lp1_shareloc_dm/shareloc_api/";

    /*
    public static void signinJson (String email, String password, View v) {
        RequestQueue queue = Volley.newRequestQueue(v.getContext());
        String url = SERVER_URL + "signin?email=" + email + "&password=" + password;

        Log.e("url", url);

        //On construit la requète. Si tout vas bien, on affiche les données
        JsonObjectRequest signinRequest = new JsonObjectRequest(Request.Method.POST, url, null, response -> {
            Log.e("success Response", response.toString());
        }, error -> {
            Log.e("error Response", error.toString());
            // com.android.volley.ParseError: org.json.JSONException: Value eyJhbGci.... of type java.lang.String cannot be converted to JSONObject
            // L'appli reçois bien le token en reponse, SAUF QUE :
            // - Volley passe dans le cas d'erreur
            // - Il considère que la réponse est un String alors que le serveur est supposé renvoyé du JSON
        });
        queue.add(signinRequest);
    }

    public static void signinString (String email, String password, View v) {
        SharedPreferences sharedPreferences = v.getContext().getSharedPreferences("user", MODE_PRIVATE);
        RequestQueue queue = Volley.newRequestQueue(v.getContext());
        String url = SERVER_URL + "signin?email=" + email + "&password=" + password;

        Log.e("url", url);

        //On construit la requète. Si tout vas bien, on affiche les données
        StringRequest signinRequestString = new StringRequest(Request.Method.POST, url, response -> {
            Log.e("success Response", response);
            sharedPreferences.edit().putString("token", response).apply();
            //Problème : notifier le fragment qu'on a la réponse, et attendre la réponse dans la fragment...
        }, error -> {
            Log.e("error Response", error.toString());
            sharedPreferences.edit().putString("token", null).apply();
        });
        queue.add(signinRequestString);
    }
    */
}
