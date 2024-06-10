package com.example.lovify.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lovify.R;
import com.example.lovify.home.HomePage;
import com.example.lovify.utils.DBUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MusicPref extends AppCompatActivity {

    Button finishBtn;

    HashMap<String,Object> data = new HashMap<>();

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_music_pref);

        Intent intent = getIntent();
        data = (HashMap<String, Object>)intent.getSerializableExtra("data");
        finishBtn = findViewById(R.id.finishBtn);

        mAuth = FirebaseAuth.getInstance();

        finishBtn.setOnClickListener(v ->{
            DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("User").child(DBUtils.currentUserId());
            currentUserDb.updateChildren(data);
            Intent i = new Intent(MusicPref.this, HomePage.class);
            startActivity(i);
        });
    }
}