package com.example.ezquize;

import static com.example.ezquize.models.TypeOfQuestion.IDENTIFICATION;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ezquize.databasemanagement.Repository;
import com.example.ezquize.models.IdentificationModel;
import com.example.ezquize.models.Question;
import com.google.android.material.textfield.TextInputEditText;

public class IdentificationCreationPage extends AppCompatActivity {
    private TextInputEditText question, answer;
    private Button saveButton;
    private Repository repository;
    private int lessonId, isToUpdate, questionId;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identification_creation_page);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Identification");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#373B3E")));
        question = findViewById(R.id.question_identification);
        answer = findViewById(R.id.answer_identification);
        saveButton = findViewById(R.id.button_identification);
        Bundle bundle = getIntent().getExtras();
        lessonId = bundle.getInt("BELONGS_TO");
        repository = new Repository(this);
        Toast.makeText(this, String.valueOf(lessonId), Toast.LENGTH_SHORT).show();
        isToUpdate = bundle.getInt("UPDATE");
        if(isToUpdate == 26){
            saveButton.setText("UPDATE");
            questionId = bundle.getInt("QUESTION_ID");
            Question questionModel = repository.getQuestion(questionId);
            Toast.makeText(this, String.valueOf(questionId), Toast.LENGTH_SHORT).show();
            question.setText(questionModel.getQuestion());
            answer.setText(questionModel.getAnswer());
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IdentificationModel model = new IdentificationModel();
                    model.setQuestion(question.getText().toString());
                    model.setAnswer(answer.getText().toString());
                    model.setId(questionId);
                    if(question.getText().toString().isEmpty() || answer.getText().toString().isEmpty()) {
                        Toast.makeText(IdentificationCreationPage.this, "Incomplete information", Toast.LENGTH_SHORT).show();
                    }else{
                        repository.updateQuestion(model);
                        onBackPressed();
                    }
                }
            });
        }else{
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String quest = String.valueOf(question.getText());
                    String ans = String.valueOf(answer.getText());
                    if(quest.isEmpty() || ans.isEmpty()){
                        Toast.makeText(IdentificationCreationPage.this, "Incomplete information", Toast.LENGTH_SHORT).show();
                    }else{
                        IdentificationModel questionModel = new IdentificationModel(quest, ans, IDENTIFICATION, lessonId);
                        repository.saveIdentificationQuestion(questionModel);
                        onBackPressed();
                    }
                }
            });
        }

    }


}