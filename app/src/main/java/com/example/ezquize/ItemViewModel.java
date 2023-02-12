package com.example.ezquize;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ItemViewModel extends ViewModel {
    private final MutableLiveData<String> identificationAnswer = new MutableLiveData<>();
    private final MutableLiveData<String> trueOrFalseAnswer = new MutableLiveData<>();
    private final MutableLiveData<String> multipleChoiceAnswer = new MutableLiveData<String>();


    //getting the value in fragment and setting it to identificationAnswer
    public void setAnswer(String answer){
        identificationAnswer.setValue(answer);
    }

    //send the value retrieve
    public LiveData<String> getAnswer(){
        return identificationAnswer;
    }

    public void setChosenBiPolar(String answer){
        trueOrFalseAnswer.setValue(answer);
    }

    public LiveData<String> getChosenAnswerBiPolar(){
        return trueOrFalseAnswer;
    }

    public void setChosenAnswerMultipleChoice(String answerMultipleChoice){
        multipleChoiceAnswer.setValue(answerMultipleChoice);
    }

    public MutableLiveData<String> getAnswerMultipleChoiceSelected(){
        return multipleChoiceAnswer;
    }
}
