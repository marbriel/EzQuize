package com.example.ezquize.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MultipleChoiceModel extends Question{
    private HashMap<String, Boolean> choices;

    public MultipleChoiceModel() {
    }

    public MultipleChoiceModel(String question, String answer, int typeOfQuestion, HashMap<String, Boolean> choices) {
        super(question, answer, typeOfQuestion);
        this.choices = choices;
    }

    public MultipleChoiceModel(int id, String question, String answer, HashMap<String, Boolean> choices) {
        super(id, question, answer);
        this.choices = choices;
    }

    public HashMap<String, Boolean> getChoices() {
        return choices;
    }

    public void setChoices(HashMap<String, Boolean> choices) {
        this.choices = choices;
    }

    public void addChoices(String choice, Boolean key){
        choices.put(choice, key);
    }

    public String getCorrectAnswer(){
        String answer = "";
        for (Map.Entry<String, Boolean> choice:choices.entrySet()) {
            if(choice.getValue()){
                answer = choice.getKey();
            }
        }
        return answer;
    }
}
