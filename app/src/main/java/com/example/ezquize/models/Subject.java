package com.example.ezquize.models;

public class Subject {
    private int subjectId;
    private String name;
    private int imageSource;
    private String cardBorder;
    private String cardBackground;

    public Subject() {
    }

    public Subject(String name) {
        this.name = name;
    }

    public Subject(String name, int imageSource) {
        this.name = name;
        this.imageSource = imageSource;
    }

    public Subject(int subjectId, String name, int imageSource) {
        this.subjectId = subjectId;
        this.name = name;
        this.imageSource = imageSource;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageSource() {
        return imageSource;
    }

    public void setImageSource(int imageSource) {
        this.imageSource = imageSource;
    }


}
