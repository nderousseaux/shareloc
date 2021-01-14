package com.example.shareloc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.shareloc.models.User;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static com.example.shareloc.Api.SERVER_URL;

public class FragWhoami extends Fragment {

    private View viewWhoami;

    public FragWhoami() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewWhoami = inflater.inflate(R.layout.frag_whoami, container, false);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("user", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);
        Log.e("token in sharedPrefs", token == null ? "null" : token);

        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = SERVER_URL + "whoami";

        JsonObjectRequest whoamiRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            Log.e("success Response", response.toString());

            // On stocke les informations de l'utilisateur
            try {
                User.getInstance().setUser(response.getString("email"), response.getString("lastname"),response.getString("firstname"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // On démarre l'activité principale
            Intent mainActivity = new Intent(getActivity(), MainActivity.class);
            startActivity(mainActivity);

        }, error -> {
            Log.e("error Response", error.toString());

            // On affiche un message d'erreur
            Toast.makeText(getContext(), "Error : Authentication failed", Toast.LENGTH_SHORT).show();

            // On affiche signin
            FragmentManager manager = getParentFragmentManager();
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

        return viewWhoami;
    }
}