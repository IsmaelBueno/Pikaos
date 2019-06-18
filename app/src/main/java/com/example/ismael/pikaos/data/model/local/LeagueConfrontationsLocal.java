package com.example.ismael.pikaos.data.model.local;

public class LeagueConfrontationsLocal {
    private int idLeague;
    private int nDayLeague;
    private String playerOne;
    private String playerTwo;
    private String winner;

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

    public String getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(String playerOne) {
        this.playerOne = playerOne;
    }

    public String getPlayerTwo() {
        return playerTwo;
    }

    public void setPlayerTwo(String playerTwo) {
        this.playerTwo = playerTwo;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public LeagueConfrontationsLocal(int idLeague, int nDayLeague, String playerOne, String playerTwo, String winner) {

        this.idLeague = idLeague;
        this.nDayLeague = nDayLeague;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.winner = winner;
    }
}
