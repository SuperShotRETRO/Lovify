package com.example.lovify.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lovify.R;

public class LogReg extends AppCompatActivity {

    TextView goToLoginPage;
    Button goToRegPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_log_reg);

        goToLoginPage = findViewById(R.id.goToLoginPage);
        goToRegPage = findViewById(R.id.goToRegPage);

        goToLoginPage.setOnClickListener(v->{
            Intent i = new Intent(LogReg.this, Login.class);
            startActivity(i);
        });

        goToRegPage.setOnClickListener(v->{
            Intent i = new Intent(LogReg.this, Register.class);
            startActivity(i);
        });
    }
}