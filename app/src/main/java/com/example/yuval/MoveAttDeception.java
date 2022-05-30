package com.example.yuval;

public class MoveAttDeception extends Move{

    public MoveAttDeception() {
        super("Deception", "Wisdom move - deals lite damage based on your strength score. Bonus effect: If the enemy's move is an attack, it's damage is halved.", Ability.WIS, Type.Physical);
    }

    @Override
    public void attack(Fighter attacker, Fighter target, double dmgMod,  boolean wonClash)
    {
        int STR = attacker.getSTR();
        if (wonClash)
        {
            target.setHP((int)(target.getHP() - (STR * dmgMod * ((double)attacker.getStartHP()/ attacker.getMaxHP() + (attacker.getStartHP()-attacker.getMaxHP())/(-1*attacker.getMaxHP()*2)) - target.getArmor())));
        }
        else
        {
            target.setHP((int) (target.getHP() - (STR * dmgMod * ((double) attacker.getStartHP() * 2 / attacker.getMaxHP()) - target.getArmor()) / 2));
        }
    }

    @Override
    public double getDMGMod(boolean wonClash)
    {
        if(wonClash)
            return 0.5;
        else
            return 1.0;
    }
}
