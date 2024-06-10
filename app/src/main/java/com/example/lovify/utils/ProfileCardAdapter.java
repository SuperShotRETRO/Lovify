package com.example.lovify.utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lovify.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProfileCardAdapter extends BaseAdapter {

    private Context context;
    private List<String> images;
    private List<String> names;
    private LayoutInflater inflater;

    public ProfileCardAdapter(Context context, List<String> images, List<String> names) {
        this.context = context;
        this.images = images;
        this.names = names;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return Math.min(images.size(), names.size());
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.profile_card, parent, false);
        }

        ImageView profilePic = convertView.findViewById(R.id.ProfilePic);
        TextView profileName = convertView.findViewById(R.id.ProfileCardName);

//        Log.e("url",images.get(position));
        Picasso.get().load(images.get(position)).into(profilePic);
        profileName.setText(names.get(position));

        return convertView;
    }
}
