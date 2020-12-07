package com.example.nefizzo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class InnerForumActivity extends AppCompatActivity {
    List<InnerForumModel> list;
    InnerForumAdapter adp;

    ListView listView;
    Button sendButton, likeButton, dislikeButton;
    ImageView forumImage;
    TextView caption, title;
    String forumTitle, username, comment;
    DatabaseReference forumRef,usersRef;
    EditText commentEdtTxt;
    FirebaseUser user;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inner_forum);
        define();
        getValue();
        setCaption();
        fillList();
        click();

    }

    private void setCaption() {
        forumRef = FirebaseDatabase.getInstance().getReference("Forums/" + forumTitle);
        forumRef.child("caption").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue() != null)
                caption.setText(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        forumRef = FirebaseDatabase.getInstance().getReference("Forums/" + forumTitle);
        forumRef.child("imageUrl").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null && !snapshot.getValue().toString().equals("empty"))
                    Picasso.get().load(snapshot.getValue().toString()).into(forumImage);
                else
                    forumImage.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void click() {
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment = commentEdtTxt.getText().toString();
                if (!comment.isEmpty()) {
                    usersRef = FirebaseDatabase.getInstance().getReference("Members");
                    usersRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds: snapshot.getChildren()){
                                if(user.getEmail().toString().equals(ds.child("mailAddress").getValue().toString())){
                                    username = ds.child("username").getValue().toString();
                                    Toast.makeText(getApplicationContext(), "Comment sent.", Toast.LENGTH_LONG).show();
                                    forumRef = FirebaseDatabase.getInstance().getReference("Forums/" + forumTitle + "/comments");
                                    InnerForumModel temp = new InnerForumModel(username, comment);
                                    forumRef.child(comment).setValue(temp);
                                    commentEdtTxt.setText("");
                                }

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                if (comment.equals("")) {
                    Toast.makeText(getApplicationContext(), "Empty comment is not allowed.", Toast.LENGTH_LONG).show();
                }
                fillList();
            }
        });

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "clicked like", Toast.LENGTH_LONG).show();
            }
        });

        dislikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "clicked dislike", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void fillList() {
        forumRef = FirebaseDatabase.getInstance().getReference("Forums").child(forumTitle).child("comments");
        forumRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String cmnt = ds.child("comment").getValue().toString();
                    String name = ds.child("name").getValue().toString();
                    list.add(new InnerForumModel(name, cmnt));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                adp = new InnerForumAdapter(list, InnerForumActivity.this);
                listView.setAdapter(adp);
            }
        }, 10);

    }

    private void define() {
        listView = (ListView) findViewById(R.id.forumList);
        sendButton = (Button) findViewById(R.id.sendButton);
        likeButton = (Button) findViewById(R.id.likeButton);
        dislikeButton = (Button) findViewById(R.id.dislikeButton);
        forumImage = (ImageView) findViewById(R.id.forumImage);
        caption = (TextView) findViewById(R.id.caption);
        title = (TextView) findViewById(R.id.forumTitle);
        commentEdtTxt = (EditText) findViewById(R.id.comment);
        list = new ArrayList<InnerForumModel>();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
    }

    private void getValue() {
        Bundle intent = getIntent().getExtras();
        if(intent != null) {
            forumTitle = intent.getString("forumTitle");
            title.setText(forumTitle);
        }
        else{
            forumTitle = "";
            caption.setText("");
        }
    }

}