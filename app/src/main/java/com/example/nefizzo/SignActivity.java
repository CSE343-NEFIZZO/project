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
                if (usernametxt.matches("")) {
                    Toast.makeText(SignActivity.this, "You did not enter a username", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignActivity.this, SignActivity.class));
                    return ;
                }
                if (nametxt.matches("")) {
                    Toast.makeText(SignActivity.this, "You did not enter a name", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignActivity.this, SignActivity.class));
                    return ;
                }
                if (surnametxt.matches("")) {
                    Toast.makeText(SignActivity.this, "You did not enter a surname", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignActivity.this, SignActivity.class));
                    return ;
                }
                if (mailtxt.matches("")) {
                    Toast.makeText(SignActivity.this, "You did not enter a mail", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignActivity.this, SignActivity.class));
                    return ;
                }
                if (passwordtxt1.matches("")) {
                    Toast.makeText(SignActivity.this, "You did not enter a password", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignActivity.this, SignActivity.class));
                    return ;
                }
                if (passwordtxt2.matches("")) {
                    Toast.makeText(SignActivity.this, "You did not enter a password", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignActivity.this, SignActivity.class));
                    return ;
                }

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
                        usernameList.clear();
                        for (DataSnapshot ds: snapshot.getChildren()){
                            String txt = ""+ds.child("username").getValue().toString();
                            usernameList.add(txt);
                        }

                        for(int x = 0 ; x < usernameList.size() ; x++){
                            //if username is invalid
                            Log.i("bak", usernameList.get(x));
                            if(usernameList.get(x).equals(usernametxt)) {
                                isFind = 1;
                            }
                        }
                        if(isFind == 0){
                            if(passwordsFit(passwordtxt1,passwordtxt2) == 0){
                                Toast.makeText(SignActivity.this,"Your passwords not equal", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(SignActivity.this, SignActivity.class));
                            }
                            if(passwordsFit(passwordtxt1,passwordtxt2) == 1){
                                mAuth.createUserWithEmailAndPassword(mailtxt,passwordtxt1).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        Member member = new Member();
                                        member.setMailAddress(mailtxt);
                                        member.setName(nametxt);
                                        member.setPassword(passwordtxt1);
                                        member.setSurname(surnametxt);
                                        member.setUsername(usernametxt);
                                        member.setGender(gender);
                                        signRef.child(usernametxt).setValue(member);
                                        startActivity(new Intent(SignActivity.this, LoginScreen.class));
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                if (isFind == 1){
                    Toast.makeText(SignActivity.this,"Username invalid", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(SignActivity.this, SignActivity.class));
                }

            }
        });

    }


}