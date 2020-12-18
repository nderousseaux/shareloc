package com.example.shareloc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import static com.example.shareloc.api.signup;

public class Signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        findViewById(R.id.btnSignup).setOnClickListener(view -> clickSignup());

        findViewById(R.id.btnSignin).setOnClickListener(view -> clickSignin());
    }

    public void clickSignin(){
        //On crée une activité
        Intent login = new Intent(Signup.this, Login.class);
        startActivity(login);
    }

    protected void clickSignup(){
        //On crée l'utilisateur
        try{
            signup();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        //On crée une activité
        Intent login = new Intent(Signup.this, Login.class);
        startActivity(login);
    }
}