package com.example.ezquize.models;

public class Lessons {
    private int id;
    private String lessonName;
    private String lessonNumber;
    private int belongsToSubject;

    public Lessons(int id, String lessonName, String lessonNumber) {
        this.id = id;
        this.lessonName = lessonName;
        this.lessonNumber = lessonNumber;
    }



    public Lessons(String lessonName, String lessonNumber, int belongsToSubject) {
        this.lessonName = lessonName;
        this.lessonNumber = lessonNumber;
        this.belongsToSubject = belongsToSubject;

    }

    public Lessons(int id, String lessonName, String lessonNumber, int belongsToSubject) {
        this.id = id;
        this.lessonName = lessonName;
        this.lessonNumber = lessonNumber;
        this.belongsToSubject = belongsToSubject;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getLessonNumber() {
        return lessonNumber;
    }

    public void setLessonNumber(String lessonNumber) {
        this.lessonNumber = lessonNumber;
    }

    public int getBelongsToSubject() {
        return belongsToSubject;
    }

    public void setBelongsToSubject(int belongsToSubject) {
        this.belongsToSubject = belongsToSubject;
    }

    @Override
    public String toString() {
        return "Lessons{" +
                "id=" + id +
                ", lessonName='" + lessonName + '\'' +
                ", lessonNumber='" + lessonNumber + '\'' +
                ", belongsToSubject=" + belongsToSubject +
                '}';
    }
}
