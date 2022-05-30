package com.example.yuval;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    Button btn_physical;
    LinearLayout ll_buttons_categories;
    LinearLayout ll_buttons_physical;
    Button btn_magic;
    LinearLayout ll_magic;
    Button btn_items;
    LinearLayout ll_items;
    Button btn_log;
    Button btn_att_heavy;
    Button btn_att_quick;
    Button btn_att_deception;
    Button btn_brace;
    Button btn_info;
    Button btn_exit;
    Fighter enemy;
    static Fighter player;
    TextView tv_PHP;
    TextView tv_EHP;
    FirebaseDatabase database;
    DatabaseReference ref;
    static int score;
    static int lastScore;
    static ArrayList<String> logMoves;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        lastScore = 0;
        logMoves = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        //ShopActivity.money = 0;
        ArrayList<Move> moves = new ArrayList<Move>();
        moves.add(new MoveHeavyAtt());
        moves.add(new MoveQuickAtt());
        if(score >= 2)
            moves.add(new MoveAttDeception());
        if(score >= 5)
            moves.add(new MoveBrace());
        LinkedList<Move> known = new LinkedList<Move>();
        int HPMod = 0;
        int STRMod = 0;
        int DEXMod = 0;
        int WISMod = 0;
        int ARMMod = 0;
        Random rng = new Random();
        if(score >= 5)
        {
            int numOMoves = 4 + rng.nextInt(5);
            for(int i = 0;i<numOMoves;i++)
            {
                int selected = rng.nextInt(4);
                if(selected == 0)
                    known.add(new MoveHeavyAtt());
                else if(selected == 1)
                    known.add(new MoveQuickAtt());
                else if(selected == 2)
                    known.add(new MoveAttDeception());
                else if(selected == 3)
                    known.add(new MoveBrace());
            }
            for(int i = 0;i<(score*2-numOMoves);i++)
            {
                int selected = rng.nextInt(5);
                if(selected == 0)
                    STRMod += rng.nextInt(2);
                else if(selected == 1)
                    HPMod += 3 + rng.nextInt(4);
                else if(selected == 2)
                    DEXMod += rng.nextInt(2);
                else if(selected == 3)
                    WISMod += rng.nextInt(2);
                else if(selected == 4)
                    ARMMod += rng.nextInt(2);
            }
        }
        else if(score >= 2)
        {
            int numOMoves = 3 + rng.nextInt(3);
            for(int i = 0;i<numOMoves;i++)
            {
                int selected = rng.nextInt(3);
                if(selected == 0)
                    known.add(new MoveHeavyAtt());
                else if(selected == 1)
                    known.add(new MoveQuickAtt());
                else if(selected == 2)
                    known.add(new MoveAttDeception());
            }
            for(int i = 0;i<(9-numOMoves);i++)
            {
                int selected = rng.nextInt(5);
                if(selected == 0)
                    STRMod += rng.nextInt(2);
                else if(selected == 1)
                    HPMod += 3 + rng.nextInt(4);
                else if(selected == 2)
                    DEXMod += rng.nextInt(2);
                else if(selected == 3)
                    WISMod += rng.nextInt(2);
                else if(selected == 4)
                    ARMMod += rng.nextInt(2);
            }
        }
        else
        {
            int numOMoves = 3 + rng.nextInt(3);
            for(int i = 0;i<numOMoves;i++)
            {
                int selected = rng.nextInt(2);
                if(selected == 0)
                    known.add(new MoveHeavyAtt());
                else if(selected == 1)
                    known.add(new MoveQuickAtt());
            }
            //for(int i = 0;i<(6-numOMoves);i++)
            int tokens = 6-numOMoves;
            while (tokens > 0 && score > 0)
            {
                int selected = rng.nextInt(5);
                if(selected == 0 && tokens>1) {
                    STRMod += rng.nextInt(2);
                    tokens --;
                }
                else if(selected == 1)
                    HPMod += 3 + rng.nextInt(4);
                else if(selected == 2)
                    DEXMod += rng.nextInt(2);
                else if(selected == 3)
                    WISMod += rng.nextInt(2);
                else if(selected == 4 && tokens>1) {
                    ARMMod += rng.nextInt(2);
                    tokens --;
                }
                tokens --;
            }
        }
        enemy = new Fighter(20+HPMod, 20+HPMod, 3 + STRMod, 3 + DEXMod, 3 + WISMod, moves, 0 + ARMMod, known);
        if(player == null)
        {
            ArrayList<Move> pMoves = new ArrayList<Move>();
            pMoves.add(new MoveHeavyAtt());
            pMoves.add(new MoveQuickAtt());
            player = new Fighter(20, 20, 3, 3, 3, pMoves, 0, new LinkedList<Move>());
        }
        else
            player.setHP(player.getMaxHP());
        tv_PHP = (TextView)findViewById(R.id.tv_money);
        tv_EHP = (TextView)findViewById(R.id.tv_stats);
        ll_buttons_physical= (LinearLayout)findViewById(R.id.ll_buttons_physical);
        ll_buttons_categories= (LinearLayout)findViewById(R.id.ll_buttons_categories);
        ll_magic = (LinearLayout)findViewById(R.id.ll_magic);
        ll_items = (LinearLayout)findViewById(R.id.ll_items);
        btn_info = (Button)findViewById(R.id.btn_info);
        btn_exit = (Button)findViewById(R.id.btn_exit);
        btn_log = (Button)findViewById(R.id.btn_log);
        btn_items = (Button)findViewById(R.id.btn_items);
        btn_magic = (Button)findViewById(R.id.btn_magic);
        btn_att_heavy = (Button)findViewById(R.id.btn_att_heavy);
        btn_att_quick = (Button)findViewById(R.id.btn_att_quick);
        btn_att_deception = (Button)findViewById(R.id.btn_att_deception);
        btn_brace = (Button)findViewById(R.id.btn_brace);
        btn_physical = (Button)findViewById(R.id.btn_physical);
        tv_PHP.setText("Your HP: " + player.getHP() + "/" + player.getMaxHP());
        tv_EHP.setText("Enemy HP: " + enemy.getHP() + "/" + enemy.getMaxHP());

        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameActivity.this, InfoActivity.class);
                startActivity(intent);
            }
        });
        btn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameActivity.this, LogActivity.class);
                startActivity(intent);
            }
        });
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                score = 0;
                ShopActivity.money = 0;
                ArrayList<Move> moves = new ArrayList<Move>();
                moves.add(new MoveHeavyAtt());
                moves.add(new MoveQuickAtt());
                player = new Fighter(20, 20, 3, 3, 3, moves, 0, new LinkedList<Move>());;
                Intent intent = new Intent(GameActivity.this, MainMenuActivity.class);
                startActivity(intent);
            }
        });
        btn_physical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_buttons_categories.setVisibility(View.GONE);
                ll_buttons_physical.setVisibility(View.VISIBLE);

            }
        });
        btn_magic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_buttons_categories.setVisibility(View.GONE);
                ll_magic.setVisibility(View.VISIBLE);
            }
        });
        btn_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_buttons_categories.setVisibility(View.GONE);
                ll_items.setVisibility(View.VISIBLE);
            }
        });
        btn_att_heavy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(scanArrList(player.getMoves(), new MoveHeavyAtt()))
                    round(new MoveHeavyAtt());
                else
                    Toast.makeText(GameActivity.this, "You don't know this move!", Toast.LENGTH_SHORT).show();
            }
        });
        btn_att_quick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(scanArrList(player.getMoves(), new MoveQuickAtt()))
                    round(new MoveQuickAtt());
                else
                    Toast.makeText(GameActivity.this, "You don't know this move!", Toast.LENGTH_SHORT).show();
            }
        });
        btn_att_deception.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(scanArrList(player.getMoves(), new MoveAttDeception()))
                round(new MoveAttDeception());
                else
                    Toast.makeText(GameActivity.this, "You don't know this move!", Toast.LENGTH_SHORT).show();
            }
        });
        btn_brace.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public  void onClick(View view)
            {
                if(scanArrList(player.getMoves(), new MoveBrace()))
                    round(new MoveBrace());
                else
                    Toast.makeText(GameActivity.this, "You don't know this move!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void round(Move chosen)
    {
        player.setStartHP(player.getHP());
        enemy.setStartHP(enemy.getHP());
        if(chosen.getMainAbility() == Ability.STR && enemy.getActiveMove().getFirst().getMainAbility() == Ability.DEX || chosen.getMainAbility() == Ability.DEX && enemy.getActiveMove().getFirst().getMainAbility() == Ability.WIS || chosen.getMainAbility() == Ability.WIS && enemy.getActiveMove().getFirst().getMainAbility() == Ability.STR)
        {
            double pDMGmod = chosen.getDMGMod(true);
            double eDMGmod = enemy.getActiveMove().getFirst().getDMGMod(false);
            chosen.attack(player, enemy, eDMGmod, true);
            logMoves.add("You used: " + chosen.getName());
            if(enemy.getStartHP()>0)
                enemy.getActiveMove().getFirst().attack(enemy, player, pDMGmod,false);
            logMoves.add("Your opponent used: " + enemy.getActiveMove().getFirst().getName());
        }
        else if(chosen.getMainAbility() == enemy.getActiveMove().getFirst().getMainAbility())
        {
            double pDMGmod = chosen.getDMGMod(false);
            double eDMGmod = enemy.getActiveMove().getFirst().getDMGMod(false);
            chosen.attack(player, enemy, eDMGmod, false);
            logMoves.add("You used: " + chosen.getName());
            if(enemy.getStartHP()>0)
                enemy.getActiveMove().getFirst().attack(enemy, player, pDMGmod, false);
            logMoves.add("Your opponent used: " + enemy.getActiveMove().getFirst().getName());
        }
        else
        {
            double pDMGmod = chosen.getDMGMod(false);
            double eDMGmod = enemy.getActiveMove().getFirst().getDMGMod(true);
            enemy.getActiveMove().getFirst().attack(enemy, player, pDMGmod,true);
            logMoves.add("Your opponent used: " + enemy.getActiveMove().getFirst().getName());
            if(player.getStartHP()>0)
                chosen.attack(player, enemy, eDMGmod,  false);
            logMoves.add("You used: " + chosen.getName());
        }
        if(player.getHP()<=0)
        {
            lastScore = score;
            if(!MainMenuActivity.playingInThisPhone.equals("Guest")) {
                Query q = ref.child("users").child(MainMenuActivity.playingInThisPhone).orderByValue();
                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        Player p = snapshot.getValue(Player.class);
                        if (p.getHighScore() < score) {
                            ref.child("users").child(MainMenuActivity.playingInThisPhone).child("highScore").setValue(score);
                        }
                        score = 0;
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
            }
            else
                score = 0;
            Intent lose = new Intent(GameActivity.this, GameoverActivity.class);
            startActivity(lose);
            ShopActivity.money = 0;
            ArrayList<Move> moves = new ArrayList<Move>();
            moves.add(new MoveHeavyAtt());
            moves.add(new MoveQuickAtt());
            player = new Fighter(20, 20, 3, 3, 3, moves, 0, new LinkedList<Move>());;
        }
        else if(enemy.getHP()<=0)
        {
            Intent win = new Intent(GameActivity.this, ShopActivity.class);
            startActivity(win);
            ShopActivity.money += 10;
            score ++;
            Toast.makeText(getApplicationContext(),"score = " + score, Toast.LENGTH_LONG).show();
        }
        else
        {
            Intent intent = new Intent(GameActivity.this, LogActivity.class);
            startActivity(intent);
        }
        enemy.getActiveMove().add(enemy.getActiveMove().remove());
        tv_PHP.setText("Your HP: " + player.getHP() + "/" + player.getMaxHP());
        tv_EHP.setText("Enemy HP: " + enemy.getHP() + "/" + enemy.getMaxHP());
    }

    public static <T> boolean scanArrList(ArrayList<T> arr, T target)
    {
        int len = arr.size();
        for(int i = 0;i<len;i++)
        {
            if(arr.get(i).getClass() == target.getClass())
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        ll_items.setVisibility(View.GONE);
        ll_magic.setVisibility(View.GONE);
        ll_buttons_physical.setVisibility(View.GONE);
        ll_buttons_categories.setVisibility(View.VISIBLE);
    }
}