package com.example.lovify.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.lovify.R;
import com.example.lovify.auth.LogReg;
import com.example.lovify.model.User;
import com.example.lovify.utils.DBUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class Profile extends Fragment {

    ImageView profilepic;
    TextView username,email;
    Button updateProfileBtn, logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        updateProfileBtn = view.findViewById(R.id.goToUpdateProfile);
        logout = view.findViewById(R.id.logout);
        profilepic = view.findViewById(R.id.profilepic);
        username = view.findViewById(R.id.usernameTxtBox);
        email = view.findViewById(R.id.emailTxtBox);

        DBUtils.currentUserDetails().addOnCompleteListener(new OnCompleteListener<User>() {
            @Override
            public void onComplete(@NonNull Task<User> task) {
                if (task.isSuccessful()) {
                    User user = task.getResult();
                    username.setText(user.getName());
                    email.setText(user.getEmail());
                    if (user.getImageURL() == null) {
                        profilepic.setImageResource(R.drawable.ic_profile);
                    } else {
                        Picasso.get().load(user.getImageURL()).into(profilepic);
                    }
                } else {
                    // Handle the error
                }
            }
        });

        updateProfileBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), EditProfile.class);
            startActivity(intent);
        });

        logout.setOnClickListener(v -> {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.signOut();
            Intent i = new Intent(getContext(), LogReg.class);
            startActivity(i);
        });

        return view;
    }
}