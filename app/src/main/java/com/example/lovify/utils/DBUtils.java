package com.example.lovify.utils;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.lovify.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DBUtils {

    public static String currentUserId(){
        return FirebaseAuth.getInstance().getUid();
    }

    public static boolean isLoggedIn(){
        return currentUserId() != null;
    }

    public static Task<User> currentUserDetails() {
        TaskCompletionSource<User> taskCompletionSource = new TaskCompletionSource<>();
        DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference();

        currentUserDb.child("User").child(DBUtils.currentUserId()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful() && task.getResult().exists()) {
                    DataSnapshot dataSnapshot = task.getResult();
                    String name = dataSnapshot.child("Name").getValue(String.class);
                    String email = dataSnapshot.child("Email").getValue(String.class);
                    Integer age = dataSnapshot.child("Age").getValue(Integer.class);
                    String gender = dataSnapshot.child("Gender").getValue(String.class);
                    String preferredGender = dataSnapshot.child("Pref Gender").getValue(String.class);

                    User user = new User(name, email,null, age != null ? age : 0, gender, preferredGender);

                    // Fetch the image URL
                    fetchImageURL().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> imageTask) {
                            if (imageTask.isSuccessful()) {
                                user.setImageURL(imageTask.getResult().toString());
                            }
                            taskCompletionSource.setResult(user);
                        }
                    });
                } else {
                    taskCompletionSource.setException(task.getException());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                taskCompletionSource.setException(e);
            }
        });

        return taskCompletionSource.getTask();
    }

    private static Task<Uri> fetchImageURL() {
        TaskCompletionSource<Uri> taskCompletionSource = new TaskCompletionSource<>();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference imageRef = storageReference.child("profile_pic/" + DBUtils.currentUserId());

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