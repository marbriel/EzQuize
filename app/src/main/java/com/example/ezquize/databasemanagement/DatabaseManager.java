package com.example.ezquize.databasemanagement;

import static com.example.ezquize.databasemanagement.DatabaseCommands.DELETE_LESSON;
import static com.example.ezquize.databasemanagement.DatabaseCommands.DELETE_QUESTION;
import static com.example.ezquize.databasemanagement.DatabaseCommands.DELETE_SUBJECT;
import static com.example.ezquize.databasemanagement.DatabaseCommands.GET_ALL_ATTEMPTS;
import static com.example.ezquize.databasemanagement.DatabaseCommands.GET_ALL_CHOICES;
import static com.example.ezquize.databasemanagement.DatabaseCommands.GET_ALL_LESSONS;
import static com.example.ezquize.databasemanagement.DatabaseCommands.GET_ALL_QUESTIONS;
import static com.example.ezquize.databasemanagement.DatabaseCommands.GET_ALL_SUBJECTS;
import static com.example.ezquize.databasemanagement.DatabaseCommands.GET_QUESTION;
import static com.example.ezquize.databasemanagement.DatabaseConstants.ANSWER;
import static com.example.ezquize.databasemanagement.DatabaseConstants.ATTEMPT_ID;
import static com.example.ezquize.databasemanagement.DatabaseConstants.ATTEMPT_REPORT_TABLE;
import static com.example.ezquize.databasemanagement.DatabaseConstants.ATTEMPT_TABLE_REPORT;
import static com.example.ezquize.databasemanagement.DatabaseConstants.BELONGS_TO_LESSON;
import static com.example.ezquize.databasemanagement.DatabaseConstants.BELONGS_TO_QUESTION;
import static com.example.ezquize.databasemanagement.DatabaseConstants.BELONGS_TO_SUBJECT;
import static com.example.ezquize.databasemanagement.DatabaseConstants.BELONG_TO_LESSON;
import static com.example.ezquize.databasemanagement.DatabaseConstants.CHOICE_ID;
import static com.example.ezquize.databasemanagement.DatabaseConstants.CHOICE_NAME;
import static com.example.ezquize.databasemanagement.DatabaseConstants.DATABASE_NAME;
import static com.example.ezquize.databasemanagement.DatabaseConstants.DATABASE_VERSION;
import static com.example.ezquize.databasemanagement.DatabaseConstants.DROP_ATTEMPT_TABLE_REPORT;
import static com.example.ezquize.databasemanagement.DatabaseConstants.DROP_LESSON_TABLE;
import static com.example.ezquize.databasemanagement.DatabaseConstants.DROP_MULTIPLE_CHOICE_TABLE;
import static com.example.ezquize.databasemanagement.DatabaseConstants.DROP_QUESTION_TABLE;
import static com.example.ezquize.databasemanagement.DatabaseConstants.DROP_SUBJECT_TABLE;
import static com.example.ezquize.databasemanagement.DatabaseConstants.IMAGE_RESOURCE;
import static com.example.ezquize.databasemanagement.DatabaseConstants.LESSON_ID;
import static com.example.ezquize.databasemanagement.DatabaseConstants.LESSON_NAME;
import static com.example.ezquize.databasemanagement.DatabaseConstants.LESSON_NUMBER;
import static com.example.ezquize.databasemanagement.DatabaseConstants.LESSON_TABLE_CREATE;
import static com.example.ezquize.databasemanagement.DatabaseConstants.LESSON_TABLE_NAME;
import static com.example.ezquize.databasemanagement.DatabaseConstants.MULTIPLE_CHOICE_TABLE;
import static com.example.ezquize.databasemanagement.DatabaseConstants.MULTIPLE_CHOICE_TABLE_CREATE;
import static com.example.ezquize.databasemanagement.DatabaseConstants.QUESTION;
import static com.example.ezquize.databasemanagement.DatabaseConstants.QUESTION_ID;
import static com.example.ezquize.databasemanagement.DatabaseConstants.QUESTION_TABLE_CREATE;
import static com.example.ezquize.databasemanagement.DatabaseConstants.QUESTION_TABLE_NAME;
import static com.example.ezquize.databasemanagement.DatabaseConstants.QUESTION_TYPE;
import static com.example.ezquize.databasemanagement.DatabaseConstants.SCORE;
import static com.example.ezquize.databasemanagement.DatabaseConstants.SUBJECT_NAME;
import static com.example.ezquize.databasemanagement.DatabaseConstants.SUBJECT_TABLE_CREATE;
import static com.example.ezquize.databasemanagement.DatabaseConstants.SUBJECT_TABLE_NAME;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.ezquize.models.Choice;
import com.example.ezquize.models.IdentificationModel;
import com.example.ezquize.models.Lessons;
import com.example.ezquize.models.Question;
import com.example.ezquize.models.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class DatabaseManager extends SQLiteOpenHelper {
    public DatabaseManager(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SUBJECT_TABLE_CREATE);
        sqLiteDatabase.execSQL(LESSON_TABLE_CREATE);
        sqLiteDatabase.execSQL(QUESTION_TABLE_CREATE);
        sqLiteDatabase.execSQL(MULTIPLE_CHOICE_TABLE_CREATE);
        sqLiteDatabase.execSQL(ATTEMPT_TABLE_REPORT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_SUBJECT_TABLE);
        sqLiteDatabase.execSQL(DROP_LESSON_TABLE);
        sqLiteDatabase.execSQL(DROP_QUESTION_TABLE);
        sqLiteDatabase.execSQL(DROP_MULTIPLE_CHOICE_TABLE);
        sqLiteDatabase.execSQL(DROP_ATTEMPT_TABLE_REPORT);
        onCreate(sqLiteDatabase);
    }

    // COMMANDS FOR QUESTION TABLE
    public List<Subject> getALlSubjects(){
        List<Subject> questions = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(GET_ALL_SUBJECTS, null);
        if(cursor.moveToFirst()){
            do{
                Subject subject = new Subject(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
                questions.add(subject);
            }while(cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return questions;
    }

    public long saveASubject(Subject subject){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SUBJECT_NAME, subject.getName());
        values.put(IMAGE_RESOURCE, subject.getImageSource());
        long result = db.insert(SUBJECT_TABLE_NAME, null, values);
        db.close();
        return result;

    }

    public boolean removeASubject(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(DELETE_SUBJECT + id, null);
        boolean result = cursor.moveToFirst();
        db.close();
        cursor.close();
        return result;
    }


    public long saveLesson(Lessons lesson) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(LESSON_NAME, lesson.getLessonName());
        values.put(LESSON_NUMBER, lesson.getLessonNumber());
        values.put(BELONGS_TO_SUBJECT, lesson.getBelongsToSubject());
        long result = db.insert(LESSON_TABLE_NAME, null, values);
        db.close();
        return result;
    }

    public List<Lessons> getAllLessons(int subjectId) {
        List<Lessons> lessons = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(GET_ALL_LESSONS + subjectId, null);
        if(cursor.moveToFirst()){
            do{
                Lessons lesson = new Lessons(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3));
                lessons.add(lesson);
            }while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return lessons;
    }

    public void deleteALesson(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(DELETE_LESSON + id, null);
        cursor.moveToFirst();
        db.close();
    }

    public long updateLesson(Lessons updatedLesson) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(LESSON_NAME, updatedLesson.getLessonName());
        values.put(LESSON_NUMBER, updatedLesson.getLessonNumber());
        long result = db.update(LESSON_TABLE_NAME, values, LESSON_ID + "=" + updatedLesson.getId(), null);
        db.close();
        return result;

    }



    public long saveQuestion(Question question) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(QUESTION, question.getQuestion());
        values.put(ANSWER, question.getAnswer());
        values.put(QUESTION_TYPE, question.getTypeOfQuestion());
        values.put(BELONGS_TO_LESSON, question.getBelongsToLesson());
        long id = db.insert(QUESTION_TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public List<Question> getAllQuestions(int lessonId) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Question> questions = new ArrayList<>();
        Cursor cursor = db.rawQuery(GET_ALL_QUESTIONS + lessonId, null);
        if(cursor.moveToFirst()){
            do{
                Question question =
                        new Question(cursor.getInt(0), cursor.getString(1),
                                cursor.getString(2), cursor.getInt(3), cursor.getInt(4));
                questions.add(question);
            }while(cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return questions;
    }

    public Question getQuestion(int questionId){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(GET_QUESTION + questionId, null);
        Question question = new Question();
        if(cursor.moveToFirst()){
            question.setId(cursor.getInt(0));
            question.setQuestion(cursor.getString(1));
            question.setAnswer(cursor.getString(2));
        }
        db.close();
        cursor.close();
        return question;

    }

    public void saveChoices(String choice, long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CHOICE_NAME, choice);
        values.put(BELONGS_TO_QUESTION, id);
        db.insert(MULTIPLE_CHOICE_TABLE, null, values);
        db.close();
    }

    public List<String> getAllChoices(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> choices = new ArrayList<>();
        Cursor cursor = db.rawQuery(GET_ALL_CHOICES + id, null);
        if(cursor.moveToFirst()){
            do{
                choices.add(cursor.getString(1));
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return choices;
    }

    public void deleteQuestion(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(DELETE_QUESTION + id, null);
        cursor.moveToFirst();
    }

    public void saveAttempt(String score, int lessonId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SCORE, String.valueOf(score));
        values.put(BELONG_TO_LESSON, String.valueOf(lessonId));
        db.insert(ATTEMPT_REPORT_TABLE, null, values);
        db.close();
    }

    public List<String> getAllAttempts(int lessonId){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> fetchRecords = new ArrayList<>();
        Cursor cursor = db.rawQuery(GET_ALL_ATTEMPTS + lessonId, null);
        if(cursor.moveToFirst()){
            do{
                fetchRecords.add(cursor.getString(1));
            }while(cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return fetchRecords;
    }

    public void updateQuestion(Question model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(QUESTION, model.getQuestion());
        values.put(ANSWER, model.getAnswer());
        db.update(QUESTION_TABLE_NAME, values, QUESTION_ID+"="+model.getId(), null);
        db.close();
    }

    public List<Choice> getAllChoicesModel(int questionId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(GET_ALL_CHOICES + questionId, null);
        List<Choice> choices = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                Choice choice = new Choice(cursor.getInt(0), cursor.getString(1));
                choices.add(choice);
            }while(cursor.moveToNext());
        }

        db.close();
        cursor.close();
        return choices;
    }

    public void updateChoices(String s, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CHOICE_NAME, s);
        db.update(MULTIPLE_CHOICE_TABLE, contentValues, CHOICE_ID +"="+id, null);
        db.close();
    }
}
