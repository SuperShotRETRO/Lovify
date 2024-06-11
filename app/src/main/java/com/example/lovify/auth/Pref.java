package com.example.lovify.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lovify.R;

import java.util.HashMap;

public class Pref extends AppCompatActivity {


    Button maleBtn, femaleBtn, nextBtn2;

    HashMap<String,Object> data = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pref);

        maleBtn = findViewById(R.id.maleBtn);
        femaleBtn = findViewById(R.id.femaleBtn);
        nextBtn2 = findViewById(R.id.nextBtn2);

        Intent intent = getIntent();
        data = (HashMap<String, Object>)intent.getSerializableExtra("data");

        maleBtn.setOnClickListener(v -> {
            data.put("Pref Gender","Male");
            Intent i = new Intent(this, MusicPref.class);
            i.putExtra("data",data);
            startActivity(i);
        });

        femaleBtn.setOnClickListener(v -> {
            data.put("Pref Gender","Female");
            Intent i = new Intent(this, MusicPref.class);
            i.putExtra("data",data);
            startActivity(i);
        });

    }

}