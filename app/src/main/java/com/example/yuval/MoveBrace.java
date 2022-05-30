package com.example.yuval;

public class MoveBrace extends Move {

    public MoveBrace()
    {
        super("Brace", "Wisdom move - the enemy's attack is 4 times weaker.", Ability.WIS, Type.Physical);
    }

    @Override
    public void attack(Fighter attacker, Fighter target, double dmgMod, boolean wonClash)
    {

    }

    @Override
    public  double getDMGMod(boolean wonClash)
    {
        return 0.25;
    }
}
