package com.example.elab_yang.mmk.model;

public class EventCard {

    String time;
    String kind;
    String name;
    String unit;
    String state;
    int posistion;

    public EventCard(String time, String kind, String name, String unit, String state, int posistion) {
        this.time = time;
        this.kind = kind;
        this.name = name;
        this.unit = unit;
        this.state = state;
        this.posistion = posistion;
    }

    public EventCard(int posistion) {
        this.posistion = posistion;
    }

    public int getPosistion() {
        return posistion;
    }

    public void setPosistion(int posistion) {
        this.posistion = posistion;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
