package com.example.ismael.pikaos.data.model.local;

public class LeagueLocal {
    private int id;
    private int nDayLeagues;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getnDayLeagues() {
        return nDayLeagues;
    }

    public void setnDayLeagues(int nDayLeagues) {
        this.nDayLeagues = nDayLeagues;
    }

    public LeagueLocal(int id, int nDayLeagues) {

        this.id = id;
        this.nDayLeagues = nDayLeagues;
    }
}
