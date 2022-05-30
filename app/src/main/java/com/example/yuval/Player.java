package com.example.yuval;

public class Player {
    String name;
    int highScore;
    String password;

    public Player() {
    }

    public Player(String name, int highScore, String password) {
        this.name = name;
        this.highScore = highScore;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", highScore=" + highScore +
                ", password='" + password + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }
}
