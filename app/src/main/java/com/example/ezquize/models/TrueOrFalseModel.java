package com.example.ezquize.models;

public class TrueOrFalseModel extends Question{

    public TrueOrFalseModel() {
    }

    public TrueOrFalseModel(String question, String answer, int typeOfQuestion) {
        super(question, answer, typeOfQuestion);
    }

    public TrueOrFalseModel(int id, String question, String answer) {
        super(id, question, answer);
    }
}
