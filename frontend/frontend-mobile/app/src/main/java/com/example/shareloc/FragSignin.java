package com.example.shareloc;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import static android.content.Context.MODE_PRIVATE;
import static com.example.shareloc.Api.SERVER_URL;

public class FragSignin extends Fragment {

    private View viewSignin;

    public FragSignin() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewSignin = inflater.inflate(R.layout.frag_signin, container, false);

        viewSignin.findViewById(R.id.btnSignin).setOnClickListener(view -> clickSignin());
        viewSignin.findViewById(R.id.btnSignup).setOnClickListener(view -> clickSignup());

        return viewSignin;
    }

    public void clickSignin() {
        String email = ((EditText)viewSignin.findViewById(R.id.edtEmail)).getText().toString();
        String password = ((EditText)viewSignin.findViewById(R.id.edtPassword)).getText().toString();

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("user", MODE_PRIVATE);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = SERVER_URL + "signin?email=" + email + "&password=" + password;

        StringRequest signinRequest = new StringRequest(Request.Method.POST, url, response -> {
            Log.e("success signin", response);
            sharedPreferences.edit().putString("token", response).apply();

            // On redirige sur whoami pour tester le token et récupérer les informations de l'utilisateur (nom, prénom, ...)
            FragmentManager manager = getParentFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.frameLayout, new FragWhoami());
            transaction.commit();

        }, error -> {
            Log.e("error signin", error.toString());
            sharedPreferences.edit().putString("token", null).apply();

            // On affiche un message d'erreur
            Toast.makeText(getContext(), "Error : Wrong email or password", Toast.LENGTH_SHORT).show();
        });
        queue.add(signinRequest);
    }

    protected void clickSignup() {
        FragmentManager manager = getParentFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, new FragSignup());
        transaction.commit();
    }
}