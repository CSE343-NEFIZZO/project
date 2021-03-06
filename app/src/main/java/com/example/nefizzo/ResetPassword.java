package com.example.nefizzo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {

    EditText mailAddress;
    Button sendButton;
    String mail;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        defineValues();
        goMainScreen();
    }

    public void defineValues(){
            mailAddress = findViewById(R.id.mailAddressEditText);
            sendButton = findViewById(R.id.sendButton);
    }

    public void setValue(){
          mail = mailAddress.getText().toString();
    }

    // 1 -> missinginfo
    public int control(){
        if (mail.matches("")) {
            Toast.makeText(ResetPassword.this, "You did not enter a mail address", Toast.LENGTH_SHORT).show();
            mailAddress.setError("please enter your mail address");
            return 1;
        }
        return 0;
    }

    public void goMainScreen(){

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue();
                auth = FirebaseAuth.getInstance();
                int missingInfo = control();
                if(missingInfo == 0){
                    if (TextUtils.isEmpty(mail)) {
                        Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    auth.sendPasswordResetEmail(mail)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ResetPassword.this, "We have sent you instructions to reset your password!", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(getApplicationContext(),LoginScreen.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(ResetPassword.this, "Failed to send reset email!", Toast.LENGTH_LONG).show();
                                    }

                                }
                            });
                }



            }
        });

    }
}