package com.example.ezquize.models;

public class Choice {
    private int id;
    private String answer;
    private int belongs_to_question;

    public Choice() {
    }

    public Choice(int id, String answer) {
        this.id = id;
        this.answer = answer;
    }

    public Choice(int id, String answer, int belongs_to_question) {
        this.id = id;
        this.answer = answer;
        this.belongs_to_question = belongs_to_question;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getBelongs_to_question() {
        return belongs_to_question;
    }

    public void setBelongs_to_question(int belongs_to_question) {
        this.belongs_to_question = belongs_to_question;
    }
}
