package com.example.ezquize.models;

public class IdentificationModel extends Question{
    public IdentificationModel() {
    }

    public IdentificationModel(String question, String answer, int typeOfQuestion) {
        super(question, answer, typeOfQuestion);
    }



    public IdentificationModel(String question, String answer, int typeOfQuestion, int belongsToLesson) {
        super(question, answer, typeOfQuestion, belongsToLesson);
    }


}
