package com.example.ismael.pikaos.data.model.local;

public class CupLocal {

    public CupLocal(long id, int nRounds) {
        this.id = id;
        this.nRounds = nRounds;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getnRounds() {
        return nRounds;
    }

    public void setnRounds(int nRounds) {
        this.nRounds = nRounds;
    }

    private long id;
    private int nRounds;
}
