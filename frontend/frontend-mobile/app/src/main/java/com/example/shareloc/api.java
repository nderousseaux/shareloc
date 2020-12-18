package com.example.shareloc;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class api { //TODO: En cours

    private static String SERVER_URL = "https://130.79.80.40:1568/lp1_shareloc_dm/shareloc_api/";

    public static void signin (String username, String password, View v) {

        RequestQueue queue = Volley.newRequestQueue(v.getContext());
        String url = SERVER_URL + "signin?email=" + username + "&password=" + password;

        Log.d("R", url);


        //On construit la requète. Si tout vas bien, on affiche les données
        JsonObjectRequest data = new JsonObjectRequest(Request.Method.POST, url, null, response -> {
            Log.d("Response", response.toString());
        }, error -> {
            Log.d("Error.Response", "error"); //TIMEOUT error
        });
        queue.add(data);

    }

    public static void signup () {

    }

}
