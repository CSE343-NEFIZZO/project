package com.example.nefizzo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginScreen extends AppCompatActivity {

    Button loginButton,forgotButton,createAccountButton;
    EditText mail,password;
    String mailtxt,passwordtxt;
    private FirebaseAuth mAuth;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        mAuth = FirebaseAuth.getInstance();
        tanimla();
        goNewActivity();
    }

    public void tanimla(){
            loginButton = findViewById(R.id.loginButton);
            forgotButton = findViewById(R.id.forgotButton);
            createAccountButton = findViewById(R.id.createAccountButton);
            mail = findViewById(R.id.mailEditText);
            password = findViewById(R.id.passwordEditText);
    }

    public void degerAl(){
            mailtxt = mail.getText().toString();
            passwordtxt = password.getText().toString();
    }

    public void goNewActivity(){
            forgotButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goResetScreen();
                }
            });
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goMainScreen();
                }
            });
            createAccountButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goSignScreen();
                }
            });
    }

    public void goResetScreen(){
                Intent intent = new Intent(getApplicationContext(),ResetPassword.class);
                startActivity(intent);
    }

    public int control(){
        if (mailtxt.matches("")) {
            Toast.makeText(LoginScreen.this, "You did not enter a mail", Toast.LENGTH_SHORT).show();
            mail.setError("Please enter your mail address");
            return 1;
        }
        if (passwordtxt.matches("")) {
            Toast.makeText(LoginScreen.this, "You did not enter a password", Toast.LENGTH_SHORT).show();
            password.setError("Please enter your password");
            return 1;
        }
        return 0;
    }

    public void goMainScreen(){
                degerAl();
                user = mAuth.getCurrentUser();
                int missingInfo = control();
                if(missingInfo == 0){
                    mAuth.signInWithEmailAndPassword(mailtxt, passwordtxt).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(LoginScreen.this, "You login Succesfully", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), Profile.class);
                            startActivity(intent);
                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginScreen.this, e.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }

    }

    public void goSignScreen(){
                Intent intent = new Intent(getApplicationContext(),SignActivity.class);
                startActivity(intent);
    }

}