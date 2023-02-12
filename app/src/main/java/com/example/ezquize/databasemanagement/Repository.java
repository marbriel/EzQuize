package com.example.ezquize.databasemanagement;

import android.content.Context;

import com.example.ezquize.models.Choice;
import com.example.ezquize.models.IdentificationModel;
import com.example.ezquize.models.Lessons;
import com.example.ezquize.models.Question;
import com.example.ezquize.models.Subject;

import java.util.List;

public class Repository {
    private DatabaseManager databaseManager;

    public Repository(Context context){
        this.databaseManager = new DatabaseManager(context);
    }

    public long saveSubjects(Subject subject){
        return databaseManager.saveASubject(subject);
    }

    public List<Subject> getAllSubjects(){
        return databaseManager.getALlSubjects();
    }

    public boolean removeASubject(int subjectId){
        return databaseManager.removeASubject(subjectId);
    }

    public long saveLesson(Lessons lesson){
        return databaseManager.saveLesson(lesson);
    }


    public List<Lessons> getAllLessons(int subjectId) {
        return databaseManager.getAllLessons(subjectId);
    }

    public Question getQuestion(int questionId){
        return databaseManager.getQuestion(questionId);
    }


    public void deleteLesson(int id) {
        databaseManager.deleteALesson(id);
    }

    public void updateLesson(Lessons updatedLesson) {
        databaseManager.updateLesson(updatedLesson);
    }



    public long saveIdentificationQuestion(Question question) {
        return databaseManager.saveQuestion(question);
    }

    public List<Question> getAllQuestions(int lessonId) {
        return databaseManager.getAllQuestions(lessonId);
    }

    public void saveChoices(String choice, long id) {
        databaseManager.saveChoices(choice, id);
    }

    public List<String> getChoicesOfCurrentQuestion(int id) {
        return databaseManager.getAllChoices(id);
    }

    public void deleteQuestion(int id) {
        databaseManager.deleteQuestion(id);
    }

    public void saveAttempt(String score, int lessonId) {
        databaseManager.saveAttempt(score, lessonId);
    }

    public List<String> fetchAllAttempts(int lessonId){
        return databaseManager.getAllAttempts(lessonId);
    }

    public void updateQuestion(Question model) {
        databaseManager.updateQuestion(model);
    }

    public List<Choice> getChoices(int questionId) {
        return databaseManager.getAllChoicesModel(questionId);
    }

    public void updateChoices(String s, int id) {
        databaseManager.updateChoices(s, id);
    }
}
