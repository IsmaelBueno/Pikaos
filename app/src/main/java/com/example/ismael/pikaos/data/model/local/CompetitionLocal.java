package com.example.ismael.pikaos.data.model.local;

public class CompetitionLocal {

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private int id;
    private String name;
    private String type;
    private boolean finished;
    private String note;
    private String date;

    public CompetitionLocal(int id,String name, String type, boolean finished, String note, String date){
        this.id = id;
        this.name = name;
        this.type = type;
        this.finished = finished;
        this.note = note;
        this.date = date;
    }

}
