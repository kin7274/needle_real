package com.example.elab_yang.mmk.model;

public class CardItem {
    private int image;
    private String time;
    private String kind;
    private String name;
    private String unit;
    private String state;
    private int image2;


    public CardItem(int image, String time, String kind, String name, String unit, String state, int image2) {
        this.image = image;
        this.time = time;
        this.kind = kind;
        this.name = name;
        this.unit = unit;
        this.state = state;
        this.image2 = image2;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
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

    public int getImage2() {
        return image2;
    }

    public void setImage2(int image2) {
        this.image2 = image2;
    }
}