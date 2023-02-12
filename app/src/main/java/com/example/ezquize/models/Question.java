package com.example.ezquize.models;

public class Question {
    private int id;
    private String question;
    private String answer;
    private int typeOfQuestion;
    private int belongsToLesson;



    public Question() {
    }

    public Question(String question, String answer, int typeOfQuestion) {
        this.question = question;
        this.answer = answer;
        this.typeOfQuestion = typeOfQuestion;
    }

    public Question(int id, String question, String answer) {
        this.id = id;
        this.question = question;
        this.answer = answer;
    }

    public Question(String question, String answer, int typeOfQuestion, int belongsToLesson) {
        this.question = question;
        this.answer = answer;
        this.typeOfQuestion = typeOfQuestion;
        this.belongsToLesson = belongsToLesson;
    }

    public Question(int id, String question, String answer, int typeOfQuestion, int belongsToLesson) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.typeOfQuestion = typeOfQuestion;
        this.belongsToLesson = belongsToLesson;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getTypeOfQuestion() {
        return typeOfQuestion;
    }

    public void setTypeOfQuestion(int typeOfQuestion) {
        this.typeOfQuestion = typeOfQuestion;
    }

    public int getBelongsToLesson() {
        return belongsToLesson;
    }

    public void setBelongsToLesson(int belongsToLesson) {
        this.belongsToLesson = belongsToLesson;
    }

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", typeOfQuestion=" + typeOfQuestion +
                ", belongsToLesson=" + belongsToLesson +
                '}';
    }
}
