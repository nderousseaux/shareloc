package com.example.shareloc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import static com.example.shareloc.Api.signin;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.btnSignin).setOnClickListener(view -> clickLogin(view));

        findViewById(R.id.btnSignup).setOnClickListener(view -> clickSignup());
    }

    public void clickLogin(View view){
        String username = ((EditText)findViewById(R.id.edTEmail)).getText().toString();
        String password = ((EditText)findViewById(R.id.edtPassword)).getText().toString();


        //On connecte l'utilisateur
        try{
            signin(username, password, view);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    protected void clickSignup(){
        //On crée une activité
        Intent signup = new Intent(Login.this, Signup.class);
        startActivity(signup);
    }
}
