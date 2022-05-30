package com.example.yuval;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private List<Player> data;
    public CustomAdapter (List<Player> data){
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.only_text_view_in_line, parent, false);
        return new ViewHolder(rowItem);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        holder.tv_playerPlace.setText("yuval");
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {
        private TextView tv_playerName;
        private TextView tv_playerHighScore;
        private TextView tv_playerPlace;

        public ViewHolder(View view) {
            super(view);
            this.tv_playerName = view.findViewById(R.id.txtName);
            this.tv_playerHighScore = view.findViewById(R.id.txtHS);
            this.tv_playerPlace = view.findViewById(R.id.txtPlace);
        }


    }
}