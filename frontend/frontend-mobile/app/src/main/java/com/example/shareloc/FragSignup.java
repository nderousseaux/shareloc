package com.example.shareloc;

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

import static com.example.shareloc.Api.SERVER_URL;

public class FragSignup extends Fragment {

    private View viewSignup;

    public FragSignup() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewSignup = inflater.inflate(R.layout.frag_signup, container, false);

        viewSignup.findViewById(R.id.btnSignup).setOnClickListener(view -> clickSignup());
        viewSignup.findViewById(R.id.btnSignin).setOnClickListener(view -> clickSignin());

        return viewSignup;
    }

    protected void clickSignup() {
        String firstname = ((EditText)viewSignup.findViewById(R.id.edtFirstname)).getText().toString();
        String lastname = ((EditText)viewSignup.findViewById(R.id.edtLastname)).getText().toString();
        String email = ((EditText)viewSignup.findViewById(R.id.edtEmail)).getText().toString();
        String password = ((EditText)viewSignup.findViewById(R.id.edtPassword)).getText().toString();

        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = SERVER_URL + "signup?email=" + email + "&lastname=" +  lastname + "&password=" + password + "&firstname=" + firstname;
        Log.e("url", url);

        StringRequest signupRequest = new StringRequest(Request.Method.PUT, url, response -> {
            Log.e("success Response", response);

            // On affiche un message pour dire c'est bon et on redirige sur signin
            Toast.makeText(getContext(), "Account created, now signin", Toast.LENGTH_SHORT).show();
            FragmentManager manager = getParentFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.frameLayout, new FragSignin());
            transaction.commit();

        }, error -> {
            Log.e("error Response", error.toString());

            // On affiche un message d'erreur
            Toast.makeText(getContext(), "Error : Email address is already used", Toast.LENGTH_SHORT).show();
        });
        queue.add(signupRequest);
    }

    public void clickSignin() {
        FragmentManager manager = getParentFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, new FragSignin());
        transaction.commit();
    }
}