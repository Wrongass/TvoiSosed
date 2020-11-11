package com.example.moisosed;

public class ModelClass {
    private int imageIcon;
    String title;
    String body;
    private int imageIcon2;

    public ModelClass(int imageIcon, String title, String body, int imageIcon2) {
        this.imageIcon = imageIcon;
        this.title = title;
        this.body = body;
        this.imageIcon2 = imageIcon2;
    }

    public int getImageIcon() {
        return imageIcon;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
    public int getImageIcon2() {
        return imageIcon2;
    }
}
