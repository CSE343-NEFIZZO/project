package com.example.nefizzo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class AddForum extends AppCompatActivity {

    EditText forumTitleEdt;
    EditText captionEdt;
    Button addBtn;
    ImageButton addImageBtn;
    String forumTitle, caption, username;
    DatabaseReference forumRef, usersRef;
    StorageReference storageReference;
    FirebaseStorage firebaseStorage;
    Uri filePath;
    int isFind = 0;

    FirebaseUser user;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_forum);
        define();
        click();
    }

    private void define() {
        forumTitleEdt = (EditText) findViewById(R.id.forumTitleEdtTxt);
        captionEdt = (EditText) findViewById(R.id.forumCaptionEdtTxt);
        addBtn = (Button) findViewById(R.id.addNewFrmBtn);
        addImageBtn = (ImageButton) findViewById(R.id.addPhotoBtn);
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
    }

    private void click() {
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forumTitle = forumTitleEdt.getText().toString();
                caption = captionEdt.getText().toString();
                if ((!forumTitle.isEmpty()) && (!caption.isEmpty())) {
                    forumRef = FirebaseDatabase.getInstance().getReference("Forums");
                    forumRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            List<String> forumTitleList = new ArrayList<>();
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                String txt = "" + ds.child("forumTitle").getValue().toString();
                                forumTitleList.add(txt);
                            }

                            for (int i = 0; i < forumTitleList.size(); i++) {
                                if (forumTitleList.get(i).equals(forumTitle)) {
                                    isFind = 1;
                                }
                            }

                            if (isFind == 0) {
                                if (filePath != null) {  //************************* eğer galeriden foto seçildiyse*************************
                                    forumTitle = forumTitleEdt.getText().toString();
                                    String imageTitle = forumTitle.replaceAll(" ", "-");
                                    StorageReference ref = storageReference.child("ForumImages").child(imageTitle + ".jpg");
                                    ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            Toast.makeText(AddForum.this, "Forum created!", Toast.LENGTH_LONG).show();
                                            Task<Uri> temp = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                                            while (!temp.isComplete()) ;
                                            String url = temp.getResult().toString();
                                            usersRef = FirebaseDatabase.getInstance().getReference("Members");
                                            usersRef.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    for (DataSnapshot ds : snapshot.getChildren()) {
                                                        if (user.getEmail().toString().equals(ds.child("mailAddress").getValue().toString())) {
                                                            username = ds.child("username").getValue().toString();
                                                            OuterForumModel newForum = new OuterForumModel(forumTitle, username, caption, url);
                                                            forumRef.child(forumTitle).setValue(newForum);
                                                            break;
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });
                                        }
                                    });
                                } else {
                                    usersRef = FirebaseDatabase.getInstance().getReference("Members");
                                    usersRef.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for (DataSnapshot ds : snapshot.getChildren()) {
                                                if (user.getEmail().toString().equals(ds.child("mailAddress").getValue().toString())) {
                                                    username = ds.child("username").getValue().toString();
                                                    Toast.makeText(AddForum.this, "Forum created!", Toast.LENGTH_LONG).show();
                                                    OuterForumModel newForum = new OuterForumModel(forumTitle, username, caption, "empty");
                                                    forumRef.child(forumTitle).setValue(newForum);
                                                    break;
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }
                                Intent intent = new Intent(AddForum.this, OuterForumActivity.class);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    if (isFind == 1) {
                        Toast.makeText(AddForum.this, "This forum title already exists!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(AddForum.this, AddForum.class);
                        startActivity(intent);
                    }
                } else if (forumTitle.isEmpty()) {
                    Toast.makeText(AddForum.this, "Enter a forum title!", Toast.LENGTH_LONG).show();
                } else if (caption.isEmpty()) {
                    Toast.makeText(AddForum.this, "Enter a forum caption!", Toast.LENGTH_LONG).show();
                }
            }
        });

        addImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
                click();
            }
        });

    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1903);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1903 && resultCode == Activity.RESULT_OK) {
            filePath = data.getData();
        }
    }


}