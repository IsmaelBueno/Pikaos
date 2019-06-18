package com.example.ismael.pikaos.data.model.local;

public class CupRoundLocal {
    public CupRoundLocal(long idCup, int nRound, String roundName) {
        this.idCup = idCup;
        this.nRound = nRound;
        this.roundName = roundName;
    }

    public long getIdCup() {
        return idCup;
    }

    public void setIdCup(int idCup) {
        this.idCup = idCup;
    }

    public int getnRound() {
        return nRound;
    }

    public void setnRound(int nRound) {
        this.nRound = nRound;
    }

    public String getRoundName() {
        return roundName;
    }

    public void setRoundName(String roundName) {
        this.roundName = roundName;
    }

    private long idCup;
    private int nRound;
    private String roundName;
}
