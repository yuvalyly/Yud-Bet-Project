package com.example.yuval;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TryRecycle_Activity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference ref;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_try_recycle);


        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        generateData();

    }

    private List<Player> generateData() {
        List<Player> data = new ArrayList<>();
        Query q = ref.child("users").orderByValue();
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                data.clear();
                for(DataSnapshot ds : snapshot.getChildren())
                {
                    Player p = ds.getValue(Player.class);
                    data.add(p);
                }

                Collections.sort(data, new Comparator<Player>() {
                    public int compare(Player p1, Player p2) {
                        return Integer.valueOf(p2.getHighScore()).compareTo(p1.getHighScore());
                    }
                });
                recyclerView.setAdapter(new CustomAdapter(data));
                recyclerView.addItemDecoration(new DividerItemDecoration(TryRecycle_Activity.this, DividerItemDecoration.VERTICAL));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return data;
    }
}
