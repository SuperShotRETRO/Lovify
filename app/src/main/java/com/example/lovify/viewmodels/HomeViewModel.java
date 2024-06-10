package com.example.lovify.viewmodels;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lovify.model.User;
import com.example.lovify.utils.DBUtils;
import com.example.lovify.utils.MatchHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<List<User>> usersLiveData = new MutableLiveData<>();
    private MatchHelper matchHelper;

    public HomeViewModel() {
        matchHelper = new MatchHelper();
        fetchMatches();
    }

    public LiveData<List<User>> getUsersLiveData() {
        return usersLiveData;
    }

    private void fetchMatches() {
        matchHelper.findMatches().addOnCompleteListener(new OnCompleteListener<List<User>>() {
            @Override
            public void onComplete(@NonNull Task<List<User>> task) {
                if (task.isSuccessful()) {
                    usersLiveData.setValue(task.getResult());
                } else {
                    usersLiveData.setValue(null); // Handle error case if needed
                }
            }
        });
    }
}
