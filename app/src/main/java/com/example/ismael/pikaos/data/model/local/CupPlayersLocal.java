package com.example.ismael.pikaos.data.model.local;

public class CupPlayersLocal {
    public CupPlayersLocal(long idCup, String name, boolean removed) {
        this.idCup = idCup;
        this.name = name;
        this.removed = removed;
    }

    public long getIdCup() {
        return idCup;
    }

    public void setIdCup(int idCup) {
        this.idCup = idCup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    private long idCup;
    private String name;
    private boolean removed;
}
