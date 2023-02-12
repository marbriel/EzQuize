package com.example.ezquize.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.ezquize.AddSubjectForm;
import com.example.ezquize.LessonsPage;
import com.example.ezquize.R;
import com.example.ezquize.RecyclerViews.SubjectRecyclerView;
import com.example.ezquize.databasemanagement.Repository;
import com.example.ezquize.listeners.SubjectCardClick;
import com.example.ezquize.listeners.SubjectCardLongClick;
import com.example.ezquize.models.Subject;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Shelf#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Shelf extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //items
    private RecyclerView subjectRecyclerView;
    private SubjectRecyclerView subjectAdapter;
    private List<Subject> subjects = new ArrayList<>();
    private FloatingActionButton toAddSubjectForm;
    private SubjectCardClick cardOnClick;
    private SubjectCardLongClick cardOnLongClick;
    private ActivityResultLauncher<Intent> refresh;
    private Repository repository;

    public Shelf() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Shelf.
     */
    // TODO: Rename and change types and number of parameters
    public static Shelf newInstance(String param1, String param2) {
        Shelf fragment = new Shelf();
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
        return inflater.inflate(R.layout.fragment_shelf, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        subjectRecyclerView = view.findViewById(R.id.subject_recycler);
        toAddSubjectForm = view.findViewById(R.id.add_subject);
        repository = new Repository(getContext());
        getAllSubjects();
        setRecyclerView();
        refresh = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                (ActivityResult result) -> {
                    refreshData();
                }
        );
        
        
        //Go to add subject form
        toAddSubjectForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddSubjectForm.class);
                refresh.launch(intent);
            }
        });
    }

    protected void getAllSubjects(){
        this.subjects = repository.getAllSubjects();
    }

    protected void setRecyclerView(){
        setClickListener();
        setOnLongClick();
        subjectRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        subjectAdapter = new SubjectRecyclerView(getContext(), this.subjects, cardOnClick, cardOnLongClick);
        subjectRecyclerView.setAdapter(subjectAdapter);
    }

    protected void setClickListener(){
        cardOnClick = new SubjectCardClick() {
            @Override
            public void subjectOnClick(View view, int position) {
                Intent intent = new Intent(getContext(), LessonsPage.class);
                intent.putExtra("SUBJECT_ID", subjects.get(position).getSubjectId());
                refresh.launch(intent);
            }
        };
    }

    protected void setOnLongClick(){
        cardOnLongClick = new SubjectCardLongClick() {
            @Override
            public boolean subjectOnLongClick(View view, int position) {
                AlertDialog dialog = new AlertDialog.Builder(Shelf.this.getContext())
                        .setTitle(subjects.get(position).getName())
                        .setMessage(Html.fromHtml("<font color='#474747'>Do you really want to remove this subject from your shelf?</font>"))
                        .setCancelable(false)
                        .setPositiveButton(Html.fromHtml("<font color='#373B3E'>Yes</font>"), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                repository.removeASubject(subjects.get(position).getSubjectId());
                                Toast.makeText(getContext(), "Deleting " + subjects.get(position).getName(), Toast.LENGTH_SHORT).show();
                                refreshData();
                            }
                        })
                        .setNegativeButton(Html.fromHtml("<font color='#373B3E'>No</font>"), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create();
                dialog.show();
                return true;
            }
        };
    }


    protected void refreshData(){
        subjects.clear();
        subjects.addAll(repository.getAllSubjects());
        subjectAdapter.notifyDataSetChanged();
        Collections.sort(subjects, (subject, t1) -> subject.getName().compareTo(t1.getName()));
    }



}