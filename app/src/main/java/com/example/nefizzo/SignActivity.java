package com.example.nefizzo;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class SignActivity extends AppCompatActivity {

    RadioGroup radiogroupGender;
    Button signButton;
    EditText username,name,surname,password1,password2,mail;
    String usernametxt,nametxt,surnametxt,passwordtxt1,passwordtxt2,mailtxt,gender;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    DatabaseReference signRef;
    public int isFind = 0;
    public int isFindMail = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        mAuth = FirebaseAuth.getInstance();
        defineValues();
        sign();
    }

    public void defineValues(){
        radiogroupGender = findViewById(R.id.gender);
        signButton = findViewById(R.id.signButton);
        username = findViewById(R.id.usernameEditText);
        name = findViewById(R.id.nameEditText);
        surname = findViewById(R.id.surnameEditText);
        password1 = findViewById(R.id.password1);
        password2 = findViewById(R.id.password2);
        mail = findViewById(R.id.mailEditText);
    }


    public int passwordsFit(String pass1, String pass2){
        if(pass1.equals(pass2))
            return 1;
        else
            return 0;
    }

    //1 -> eksik bilgi var
    public int control(){
        if (usernametxt.matches("")) {
            Toast.makeText(SignActivity.this, "You did not enter a username", Toast.LENGTH_SHORT).show();
            username.setError("Please enter a username");
            return 1;
        }
        if (nametxt.matches("")) {
            Toast.makeText(SignActivity.this, "You did not enter a name", Toast.LENGTH_SHORT).show();
            name.setError("Please enter a name");
            return 1;
        }
        if (surnametxt.matches("")) {
            Toast.makeText(SignActivity.this, "You did not enter a surname", Toast.LENGTH_SHORT).show();
            surname.setError("Please enter a surname");
            return 1;
        }
        if (mailtxt.matches("")) {
            Toast.makeText(SignActivity.this, "You did not enter a mail", Toast.LENGTH_SHORT).show();
            mail.setError("Please enter a mail address");
            return 1;
        }
        if (passwordtxt1.matches("")) {
            Toast.makeText(SignActivity.this, "You did not enter a password", Toast.LENGTH_SHORT).show();
            password1.setError("Please enter a password");
            return 1;
        }
        if (passwordtxt2.matches("")) {
            Toast.makeText(SignActivity.this, "You did not enter a password", Toast.LENGTH_SHORT).show();
            password2.setError("Please enter a password");
            return 1;
        }
        return 0;
    }

    public int test_true_format_mail(String mail){
        if(mail.contains("@hotmail.com")){
            return 1;
        }
        if(mail.contains("@gmail.com")){
            return 1;
        }
        return 0;
    }

    public void setTextElif(String usernamePar,String namePar,String surnamePar,String mailPar,String passPar1,String passPar2){
        username.setText(usernamePar);
        name.setText(namePar);
        surname.setText(surnamePar);
        mail.setText(mailPar);
        password1.setText(passPar1);
        password2.setText(passPar2);
    }


    public void sign(){
        user = mAuth.getCurrentUser();
        signButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                usernametxt = username.getText().toString();
                nametxt = name.getText().toString();
                surnametxt = surname.getText().toString();
                mailtxt = mail.getText().toString();
                passwordtxt1 = password1.getText().toString();
                passwordtxt2 = password2.getText().toString();

                // checks empty edit text is exist or not.
                int missingInfo = control();
                if(missingInfo == 0){
                    int gelenid = radiogroupGender.getCheckedRadioButtonId();
                    if(gelenid == R.id.maleButton){
                        gender = "male";
                    }
                    else if(gelenid == R.id.femaleButton){
                        gender = "female";
                    }

                    signRef = FirebaseDatabase.getInstance().getReference("Members");
                    signRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            List<String> usernameList = new ArrayList<>();
                            List<String> mailList = new ArrayList<>();
                            mailList.clear();
                            usernameList.clear();
                            for (DataSnapshot ds: snapshot.getChildren()){
                                String txt = ""+ds.child("username").getValue().toString();
                                String buffermail = ""+ds.child("mailAddress").getValue().toString();
                                usernameList.add(txt);
                                mailList.add(buffermail);
                            }

                            for(int x = 0 ; x < usernameList.size() ; x++){
                                if(usernameList.get(x).equals(usernametxt)) {
                                    isFind = 1;
                                }
                                if(mailList.get(x).equals(mailtxt)) {
                                    isFindMail = 1;
                                }
                            }

                            if(isFind == 0 && isFindMail == 0){
                                if(passwordsFit(passwordtxt1,passwordtxt2) == 0){
                                    Toast.makeText(SignActivity.this,"Your passwords not equal", Toast.LENGTH_LONG).show();
                                    setTextElif(usernametxt,nametxt,surnametxt,mailtxt,"","");
                                }
                                if(passwordsFit(passwordtxt1,passwordtxt2) == 1){
                                    if(test_true_format_mail(mailtxt)==1){
                                        mAuth.createUserWithEmailAndPassword(mailtxt,passwordtxt1).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                            @Override
                                            public void onSuccess(AuthResult authResult) {
                                                Member member = new Member();
                                                member.setMailAddress(mailtxt);
                                                member.setName(nametxt);
                                                member.setSurname(surnametxt);
                                                member.setUsername(usernametxt);
                                                member.setGender(gender);
                                                signRef.child(usernametxt).setValue(member);
                                                startActivity(new Intent(SignActivity.this, LoginScreen.class));
                                            }
                                        });
                                    }
                                    else{
                                        Toast.makeText(SignActivity.this,"mail address is baddly formatted.", Toast.LENGTH_LONG).show();
                                        setTextElif(usernametxt,nametxt,surnametxt,"",passwordtxt1,passwordtxt2);
                                    }
                                }
                            }
                            if (isFind == 1){
                                username.setError("username has already token,please enter different.");
                                setTextElif("",nametxt,surnametxt,mailtxt,passwordtxt1,passwordtxt2);
                                isFind = 0;
                            }
                            if(isFindMail == 1){
                                mail.setError("mail has already token,please enter different.");
                                setTextElif(usernametxt,nametxt,surnametxt,"",passwordtxt1,passwordtxt2);
                                isFindMail = 0;
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }
        });

    }


}