package com.example.ismael.pikaos.data.model.online;

public class Competition {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int isPrivate() {
        return isPrivate;
    }

    public void setPrivate(int aPrivate) {
        isPrivate = aPrivate;
    }

    private int id;
    private String name;
    private String admin;
    private String created;
    private String game;
    private String type;
    private int isPrivate;
}
