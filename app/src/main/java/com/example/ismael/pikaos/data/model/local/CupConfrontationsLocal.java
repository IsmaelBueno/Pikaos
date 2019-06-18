package com.example.ismael.pikaos.data.model.local;

public class CupConfrontationsLocal {

    public CupConfrontationsLocal(long idCup, int nRound, String playerOne, String playerTwo, String winner) {
        this.idCup = idCup;
        this.nRound = nRound;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.winner = winner;
    }

    public long getIdCup() {
        return idCup;
    }

    public void setIdCup(long idCup) {
        this.idCup = idCup;
    }

    public int getnRound() {
        return nRound;
    }

    public void setnRound(int nRound) {
        this.nRound = nRound;
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

    private long idCup;
    private int nRound;
    private String playerOne;
    private String playerTwo;
    private String winner;
}
