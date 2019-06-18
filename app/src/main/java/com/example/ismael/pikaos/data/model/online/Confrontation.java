package com.example.ismael.pikaos.data.model.online;

import android.support.annotation.Nullable;

public class Confrontation {

    public String getPlayer_one() {
        return player_one;
    }

    public void setPlayer_one(String player_one) {
        this.player_one = player_one;
    }

    public String getPlayer_two() {
        return player_two;
    }

    public void setPlayer_two(String player_two) {
        this.player_two = player_two;
    }

    @Nullable
    public String getWinner() {
        return winner;
    }

    public void setWinner(@Nullable String winner) {
        this.winner = winner;
    }

    private String player_one;
    private String player_two;
    @Nullable private String winner;
}
