package com.xriamer.mixdemo.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xriamer.mixdemo.R;
import com.xriamer.mixdemo.Util.Box;

import java.util.List;

/**
 * MixDemo;
 * com.xriamer.mixdemo.Adapter;
 * Created by Xriam on 12/22/2018;
 */
public class BoxAdapter extends ArrayAdapter<Box> {
    private int resourceId;
    public BoxAdapter(@NonNull Context context, int textViewResourceId, List<Box> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        Box box = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        ImageView BoxView = view.findViewById(R.id.password_image);
        TextView BoxTitle = view.findViewById(R.id.password_title);
        BoxView.setImageResource(box.getImageId());
        BoxTitle.setText(box.getTitle());
        return view;
    }

    public void clear()
    {

        notifyDataSetChanged();
    }
}
