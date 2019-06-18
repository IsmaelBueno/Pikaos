package com.example.ismael.pikaos.data.model.local;

public class LeaguePlayersLocal {
    private String name;
    private int points;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public LeaguePlayersLocal(String name, int points) {

        this.name = name;
        this.points = points;
    }
}
