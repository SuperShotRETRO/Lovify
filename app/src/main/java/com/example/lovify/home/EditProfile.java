package com.example.lovify.home;

import static java.security.AccessController.getContext;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lovify.R;
import com.example.lovify.utils.DBUtils;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.storage.FirebaseStorage;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class EditProfile extends AppCompatActivity {

    ImageView profilePic;
    EditText usernameInput;
    EditText phoneInput;
    Button updateProfileBtn;


//    UserModel currentUserModel;
    ActivityResultLauncher<Intent> imagePickLauncher;
    Uri selectedImageUri;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        profilePic = findViewById(R.id.profile_image_view);
        usernameInput = findViewById(R.id.profile_username);
        phoneInput = findViewById(R.id.profile_phone);
        updateProfileBtn = findViewById(R.id.profle_update_btn);

        imagePickLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        if(data!=null && data.getData()!=null){
                            selectedImageUri = data.getData();
//                            AndroidUtil.setProfilePic(getContext(),selectedImageUri,profilePic);
                        }
                    }
                }
        );

        updateProfileBtn.setOnClickListener((v -> {
            updateBtnClick();
        }));

        profilePic.setOnClickListener((v)->{
            ImagePicker.with(this).cropSquare().compress(512).maxResultSize(512,512)
                    .createIntent(new Function1<Intent, Unit>() {
                        @Override
                        public Unit invoke(Intent intent) {
                            imagePickLauncher.launch(intent);
                            return null;
                        }
                    });
        });
    }

    void updateBtnClick(){
//        String newUsername = usernameInput.getText().toString();
//        if(newUsername.isEmpty() || newUsername.length()<3){
//            usernameInput.setError("Username length should be at least 3 chars");
//            return;
//        }


        if(selectedImageUri!=null){
            FirebaseStorage.getInstance().getReference().child("profile_pic")
                    .child(DBUtils.currentUserId()).putFile(selectedImageUri)
                    .addOnCompleteListener(task -> {
                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                    });
        }
    }

//    void updateToFirestore(){
//        FirebaseUtil.currentUserDetails().set(currentUserModel)
//                .addOnCompleteListener(task -> {
//                    setInProgress(false);
//                    if(task.isSuccessful()){
//                        AndroidUtil.showToast(getContext(),"Updated successfully");
//                    }else{
//                        AndroidUtil.showToast(getContext(),"Updated failed");
//                    }
//                });
//    }


}