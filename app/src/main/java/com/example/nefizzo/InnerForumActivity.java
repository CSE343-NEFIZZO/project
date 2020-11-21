package com.example.nefizzo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class InnerForumActivity extends AppCompatActivity {
    static List<InnerForumModel> list;
    InnerForumAdapter adp;

    ListView listView;
    Button sendButton, prevPageButton, nextPageButton, likeButton, dislikeButton;
    ImageView forumImage;
    TextView caption, title;
    String forumTitle, username, comment;
    DatabaseReference forumRef;
    EditText commentEdtTxt;

    static List<String> names = new ArrayList<>();
    static List<String> comments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inner_forum);
        define();
        getValue();
        setValue();
        fillList();

        click();

    }



    private void setValue() {
        forumRef = FirebaseDatabase.getInstance().getReference("Forums/" + forumTitle);
        forumRef.child("caption").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                caption.setText(snapshot.getValue().toString());
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
                    Toast.makeText(getApplicationContext(), "Comment sent.", Toast.LENGTH_LONG).show();
                    forumRef = FirebaseDatabase.getInstance().getReference("Forums/" + forumTitle + "/comments");
                    InnerForumModel temp = new InnerForumModel(username, comment);
                    forumRef.child(comment).setValue(temp);
                    commentEdtTxt.setText("");
                    //////////////////////////devam edilecek
                }
                if (comment.equals("")) {
                    Toast.makeText(getApplicationContext(), "Empty comment is not valid.", Toast.LENGTH_LONG).show();
                }


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
        list = new ArrayList<InnerForumModel>();
        list.clear();
    //    names.clear();
    //    comments.clear();   // düzeltilecek - 2 tane yazdırıyor

        forumRef = FirebaseDatabase.getInstance().getReference("Forums").child(forumTitle).child("comments");
        forumRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String cmnt = ds.child("comment").getValue().toString();
                    String name = ds.child("name").getValue().toString();

                    names.add(name);
                    comments.add(cmnt);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        for(int i=0; i<names.size();++i){
            list.add(new InnerForumModel(names.get(i),comments.get(i)));
        }
        Log.i("asd", String.valueOf(list.size()));
        adp = new InnerForumAdapter(list, InnerForumActivity.this);
        adp.notifyDataSetChanged();
        listView.setAdapter(adp);

    }

    private void define() {
        listView = (ListView) findViewById(R.id.forumList);
        sendButton = (Button) findViewById(R.id.sendButton);
        prevPageButton = (Button) findViewById(R.id.prevPageButton);
        nextPageButton = (Button) findViewById(R.id.nextPageButton);
        likeButton = (Button) findViewById(R.id.likeButton);
        dislikeButton = (Button) findViewById(R.id.dislikeButton);
        forumImage = (ImageView) findViewById(R.id.forumImage);
        caption = (TextView) findViewById(R.id.caption);
        title = (TextView) findViewById(R.id.forumTitle);
        commentEdtTxt = (EditText) findViewById(R.id.comment);
    }

    private void getValue() {
        Bundle intent = getIntent().getExtras();
        username = intent.getString("userName");
        forumTitle = intent.getString("forumTitle");
        title.setText(forumTitle);
    }

}