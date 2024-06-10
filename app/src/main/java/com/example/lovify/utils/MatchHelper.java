package com.example.lovify.utils;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.lovify.R;
import com.example.lovify.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MatchHelper {

    private String uid;
    private String ugender;
    private String matches_gender;
    private DatabaseReference mDb = FirebaseDatabase.getInstance().getReference();
    private TaskCompletionSource<Void> initializationTask = new TaskCompletionSource<>();

    public MatchHelper() {
        uid = DBUtils.currentUserId();
        DBUtils.currentUserDetails().addOnCompleteListener(new OnCompleteListener<User>() {
            @Override
            public void onComplete(@NonNull Task<User> task) {
                if (task.isSuccessful()) {
                    User user = task.getResult();
                    ugender = user.getGender();
                    matches_gender = user.getPreferredGender();

                    initializationTask.setResult(null);
                } else {
                    initializationTask.setException(task.getException() != null ? task.getException() : new Exception("Error retrieving user details"));
                }
            }
        });
    }

    public Task<List<User>> findMatches() {
        TaskCompletionSource<List<User>> taskCompletionSource = new TaskCompletionSource<>();

        // Wait until the initialization is complete before proceeding
        initializationTask.getTask().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> initTask) {
                if (initTask.isSuccessful()) {
                    mDb.child("User").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (task.isSuccessful() && task.getResult().exists()) {
                                List<User> matches = new ArrayList<>();
                                List<Task<Uri>> imageTasks = new ArrayList<>();
                                for (DataSnapshot userSnapshot : task.getResult().getChildren()) {
                                    String gender = userSnapshot.child("Gender").getValue(String.class);
                                    if (matches_gender.equals(gender)) {
                                        String name = userSnapshot.child("Name").getValue(String.class);
                                        String email = userSnapshot.child("Email").getValue(String.class);
                                        Integer age = userSnapshot.child("Age").getValue(Integer.class);
                                        String preferredGender = userSnapshot.child("Pref Gender").getValue(String.class);

                                        User user = new User(name, email, null, age != null ? age : 0, gender, preferredGender);
                                        Task<Uri> imageTask = fetchImageURL(userSnapshot.getKey());
                                        imageTasks.add(imageTask);
                                        imageTask.addOnCompleteListener(new OnCompleteListener<Uri>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Uri> task) {
                                                if (task.isSuccessful()) {
                                                    user.setImageURL(task.getResult().toString());
                                                }
                                                synchronized (matches) {
                                                    matches.add(user);
                                                    if (matches.size() == imageTasks.size()) {
                                                        taskCompletionSource.setResult(matches);
                                                    }
                                                }
                                            }
                                        });
                                    }
                                }
                                if (imageTasks.isEmpty()) {
                                    taskCompletionSource.setResult(matches);
                                }
                            } else {
                                taskCompletionSource.setException(task.getException() != null ? task.getException() : new Exception("Unknown error occurred"));
                            }
                        }
                    });
                } else {
                    taskCompletionSource.setException(initTask.getException() != null ? initTask.getException() : new Exception("Initialization failed"));
                }
            }
        });

        return taskCompletionSource.getTask();
    }

    private static Task<Uri> fetchImageURL(String uid) {
        TaskCompletionSource<Uri> taskCompletionSource = new TaskCompletionSource<>();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference imageRef = storageReference.child("profile_pic/" + uid);

        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                taskCompletionSource.setResult(uri);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                taskCompletionSource.setException(exception);
            }
        });

        return taskCompletionSource.getTask();
    }
}
