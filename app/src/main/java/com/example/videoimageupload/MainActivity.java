package com.example.videoimageupload;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); ImageView addVideo,showVideo;

        addVideo = findViewById(R.id.addVideo);
        addVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAddActivity();
            }
        });

        showVideo = findViewById(R.id.showVideo);
        showVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToVideos();
            }
        });

    }

    public void goToAddActivity() {
        Intent goToAddActivityIntent = new Intent(MainActivity.this, UploadMedia.class);
        startActivity(goToAddActivityIntent);

    }

    public void goToVideos() {
        Intent goToVideosIntent = new Intent(MainActivity.this, VideosActivity.class);
        startActivity(goToVideosIntent);
    }

    public void logOutUser(View view) {
        FirebaseAuth.getInstance().signOut();
        updateUi(null);
    }

    private void updateUi(FirebaseUser user) {
        if (user != null) {
            //neccessary ui updates
        } else {
            Intent logOutUser = new Intent(MainActivity.this,Login.class);
            startActivity(logOutUser);
            finish();
        }
    }
}