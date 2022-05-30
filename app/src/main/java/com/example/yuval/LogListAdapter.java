package com.example.yuval;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class LogListAdapter extends ArrayAdapter<String> {
    Context context;
    ArrayList<String> arr_log_activity;
    String str_moves;


    public LogListAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull ArrayList<String> objects) {
        super(context, resource, textViewResourceId, objects);
        this.context = context;
        arr_log_activity = objects;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.log_line, parent, false);

        TextView tv_log_textview = view.findViewById(R.id.tv_log_textview);
        if(position%2==1){
            view.setBackgroundColor(Color.parseColor("#e0eee0"));
        }
        String temp = arr_log_activity.get(position);
        tv_log_textview.setText(temp);
        return view;
    }
}
