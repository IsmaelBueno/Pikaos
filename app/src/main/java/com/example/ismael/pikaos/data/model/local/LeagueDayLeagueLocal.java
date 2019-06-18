package com.example.ismael.pikaos.data.model.local;

public class LeagueDayLeagueLocal {
    private int idLeague;
    private int nDayLeague;

    public int getIdLeague() {
        return idLeague;
    }

    public void setIdLeague(int idLeague) {
        this.idLeague = idLeague;
    }

    public int getnDayLeague() {
        return nDayLeague;
    }

    public void setnDayLeague(int nDayLeague) {
        this.nDayLeague = nDayLeague;
    }

    public LeagueDayLeagueLocal(int idLeague, int nDayLeague) {

        this.idLeague = idLeague;
        this.nDayLeague = nDayLeague;
    }
}
