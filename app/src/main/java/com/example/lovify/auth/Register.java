package com.example.lovify.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lovify.R;
import com.example.lovify.home.HomePage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.DataCollectionDefaultChange;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private Button regBtn;
    private ProgressBar pBar;
    private EditText regEmail,regPassword1,regPassword2;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        regBtn = findViewById(R.id.regBtn);
        pBar = findViewById(R.id.pBar2);
        regEmail = findViewById(R.id.regEmail);
        regPassword1 = findViewById(R.id.regPassword1);
        regPassword2 = findViewById(R.id.regPassword2);

        HashMap<String,Object> data = new HashMap<>();

        pBar.setVisibility(View.GONE);

        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                pBar.setVisibility(View.VISIBLE);
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user != null && user.isEmailVerified()){
                    Intent i = new Intent(Register.this, HomePage.class);
                    data.put("Email",regEmail.getText().toString());
                    i.putExtra("data",data);
                    startActivity(i);
                    finish();
                    pBar.setVisibility(View.GONE);
                }
                pBar.setVisibility(View.GONE);
            }
        };

        regBtn.setOnClickListener(v->{
            final String email = regEmail.getText().toString();
            final String password1 = regPassword1.getText().toString();
            final String password2 = regPassword2.getText().toString();

            if (!isValidEmail(email)) {
                Toast.makeText(Register.this, "Invalid email format", Toast.LENGTH_SHORT).show();
                return;
            }

            if(password1.equals(password2)){
                mAuth.createUserWithEmailAndPassword(email,password1).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(Register.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(Register.this,"Registered Successfully",Toast.LENGTH_SHORT).show();
                            String uid = mAuth.getCurrentUser().getUid();
                            Intent i = new Intent(Register.this,UserDetails.class);
                            data.put("Email",regEmail.getText().toString());
                            i.putExtra("data",data);
                            startActivity(i);
                        }
                    }
                });

            } else {
                Toast.makeText(Register.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }
}