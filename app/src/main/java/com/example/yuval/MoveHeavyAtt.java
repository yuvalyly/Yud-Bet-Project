package com.example.yuval;

public class MoveHeavyAtt extends Move {

    public MoveHeavyAtt() {
        super("Heavy Attack", "Strength move - deals medium damage based on your strength score. Bonus effect: The attack does heavy damage.", Ability.STR, Type.Physical);
    }

    @Override
    public void attack(Fighter attacker, Fighter target, double dmgMod, boolean wonClash)
    {
        int STR = attacker.getSTR();
        if (wonClash)
        {
            target.setHP((int)(target.getHP() - (STR * 2 * dmgMod * ((double)attacker.getStartHP()/ attacker.getMaxHP() + (attacker.getStartHP()-attacker.getMaxHP())/(-1*attacker.getMaxHP()*2)) - target.getArmor())));
        }
        else
        {
            target.setHP((int) (target.getHP()-(STR*1.5*dmgMod*((double)attacker.getStartHP()*2/attacker.getMaxHP())-target.getArmor())/2));
        }
    }

    @Override
    public double getDMGMod(boolean wonClash)
    {
        return 1;
    }
}
