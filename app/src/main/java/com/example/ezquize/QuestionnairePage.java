package com.example.ezquize;

import static com.example.ezquize.models.TypeOfQuestion.IDENTIFICATION;
import static com.example.ezquize.models.TypeOfQuestion.TRUE_OR_FALSE;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.ezquize.RecyclerViews.QuestionRecyclerView;
import com.example.ezquize.databasemanagement.Repository;
import com.example.ezquize.models.Question;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class QuestionnairePage extends AppCompatActivity {
    
    private FloatingActionButton multipleChoiceButton, identificationButton, trueOrFalseButton;
    private List<Question> questions = new ArrayList<>();
    private QuestionRecyclerView questionCardAdapter;
    private RecyclerView questionCardRecyclerView;
    private QuestionRecyclerView.QuestionCardOnLongClickListener longClickListener;
    private ActivityResultLauncher<Intent> refresh;
    private int lessonId;
    private Repository repository;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire_page);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFC947")));
        actionBar.setTitle("Questionnaire");
        
        //initialize views
        multipleChoiceButton = findViewById(R.id.multiple_choice);
        identificationButton = findViewById(R.id.identification);
        trueOrFalseButton = findViewById(R.id.trueorfalse);
        questionCardRecyclerView = findViewById(R.id.question_card_recycler_view);
        repository = new Repository(this);
        Bundle bundle = getIntent().getExtras();
        lessonId = bundle.getInt("LESSON_ID");
        refresh = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                (ActivityResult result) -> {
                    refreshData();
                }
        );

        //setting up adapter
        setAdapter();
        
        
        
        //set on click listeners
        multipleChoiceButton.setOnClickListener(view -> {
            Intent intent = new Intent(QuestionnairePage.this, MultipleChoiceCreationPage.class);
            intent.putExtra("BELONGS_TO", lessonId);
            refresh.launch(intent);
        });
        
        identificationButton.setOnClickListener(view -> {
            Intent intent = new Intent(QuestionnairePage.this, IdentificationCreationPage.class);
            intent.putExtra("BELONGS_TO", lessonId);
            refresh.launch(intent);
        });
        
        trueOrFalseButton.setOnClickListener(view -> {
            Intent intent = new Intent(QuestionnairePage.this, TrueOrFalseCreationPage.class);
            intent.putExtra("BELONGS_TO", lessonId);
            refresh.launch(intent);
        });
    }

    private void refreshData() {
        questions.clear();
        questions.addAll(initData());
        questionCardAdapter.notifyDataSetChanged();
    }


    private void setAdapter(){
        this.questions.addAll(initData());
        setOnLongClickListener();
        questionCardAdapter = new QuestionRecyclerView(this.questions, this, longClickListener);
        questionCardRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        questionCardRecyclerView.setAdapter(questionCardAdapter);
    }

    private void setOnLongClickListener(){
        longClickListener = (view, position) -> {
           showPopUpMenu(view, position);
            return true;
        };
    }





    private List<Question> initData(){
        return repository.getAllQuestions(lessonId);
    }

    private void showPopUpMenu(View view, int position){
        PopupMenu popupMenu = new PopupMenu(QuestionnairePage.this, view);
        popupMenu.getMenuInflater().inflate(R.menu.memu_card, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.update:
                        if(questions.get(position).getTypeOfQuestion() == IDENTIFICATION){
                            Intent intent = new Intent(QuestionnairePage.this, IdentificationCreationPage.class);
                            intent.putExtra("UPDATE", 26);
                            intent.putExtra("QUESTION_ID", questions.get(position).getId());
                            refresh.launch(intent);
                        }else if(questions.get(position).getTypeOfQuestion() == TRUE_OR_FALSE){
                            Intent intent = new Intent(QuestionnairePage.this, TrueOrFalseCreationPage.class);
                            intent.putExtra("UPDATE", 26);
                            intent.putExtra("QUESTION_ID", questions.get(position).getId());
                            refresh.launch(intent);
                        }else{
                            Intent intent = new Intent(QuestionnairePage.this, MultipleChoiceCreationPage.class);
                            intent.putExtra("UPDATE", 26);
                            intent.putExtra("QUESTION_ID", questions.get(position).getId());
                            refresh.launch(intent);
                        }
                        break;
                    case R.id.delete:
                        repository.deleteQuestion(questions.get(position).getId());
                        refreshData();
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }
}