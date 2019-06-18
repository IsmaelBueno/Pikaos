package com.example.ismael.pikaos.data.model.online;

public class Round {

    public int getnRound() {
        return nRound;
    }

    public void setnRound(int nRound) {
        this.nRound = nRound;
    }

    public Confrontation[] getConfrontations() {
        return confrontations;
    }

    public void setConfrontations(Confrontation[] confrontations) {
        this.confrontations = confrontations;
    }

    private int nRound;
    private Confrontation[] confrontations;

}
