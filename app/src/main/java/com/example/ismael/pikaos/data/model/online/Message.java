package com.example.ismael.pikaos.data.model.online;

public class Message {

    private String Chour;
    private String Cdate;
    private String text;
    private String Cfrom;

    public Message(String hour,String date,String text,String from){
        this.Chour = hour;
        this.Cdate = date;
        this.text = text;
        this.Cfrom = from;
    }

    public String getChour() {
        return Chour;
    }

    public void setChour(String chour) {
        Chour = chour;
    }

    public String getCdate() {
        return Cdate;
    }

    public void setCdate(String cdate) {
        Cdate = cdate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCfrom() {
        return Cfrom;
    }

    public void setCfrom(String cfrom) {
        Cfrom = cfrom;
    }
}
