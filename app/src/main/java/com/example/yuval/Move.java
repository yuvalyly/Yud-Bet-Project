package com.example.yuval;

import android.util.Log;

public class Move {
    protected String name;
    protected String description;
    protected Ability mainAbility;
    protected Type moveType;

    public Move(String name, String description, Ability MainAbility, Type MoveType) {
        this.name = name;
        this.description = description;
        mainAbility = MainAbility;
        moveType = MoveType;
    }

    public void attack(Fighter attacker, Fighter target, double dmgMod,  boolean wonClash)
    {
        Log.d("myTag", "The move: " + name + " is not implemented yet!");
    }

    public double getDMGMod(boolean wonClash)
    {
        return 1.0;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Ability getMainAbility() {
        return mainAbility;
    }

    public Type getMoveType() {
        return moveType;
    }
}
