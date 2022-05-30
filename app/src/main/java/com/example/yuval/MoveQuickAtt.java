package com.example.yuval;

public class MoveQuickAtt extends Move {

    public MoveQuickAtt() {
        super("Quick Attack", "Dexterity move - deals lite damage based on your dexterity score. Bonus effect: This move is executed before the enemy's move.", Ability.DEX, Type.Physical);
    }

    @Override
    public void attack(Fighter attacker, Fighter target, double dmgMod, boolean wonClash)
    {
        int DEX = attacker.getDEX();
        if(wonClash)
        {
            target.setHP((int) (target.getHP()-(DEX*dmgMod*((double)attacker.getStartHP()*2/attacker.getMaxHP())-target.getArmor())/2));
            target.setStartHP(target.getHP());
        }
        else
        {
            target.setHP((int) (target.getHP()-(DEX*dmgMod*((double)attacker.getStartHP()*2/attacker.getMaxHP())-target.getArmor())/2));
        }
    }

    @Override
    public double getDMGMod(boolean wonClash)
    {
        return 1;
    }
}
