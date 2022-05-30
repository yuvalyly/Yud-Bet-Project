package com.example.yuval;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class GameoverActivity extends AppCompatActivity {
    TextView tv_score;
    DatabaseReference ref;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();

        Query q = ref.child("top3").orderByValue();
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Player first = snapshot.child("1st").getValue(Player.class);
                Player second = snapshot.child("2nd").getValue(Player.class);
                Player third = snapshot.child("3rd").getValue(Player.class);
                if(first.getHighScore() < GameActivity.lastScore && !first.getName().equals(MainMenuActivity.playingInThisPhone)) {
                    ref.child("top3").child("1st").setValue(new Player(MainMenuActivity.playingInThisPhone, GameActivity.lastScore, "empty"));
                    ref.child("top3").child("2nd").setValue(first);
                    if(!second.getName().equals(MainMenuActivity.playingInThisPhone)) {
                        ref.child("top3").child("3rd").setValue(second);
                        if(!third.getName().equals(MainMenuActivity.playingInThisPhone))
                            ref.child("top3").child("4th").setValue(third);
                    }
                }
                else if(second.getHighScore() < GameActivity.lastScore && !second.getName().equals(MainMenuActivity.playingInThisPhone)) {
                    ref.child("top3").child("2nd").setValue(new Player(MainMenuActivity.playingInThisPhone, GameActivity.lastScore, "empty"));
                    ref.child("top3").child("3rd").setValue(second);
                    if(!third.getName().equals(MainMenuActivity.playingInThisPhone))
                        ref.child("top3").child("4th").setValue(third);
                }
                else if(third.getHighScore() < GameActivity.lastScore) {
                    ref.child("top3").child("3rd").setValue(new Player(MainMenuActivity.playingInThisPhone, GameActivity.lastScore, "empty"));
                    ref.child("top3").child("4th").setValue(third);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        tv_score = (TextView)findViewById(R.id.tv_score);
        tv_score.setText("score: " + GameActivity.lastScore);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(GameoverActivity.this, MainMenuActivity.class);
        startActivity(intent);
    }
}