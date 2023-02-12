package com.example.ezquize.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ezquize.LessonStats;
import com.example.ezquize.LessonsPage;
import com.example.ezquize.R;
import com.example.ezquize.RecyclerViews.SubjectStatsAdapter;
import com.example.ezquize.databasemanagement.Repository;
import com.example.ezquize.listeners.SubjectCardClick;
import com.example.ezquize.models.Subject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Statistics#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Statistics extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Repository repository;
    private SubjectStatsAdapter adapter;
    private List<Subject> subjects = new ArrayList<>();
    private SubjectCardClick cardClick;
    private ActivityResultLauncher<Intent> refresh;
    private RecyclerView statsRecyclerView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Statistics() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Statistics.
     */
    // TODO: Rename and change types and number of parameters
    public static Statistics newInstance(String param1, String param2) {
        Statistics fragment = new Statistics();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistics, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        statsRecyclerView = view.findViewById(R.id.stats_recycler_view);
        repository = new Repository(getContext());
        getAllSubjects();
        setRecyclerView();
        refresh = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                (ActivityResult result) -> {
                    refreshData();
                }
        );
    }

    private void refreshData() {
    }

    protected void getAllSubjects(){
        this.subjects = repository.getAllSubjects();
    }

    protected void setRecyclerView(){
        setClickListener();
        statsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SubjectStatsAdapter(getContext(), this.subjects, cardClick);
        statsRecyclerView.setAdapter(adapter);
    }

    protected void setClickListener(){
        cardClick = new SubjectCardClick() {
            @Override
            public void subjectOnClick(View view, int position) {
                Intent intent = new Intent(getContext(), LessonStats.class);
                intent.putExtra("LESSON_ID", subjects.get(position).getSubjectId());
                refresh.launch(intent);
            }
        };
    }
}