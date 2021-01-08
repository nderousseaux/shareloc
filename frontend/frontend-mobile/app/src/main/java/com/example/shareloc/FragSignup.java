package com.example.shareloc;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static com.example.shareloc.Api.signup;

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
        //On crée l'utilisateur
        try{
            signup();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        FragmentManager manager = getParentFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, new FragSignin());
        transaction.commit();
    }

    public void clickSignin() {
        FragmentManager manager = getParentFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, new FragSignin());
        transaction.commit();
    }
}