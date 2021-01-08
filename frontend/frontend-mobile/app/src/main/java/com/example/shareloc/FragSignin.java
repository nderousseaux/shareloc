package com.example.shareloc;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import static com.example.shareloc.Api.signin;

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
        String username = ((EditText)viewSignin.findViewById(R.id.edTEmail)).getText().toString();
        String password = ((EditText)viewSignin.findViewById(R.id.edtPassword)).getText().toString();

        //On connecte l'utilisateur
        try{
            signin(username, password, viewSignin);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    protected void clickSignup() {
        FragmentManager manager = getParentFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, new FragSignup());
        transaction.commit();
    }
}