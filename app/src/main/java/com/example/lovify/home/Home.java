package com.example.lovify.home;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lovify.R;
import com.example.lovify.model.User;
import com.example.lovify.utils.DBUtils;
import com.example.lovify.utils.MatchHelper;
import com.example.lovify.utils.ProfileCardAdapter;
import com.example.lovify.viewmodels.HomeViewModel;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment {

    SwipeFlingAdapterView flingContainer;
    MatchHelper matchHelper;
    List<User> users;
    ArrayList<String> images;
    ArrayList<String> names;
    ProfileCardAdapter profileCardAdapter;
    HomeViewModel homeViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        flingContainer = view.findViewById(R.id.frame);

        images = new ArrayList<>();
        names = new ArrayList<>();

        profileCardAdapter = new ProfileCardAdapter(getContext(),images,names);
        flingContainer.setAdapter(profileCardAdapter);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.getUsersLiveData().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                if (users != null) {
                    for (User user : users) {
                        names.add(user.getName());
                        images.add(user.getImageURL());
                    }
                }
                profileCardAdapter.notifyDataSetChanged();
            }
        });


        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                if (!images.isEmpty() && !names.isEmpty()) {
                    images.remove(0);
                    names.remove(0);
                    profileCardAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onLeftCardExit(Object o) {

            }

            @Override
            public void onRightCardExit(Object o) {

            }

            @Override
            public void onAdapterAboutToEmpty(int i) {

            }

            @Override
            public void onScroll(float v) {

            }
        });

        return view;
    }
}