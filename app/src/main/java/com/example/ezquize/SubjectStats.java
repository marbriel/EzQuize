package com.example.ezquize;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.ezquize.RecyclerViews.SubjectRecyclerView;
import com.example.ezquize.RecyclerViews.SubjectStatsAdapter;
import com.example.ezquize.databasemanagement.Repository;
import com.example.ezquize.listeners.SubjectCardClick;
import com.example.ezquize.models.Subject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SubjectStats extends AppCompatActivity {

    private SubjectStatsAdapter adapter;
    private List<Subject> subjects = new ArrayList<>();
    private Repository repository;
    private SubjectCardClick cardClick;
    private ActivityResultLauncher<Intent> refresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_stats);
        repository = new Repository(this);
        refresh = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                (ActivityResult result) -> {
                    refreshData();
                }
        );

    }

    private void refreshData() {

    }


    private void setAdapter(){

    }

    private void setSubjects(){
        subjects.addAll(repository.getAllSubjects());
    }

    private void setOnClickListener(){
        cardClick = new SubjectCardClick() {
            @Override
            public void subjectOnClick(View view, int position) {

            }
        };
    }
}