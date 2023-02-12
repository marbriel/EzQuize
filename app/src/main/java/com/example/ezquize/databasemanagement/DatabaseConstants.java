package com.example.ezquize.databasemanagement;

public class DatabaseConstants {
    public static final String DATABASE_NAME = "ezquize.db";
    public static final int DATABASE_VERSION = 1;


    // Table names
    public static final String QUESTION_TABLE_NAME = "question_table";




    public static final String SUBJECT_TABLE_NAME = "subject_table";
    public static final String SUBJECT_ID = "subject_id";
    public static final String SUBJECT_NAME = "subject_name";
    public static final String IMAGE_RESOURCE = "image_resource";

    public static final String SUBJECT_TABLE_CREATE = "CREATE TABLE " + SUBJECT_TABLE_NAME + " ("
            + SUBJECT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + SUBJECT_NAME + " TEXT NOT NULL,"
            + IMAGE_RESOURCE + " INTEGER NOT NULL);";

    public static final String LESSON_TABLE_NAME = "lesson_table";
    public static final String LESSON_ID = "lesson_id";
    public static final String LESSON_NAME = "lesson_name";
    public static final String LESSON_NUMBER = "lesson_number";
    public static final String BELONGS_TO_SUBJECT = "belongs_to_subject";
    public static final String LESSON_TABLE_CREATE = "CREATE TABLE " + LESSON_TABLE_NAME + " ("
            + LESSON_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + LESSON_NAME + " TEXT NOT NULL,"
            + LESSON_NUMBER + " TEXT NOT NULL,"
            + BELONGS_TO_SUBJECT + " INTEGER NOT NULL,"
            + "FOREIGN KEY (" + BELONGS_TO_SUBJECT + ")"
            + "REFERENCES "+SUBJECT_TABLE_NAME+ "("+SUBJECT_ID+") ON UPDATE CASCADE ON DELETE CASCADE);";

    public static final String QUESTION_ID = "question_id";
    public static final String QUESTION = "question";
    public static final String ANSWER = "answer";
    public static final String QUESTION_TYPE = "question_type";
    //Table creation commands


    public static final String BELONGS_TO_LESSON = "belongs_to_lesson";
    public static final String QUESTION_TABLE_CREATE = "CREATE TABLE " + QUESTION_TABLE_NAME + "("
            + QUESTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + QUESTION + " TEXT NOT NULL,"
            + ANSWER + " TEXT NOT NULL,"
            + QUESTION_TYPE + " INTEGER NOT NULL,"
            + BELONGS_TO_LESSON+" INTEGER NOT NULL,"
            + " FOREIGN KEY ("+ BELONGS_TO_LESSON +")"
            + " REFERENCES "+LESSON_TABLE_NAME+"("+LESSON_ID+") ON UPDATE CASCADE ON DELETE CASCADE)";

    public static final String MULTIPLE_CHOICE_TABLE = "multiple_choice";
    public static final String CHOICE_ID = "choice_id";
    public static final String CHOICE_NAME = "choice_name";
    public static final String BELONGS_TO_QUESTION = "belongs_to_question";
    public static final String MULTIPLE_CHOICE_TABLE_CREATE = "CREATE TABLE " + MULTIPLE_CHOICE_TABLE + "("
            + CHOICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + CHOICE_NAME + " TEXT NOT NULL,"
            + BELONGS_TO_QUESTION + " INTEGER NOT NULL,"
            + "FOREIGN KEY (" + BELONGS_TO_QUESTION + ")"
            + " REFERENCES "+ QUESTION_TABLE_NAME + "(" + QUESTION_ID + ") ON UPDATE CASCADE ON DELETE CASCADE);";


    public static final String ATTEMPT_REPORT_TABLE = "attempt_report";
    public static final String ATTEMPT_ID = "attempt_id";
    public static final String SCORE = "score";
    public static final String BELONG_TO_LESSON = "belong_to_lesson";
    public static final String ATTEMPT_TABLE_REPORT = "CREATE TABLE " + ATTEMPT_REPORT_TABLE + "(" +
            ATTEMPT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            SCORE + " TEXT NOT NULL," +
            BELONG_TO_LESSON + " INTEGER NOT NULL," +
            "FOREIGN KEY ("+ BELONG_TO_LESSON+") " +
            "REFERENCES " + LESSON_TABLE_NAME + "(" + BELONG_TO_LESSON + ") ON UPDATE CASCADE ON DELETE CASCADE)";


    //Dropping database tables
    public static final String DROP_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS";
    public static final String DROP_QUESTION_TABLE = DROP_TABLE_IF_EXISTS + " " + QUESTION_TABLE_NAME;
    public static final String DROP_SUBJECT_TABLE =  DROP_TABLE_IF_EXISTS + " " + SUBJECT_TABLE_NAME;
    public static final String DROP_LESSON_TABLE = DROP_TABLE_IF_EXISTS+ " "+LESSON_TABLE_NAME;
    public static final String DROP_MULTIPLE_CHOICE_TABLE = DROP_TABLE_IF_EXISTS + " "+ MULTIPLE_CHOICE_TABLE;
    public static final String DROP_ATTEMPT_TABLE_REPORT = DROP_TABLE_IF_EXISTS + " " +  ATTEMPT_TABLE_REPORT;



}
