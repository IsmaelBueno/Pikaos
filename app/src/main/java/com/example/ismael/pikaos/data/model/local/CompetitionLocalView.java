package com.example.ismael.pikaos.data.model.local;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class CompetitionLocalView extends CompetitionLocal implements Comparable<CompetitionLocalView>,Serializable {

    private long nPlayers;
    private String nameRound;

    public CompetitionLocalView(int id, String name, String type, boolean finished, String note, String date) {
        super(id, name, type, finished, note, date);
    }


    public long getnPlayers() {
        return nPlayers;
    }

    public void setnPlayers(long nPlayers) {
        this.nPlayers = nPlayers;
    }

    public String getNameRound() {
        return nameRound;
    }

    public void setNameRound(String nameRound) {
        this.nameRound = nameRound;
    }

    @Override
    public int compareTo(@NonNull CompetitionLocalView o) {
        return this.getName().compareTo(o.getName());
    }
}
