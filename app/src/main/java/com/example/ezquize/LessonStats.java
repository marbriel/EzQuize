package com.example.ezquize;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.bluetooth.BluetoothAssignedNumbers;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.ezquize.RecyclerViews.LessonStatsAdapter;
import com.example.ezquize.databasemanagement.Repository;
import com.example.ezquize.listeners.LessonOnClickListener;
import com.example.ezquize.models.Lessons;

import java.util.ArrayList;
import java.util.List;

public class LessonStats extends AppCompatActivity {

    private RecyclerView lessonRecyclerStats;
    private LessonStatsAdapter adapter;
    private List<Lessons> lessons = new ArrayList<>();
    private LessonOnClickListener listener;
    private Repository repository;
    private int questionId;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_stats);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFC947")));
        actionBar.setTitle("Lessons");
        bundle = getIntent().getExtras();
        questionId = bundle.getInt("LESSON_ID");
        repository = new Repository(this);
        lessonRecyclerStats = findViewById(R.id.lesson_stats_recycler);
        setAdapter();


    }


    private void setAdapter(){
        setLessons();
        setListener();
        adapter = new LessonStatsAdapter(this, this.lessons, listener);
        lessonRecyclerStats.setLayoutManager(new LinearLayoutManager(this));
        lessonRecyclerStats.setAdapter(adapter);

    }

    private void setLessons(){
        this.lessons = repository.getAllLessons(questionId);
    }

    private void setListener(){
        listener = new LessonOnClickListener() {
            @Override
            public void lessonOnClick(View view, int position) {
                Intent intent = new Intent(LessonStats.this, Tabulate.class);
                intent.putExtra("LESSON_ID", lessons.get(position).getId());
                startActivity(intent);
            }
        };
    }

}