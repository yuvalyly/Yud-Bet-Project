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

public class HighScoreAdapter extends ArrayAdapter<Player> {
    Context context;
    ArrayList<Player> arr_players;
    String rememberedUsername;


    public HighScoreAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull ArrayList<Player> objects, String username) {
        super(context, resource, textViewResourceId, objects);
        this.context = context;
        arr_players = objects;
        rememberedUsername = username;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.line, parent, false);

        TextView tv_userName = view.findViewById(R.id.txtName);
        TextView tv_highScore = view.findViewById(R.id.txtHS);
        TextView tv_place = view.findViewById(R.id.txtPlace);
        if(position%2==1){
            view.setBackgroundColor(Color.parseColor("#e0eee0"));
        }
        Player temp = arr_players.get(position);
        tv_place.setText("" + (position+1));
        tv_userName.setText(temp.getName());
        if(temp.getName().equals(rememberedUsername))
            view.setBackgroundColor(Color.parseColor("#55BBFF"));
        tv_highScore.setText(""+temp.getHighScore());


        return view;
    }
}
