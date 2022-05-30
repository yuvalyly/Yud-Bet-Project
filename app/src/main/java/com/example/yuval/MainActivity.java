package com.example.yuval;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_find, btn_return;
    ListView lv_playersScore;
    FirebaseDatabase database;
    DatabaseReference ref;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String rememberedUsername;
    int myPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("remembered username", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        rememberedUsername = sharedPreferences.getString("remembered username", "Guest");

        database = FirebaseDatabase.getInstance();
        ref = database.getReference();

        btn_return = (Button) findViewById(R.id.button);
        btn_return.setOnClickListener(this);
        btn_find = (Button) findViewById(R.id.btn_find);
        btn_find.setOnClickListener(this);

        lv_playersScore = (ListView)findViewById(R.id.lv_playersScore);

        ArrayList <Player> players = new ArrayList<>();

        Query q = ref.child("users").orderByValue();
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                players.clear();
                for(DataSnapshot ds : snapshot.getChildren())
                {
                    Player p = ds.getValue(Player.class);
                    players.add(p);
                }
                Collections.sort(players, new Comparator<Player>() {
                    public int compare(Player p1, Player p2) {
                        int x = Integer.valueOf(p2.getHighScore()).compareTo(p1.getHighScore());

                        return x;
                    }
                });
                for(int i = 0; i < players.size(); i++)
                {
                    if (players.get(i).getName().equals(rememberedUsername))
                    {
                        myPos = i;
                    }
                }
                HighScoreAdapter adapter = new HighScoreAdapter(MainActivity.this, 0, 0, players, rememberedUsername);
                lv_playersScore.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == btn_return){
            Intent goToMain = new Intent(MainActivity.this, MainMenuActivity.class);
            startActivity(goToMain);
        }
        if(view == btn_find)
        {

            if(rememberedUsername.equals("Guest"))
                Toast.makeText(this, "You are not logged in.", Toast.LENGTH_LONG).show();
            else
            {
                lv_playersScore.smoothScrollToPosition(myPos);
            }
        }
    }
}
