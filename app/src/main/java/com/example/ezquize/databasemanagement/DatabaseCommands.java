package com.example.ezquize.databasemanagement;
import static com.example.ezquize.databasemanagement.DatabaseConstants.*;

public class DatabaseCommands {



    //SUBJECT COMMANDS
    public static final String GET_ALL_SUBJECTS = "SELECT * FROM " + SUBJECT_TABLE_NAME;
    public static final String DELETE_SUBJECT = "DELETE FROM " + SUBJECT_TABLE_NAME + " WHERE " + SUBJECT_ID + " = ";

    //LESSON COMMANDS
    public static final String GET_ALL_LESSONS = "SELECT * FROM " + LESSON_TABLE_NAME + " WHERE " + BELONGS_TO_SUBJECT + " = ";
    public static final String DELETE_LESSON = "DELETE FROM " + LESSON_TABLE_NAME + " WHERE " + LESSON_ID + " = ";

    //QUESTIONS COMMANDS
    public static final String GET_ALL_QUESTIONS = "SELECT * FROM " + QUESTION_TABLE_NAME + " WHERE " + BELONGS_TO_LESSON + " = ";
    public static final String DELETE_QUESTION = "DELETE FROM " + QUESTION_TABLE_NAME + " WHERE " + QUESTION_ID + " = ";
    public static final String GET_QUESTION = "SELECT * FROM " + QUESTION_TABLE_NAME + " WHERE " + QUESTION_ID + " = ";


    //CHOICES COMMANDS
    public static final String GET_ALL_CHOICES = "SELECT * FROM " + MULTIPLE_CHOICE_TABLE + " WHERE " + BELONGS_TO_QUESTION + " = ";



    //ATTEMPT COMMANDS
    public static final String GET_ALL_ATTEMPTS = "SELECT * FROM " + ATTEMPT_REPORT_TABLE + " WHERE " + BELONG_TO_LESSON + "=";


}

