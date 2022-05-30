package com.example.yuval;

import java.util.ArrayList;
import java.util.LinkedList;

public class Fighter {
    private int HP;
    private int maxHP;
    private int STR;
    private int DEX;
    private int WIS;
    private ArrayList<Move> moves;
    private int armor;
    private int StartHP;
    private LinkedList<Move> activeMove;

    public int getStartHP() {
        return StartHP;
    }

    public void setStartHP(int startHP) {
        StartHP = startHP;
    }

    public Fighter(int HP, int maxHP, int STR, int DEX, int WIS, ArrayList<Move> moves, int armor, LinkedList<Move> activeMove) {
        this.HP = HP;
        this.maxHP = maxHP;
        this.STR = STR;
        this.DEX = DEX;
        this.WIS = WIS;
        this.moves = moves;
        this.armor = armor;
        this.activeMove = activeMove;
    }

    public LinkedList<Move> getActiveMove() {
        return activeMove;
    }

    public void setActiveMove(LinkedList<Move> activeMove) {
        this.activeMove = activeMove;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public int getSTR() {
        return STR;
    }

    public void setSTR(int STR) {
        this.STR = STR;
    }

    public int getDEX() {
        return DEX;
    }

    public void setDEX(int DEX) {
        this.DEX = DEX;
    }

    public int getWIS() {
        return WIS;
    }

    public void setWIS(int WIS) {
        this.WIS = WIS;
    }

    public ArrayList<Move> getMoves() {
        return moves;
    }

    public void setMoves(ArrayList<Move> moves) {
        this.moves = moves;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }
}
