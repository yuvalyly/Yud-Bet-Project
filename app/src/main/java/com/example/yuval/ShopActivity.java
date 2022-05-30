package com.example.yuval;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;

public class ShopActivity extends AppCompatActivity {

    Fighter player;
    LinearLayout ll_categories;
    LinearLayout ll_train;
    LinearLayout ll_physical;
    LinearLayout ll_magic;
    LinearLayout ll_items;
    TextView tv_money;
    TextView tv_stats;
    Button btn_exit;
    Button btn_info;
    Button btn_physical;
    Button btn_items;
    Button btn_upArmor;
    Button btn_train;
    Button btn_trainSTR;
    Button btn_trainDEX;
    Button btn_trainWIS;
    Button btn_trainHP;
    Button btn_next;
    Button btn_att_heavy;
    Button btn_att_deception;
    Button btn_att_quick;
    Button btn_brace;
    static int money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        player = GameActivity.player;
        ll_categories = (LinearLayout)findViewById(R.id.ll_buttons_categories);
        ll_train = (LinearLayout)findViewById(R.id.ll_train);
        ll_physical = (LinearLayout)findViewById(R.id.ll_buttons_physical);
        ll_magic = (LinearLayout)findViewById(R.id.ll_magic);
        ll_items = (LinearLayout)findViewById(R.id.ll_items);
        tv_money = (TextView)findViewById(R.id.tv_money);
        tv_stats = (TextView)findViewById(R.id.tv_stats);
        btn_info = (Button)findViewById(R.id.btn_info);
        btn_exit = (Button)findViewById(R.id.btn_exit);
        btn_next = (Button)findViewById(R.id.btn_next);
        btn_physical = (Button)findViewById(R.id.btn_physical);
        btn_items = (Button)findViewById(R.id.btn_items);
        btn_upArmor = (Button)findViewById(R.id.btn_upArmor);
        btn_train = (Button)findViewById(R.id.btn_log);
        btn_trainSTR = (Button)findViewById(R.id.btn_trainSTR);
        btn_trainDEX = (Button)findViewById(R.id.btn_trainDEX);
        btn_trainWIS = (Button)findViewById(R.id.btn_trainWIS);
        btn_trainHP = (Button)findViewById(R.id.btn_trainHP);
        btn_att_heavy = (Button)findViewById(R.id.btn_att_heavy);
        btn_att_quick = (Button)findViewById(R.id.btn_att_quick);
        btn_att_deception = (Button)findViewById(R.id.btn_att_deception);
        btn_brace = (Button)findViewById(R.id.btn_brace);
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameActivity.score = 0;
                money = 0;
                ArrayList<Move> moves = new ArrayList<Move>();
                moves.add(new MoveHeavyAtt());
                moves.add(new MoveQuickAtt());
                player = new Fighter(20, 20, 3, 3, 3, moves, 0, new LinkedList<Move>());;
                Intent intent = new Intent(ShopActivity.this, MainMenuActivity.class);
                startActivity(intent);
            }
        });
        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShopActivity.this, InfoActivity.class);
                startActivity(intent);
            }
        });
        btn_physical.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ll_categories.setVisibility(View.GONE);
                ll_physical.setVisibility(View.VISIBLE);
            }
        });
        btn_items.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ll_categories.setVisibility(View.GONE);
                ll_items.setVisibility(View.VISIBLE);
            }
        });
        btn_upArmor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(money >= 20)
                {
                    player.setArmor(player.getArmor() + 1);
                    money -= 20;
                }
                else
                    Toast.makeText(ShopActivity.this, "You don't have enough money.", Toast.LENGTH_SHORT).show();
                updateData();
            }
        });
        btn_train.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ll_categories.setVisibility(View.GONE);
                ll_train.setVisibility(View.VISIBLE);
            }
        });
        btn_trainSTR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(money >= 20)
                {
                    player.setSTR(player.getSTR() + 1);
                    money -= 20;
                }
                else
                    Toast.makeText(ShopActivity.this, "You don't have enough money.", Toast.LENGTH_SHORT).show();
                updateData();
            }
        });
        btn_trainDEX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(money >= 10)
                {
                    player.setDEX(player.getDEX() + 1);
                    money -= 10;
                }
                else
                    Toast.makeText(ShopActivity.this, "You don't have enough money.", Toast.LENGTH_SHORT).show();
                updateData();
            }
        });
        btn_trainWIS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(money >= 10)
                {
                    player.setWIS(player.getWIS() + 1);
                    money -= 10;
                }
                else
                    Toast.makeText(ShopActivity.this, "You don't have enough money.", Toast.LENGTH_SHORT).show();
                updateData();
            }
        });
        btn_trainHP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(money >= 2)
                {
                    player.setMaxHP(player.getMaxHP() + 1);
                    money -= 2;
                }
                else
                    Toast.makeText(ShopActivity.this, "You don't have enough money.", Toast.LENGTH_SHORT).show();
                updateData();
            }
        });
        btn_brace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!GameActivity.scanArrList(player.getMoves(), new MoveBrace()))
                {
                    if (money >= 10) {
                        player.getMoves().add(new MoveBrace());
                        money -= 10;
                        Toast.makeText(ShopActivity.this, "Move unlocked.", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(ShopActivity.this, "You don't have enough money.", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(ShopActivity.this, "You already know this move.", Toast.LENGTH_SHORT).show();
                updateData();
            }
        });
        btn_att_quick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!GameActivity.scanArrList(player.getMoves(), new MoveQuickAtt()))
                {
                    if (money >= 10) {
                        player.getMoves().add(new MoveQuickAtt());
                        money -= 10;
                        Toast.makeText(ShopActivity.this, "Move unlocked.", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(ShopActivity.this, "You don't have enough money.", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(ShopActivity.this, "You already know this move.", Toast.LENGTH_SHORT).show();
                updateData();
            }
        });
        btn_att_heavy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!GameActivity.scanArrList(player.getMoves(), new MoveHeavyAtt()))
                {
                    if (money >= 10) {
                        player.getMoves().add(new MoveHeavyAtt());
                        money -= 10;
                        Toast.makeText(ShopActivity.this, "Move unlocked.", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(ShopActivity.this, "You don't have enough money.", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(ShopActivity.this, "You already know this move.", Toast.LENGTH_SHORT).show();
                updateData();
            }
        });
        btn_att_deception.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!GameActivity.scanArrList(player.getMoves(), new MoveAttDeception()))
                {
                    if (money >= 10) {
                        player.getMoves().add(new MoveAttDeception());
                        money -= 10;
                        Toast.makeText(ShopActivity.this, "Move unlocked.", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(ShopActivity.this, "You don't have enough money.", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(ShopActivity.this, "You already know this move.", Toast.LENGTH_SHORT).show();
                updateData();
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShopActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });
        updateData();
    }

    private void updateData()
    {
        tv_money.setText("Money: " + money);
        tv_stats.setText("Strength: " + player.getSTR() + " \n Dexterity: " + player.getDEX() + " \n Wisdom: " + player.getWIS() + " \n Armor: " + player.getArmor() + " Max HP: " + player.getMaxHP());
    }

    @Override
    public void onBackPressed() {
        ll_train.setVisibility(View.GONE);
        ll_items.setVisibility(View.GONE);
        ll_magic.setVisibility(View.GONE);
        ll_physical.setVisibility(View.GONE);
        ll_categories.setVisibility(View.VISIBLE);
    }



}