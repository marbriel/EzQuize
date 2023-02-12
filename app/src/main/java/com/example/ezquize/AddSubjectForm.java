package com.example.ezquize;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import com.example.ezquize.RecyclerViews.AddSubjectRecyclerView;
import com.example.ezquize.databasemanagement.Repository;
import com.example.ezquize.listeners.SubjectAddListener;
import com.example.ezquize.models.Subject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class AddSubjectForm extends AppCompatActivity implements SubjectAddListener{

    private RecyclerView subjectsRecyclerView;
    private List<Subject> subjects = new ArrayList<>();
    private AddSubjectRecyclerView subjectAdapter;
    private SubjectAddListener subjectAddListener;
    private Repository repository;
    private androidx.appcompat.widget.SearchView searchName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject_form);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFC947")));
        actionBar.setTitle("Subjects");

        //initialize views
        subjectsRecyclerView = findViewById(R.id.add_subjects_recyclerview);
        searchName = findViewById(R.id.search_subject_name);
        searchName.clearFocus();

        //searchName on change listener
        searchName.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                subjectAdapter.getFilter().filter(newText);
                return false;
            }
        });


        initSubjects();
        setRecyclerView();
        repository = new Repository(this);

    }






    private void initSubjects() {
        Subject math = new Subject("Mathematics", R.drawable.math_icon);
        Subject science = new Subject("Science", R.drawable.science);
        Subject english = new Subject("English", R.drawable.english);
        Subject readingAndWriting = new Subject("Reading and Writing");
        Subject komunikasyon = new Subject("Komunikayon at Pananaliksik sa Wika at Kulturang Pilipino");
        Subject centuryLiterature = new Subject("21st Century Literature from the Philippines and the World");
        Subject contemporaryArts = new Subject("Contemporary Philippine Arts from the Philippines");
        Subject stats = new Subject("Statistics and Probability");
        Subject physci = new Subject("Physical Science");
        Subject pe1 = new Subject("Physical Education and Health 1");
        Subject pe2 = new Subject("Physical Education and Health 2");
        Subject pe3 = new Subject("Physical Education and Health 3");
        Subject pilingLarang = new Subject("Filipino sa Piling Larang");
        Subject discipline = new Subject("Discipline and Ideas in Social Science");
        Subject entrep = new Subject("Entrepreneurship");
        Subject practicalResearch2 = new Subject("Practical Research 2");
        Subject practicalResearch1 = new Subject("Practical Research 1");
        Subject iii = new Subject("Inquiries, Investigations and Immersion");
        Subject nonFic = new Subject("Creative Nonfiction");
        Subject trends = new Subject("Trends, Network and Critical Thinking in the 21st Century Culture");
        Subject cess = new Subject("Community Engagement, Solidarity and Citizenship");
        Subject mil = new Subject("Media and Information Literacy");
        Subject perdev = new Subject("Personal Development");
        Subject etch = new Subject("Empowerment Technology");
        Subject creativeWriting = new Subject("Creative Writing");
        Subject politics = new Subject("Philippine Politics and Governance");
        Subject immersion = new Subject("Work Immersion");
        Subject oralcom = new Subject("Oral Communication");
        Subject genMath = new Subject("General Mathematics");
        Subject els = new Subject("Earth and Life Science");
        Subject ucsp = new Subject("Understanding Culture, Society and Politics");
        Subject philo = new Subject("Introduction to the Philosophy of the Human Person");
        Subject eapp = new Subject("English for Academic and Professional Purposes");
        Subject religion = new Subject("Introduction to World Religions and Beliefs Systems");




        subjects = new ArrayList<>(
                Arrays.asList(readingAndWriting, komunikasyon, centuryLiterature, contemporaryArts, stats, physci, pe1, pe2, pe3,
                        pilingLarang, discipline, entrep, practicalResearch1, practicalResearch2, iii, nonFic, trends, cess, mil,
                        perdev, etch, creativeWriting, politics, immersion, oralcom, genMath, els, ucsp, philo, eapp, religion)
        );

        Collections.sort(subjects, (subject, t1) -> subject.getName().compareTo(t1.getName()));


    }

    private void setRecyclerView() {
        subjectAdapter = new AddSubjectRecyclerView(this, this.subjects, this::subjectAddListener);
        subjectsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        subjectsRecyclerView.setAdapter(subjectAdapter);
    }

    @Override
    public void subjectAddListener(Subject subject) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage(String.format("Do you want to add %s to your shelf? ", subject.getName()))
                .setCancelable(false)
                .setPositiveButton(Html.fromHtml("<font color='#373B3E'>Yes</font>"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        long result = repository.saveSubjects(subject);
                        if(result > 0){
                            Toast.makeText(AddSubjectForm.this, "Successfully added", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(AddSubjectForm.this, "There's an error saving the subject", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton(Html.fromHtml("<font color='#373B3E'>No</font>"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create();
        dialog.show();
    }






   /* private void setSubjectAddClickListener(){
        subjectAddListener = (view, position) -> {
            AlertDialog message = new AlertDialog.Builder(AddSubjectForm.this)
                    .setMessage(String.format("Do you want to add %s to your shelf?", subjects.get(position).getName()))
                    .setCancelable(false)
                    .setPositiveButton(Html.fromHtml("<font color='#373B3E'>Yes</font>"), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Subject subject = subjects.get(position);
                            long result = repository.saveSubjects(subject);
                            if(result > 0){
                                Toast.makeText(AddSubjectForm.this, "Successfully added", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(AddSubjectForm.this, "There's an error saving the subject", Toast.LENGTH_SHORT).show();
                            }

                        }
                    })
                    .setNegativeButton(Html.fromHtml("<font color='#373B3E'>No</font>"), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(AddSubjectForm.this, "Cancel", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .create();
            message.show();
        };
    }*/
}