package com.example.ezquize;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.ezquize.RecyclerViews.LessonRecyclerView;
import com.example.ezquize.databasemanagement.Repository;
import com.example.ezquize.listeners.LessonOnClickListener;
import com.example.ezquize.listeners.LessonOnLongClick;
import com.example.ezquize.listeners.PlayButtonListener;
import com.example.ezquize.models.Lessons;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class LessonsPage extends AppCompatActivity {

    private List<Lessons> lessons = new ArrayList<>();
    private RecyclerView lessonRecyclerView;
    private LessonRecyclerView lessonAdapter;
    private LessonOnClickListener onClickListener;
    private LessonOnLongClick lessonOnLongClick;
    private PlayButtonListener playButtonListener;
    private FloatingActionButton addLessonButton;
    private Repository repository;
    private Bundle bundle;
    private int subjectId;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons_page);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFC947")));
        actionBar.setTitle("Lessons");

        //initialize everything
        bundle = getIntent().getExtras();
        this.subjectId = bundle.getInt("SUBJECT_ID");
        lessonRecyclerView = findViewById(R.id.lesson_recycler_view);
        repository = new Repository(this);
        setAdapter();

        addLessonButton = findViewById(R.id.add_lesson_floating_btn);
        addLessonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddFormLesson();
                refreshData();
            }
        });

    }

    private void setAdapter() {
        setLessonOnLongClick();
        setOnClickListener();
        setPlayButtonListener();
        this.lessons.addAll(repository.getAllLessons(this.subjectId));
        lessonAdapter = new LessonRecyclerView(this, lessons, onClickListener, playButtonListener, lessonOnLongClick);
        lessonRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        lessonRecyclerView.setAdapter(lessonAdapter);

    }



    private void setOnClickListener(){
        onClickListener = new LessonOnClickListener() {
            @Override
            public void lessonOnClick(View view, int position) {
                Intent intent = new Intent(LessonsPage.this, QuestionnairePage.class);
                intent.putExtra("LESSON_ID", lessons.get(position).getId());
                startActivity(intent);
            }

        };
    }

    private void setLessonOnLongClick(){
        lessonOnLongClick = new LessonOnLongClick(){

            @Override
            public boolean lessonLongClick(View view, int position) {
                PopupMenu popupMenu = new PopupMenu(LessonsPage.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.memu_card, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.update:
                                showUpdateForm(lessons.get(position));
                                break;
                            case R.id.delete:
                                Toast.makeText(LessonsPage.this, "Delete", Toast.LENGTH_SHORT).show();
                                repository.deleteLesson(lessons.get(position).getId());
                                refreshData();
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
                return true;
            }
        };
    }

    private void setPlayButtonListener(){
        
        playButtonListener = new PlayButtonListener() {
            @Override
            public void playButtonListener(View view, int position) {
                if(repository.getAllQuestions(lessons.get(position).getId()).size() >0){
                    Intent intent = new Intent(LessonsPage.this, TestPage.class);
                    intent.putExtra("LESSON_ID", lessons.get(position).getId());
                    startActivity(intent);
                }else{
                    Toast.makeText(LessonsPage.this, "No questions tos show", Toast.LENGTH_SHORT).show();
                }
                
            }
        };
    }

    private void showAddFormLesson(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(LessonsPage.this);
        View view = LayoutInflater.from(LessonsPage.this).inflate(R.layout.add_lesson_menu, null);
        dialog.setView(view);
        final AlertDialog alert = dialog.create();
        Button button = view.findViewById(R.id.add_lesson_button);
        EditText lessonNameEdt =view.findViewById(R.id.lesson_name_form);
        EditText lessonNumberEdt = view.findViewById(R.id.lesson_number_form);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lessonNameEdt.getText().toString().isEmpty()){
                    Toast.makeText(LessonsPage.this, "Please enter a name", Toast.LENGTH_SHORT).show();
                }else if(lessonNumberEdt.getText().toString().isEmpty()){
                    Toast.makeText(LessonsPage.this, "Please enter a number", Toast.LENGTH_SHORT).show();
                }else{
                    Lessons lesson = new Lessons(String.valueOf(lessonNameEdt.getText()), String.valueOf(lessonNumberEdt.getText()), LessonsPage.this.subjectId);
                    LessonsPage.this.repository.saveLesson(lesson);
                    refreshData();
                    alert.dismiss();
                }
            }
        });
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert.show();
        refreshData();

    }

    private void refreshData(){
        this.lessons.clear();
        this.lessons.addAll(getLessons());
        lessonAdapter.notifyDataSetChanged();
    }


    private List<Lessons> getLessons(){
        return repository.getAllLessons(this.subjectId);
    }

    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
    private void showUpdateForm(Lessons lessonToUpdate){
        AlertDialog.Builder dialog = new AlertDialog.Builder(LessonsPage.this);
        View view = LayoutInflater.from(LessonsPage.this).inflate(R.layout.update_lesson, null);
        dialog.setView(view);
        final AlertDialog alert = dialog.create();
        Button button = view.findViewById(R.id.update_lesson_button);
        EditText lessonNameEdt =view.findViewById(R.id.update_lesson_name);
        EditText lessonNumberEdt = view.findViewById(R.id.update_lesson_number);
        lessonNumberEdt.setText(lessonToUpdate.getLessonNumber());
        lessonNameEdt.setText(lessonToUpdate.getLessonName());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lessonNameEdt.getText().toString().isEmpty()){
                    Toast.makeText(LessonsPage.this, "Please enter a name", Toast.LENGTH_SHORT).show();
                }else if(lessonNumberEdt.getText().toString().isEmpty()){
                    Toast.makeText(LessonsPage.this, "Please enter a number", Toast.LENGTH_SHORT).show();
                }else{
                    Lessons updatedLesson = new Lessons(lessonToUpdate.getId(),
                            String.valueOf(lessonNameEdt.getText()), String.valueOf(lessonNumberEdt.getText()), LessonsPage.this.subjectId);
                    repository.updateLesson(updatedLesson);
                    refreshData();
                    alert.dismiss();
                }

            }
        });
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert.show();
        refreshData();

    }
}