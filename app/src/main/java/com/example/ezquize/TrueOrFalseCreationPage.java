package com.example.ezquize;

import static com.example.ezquize.models.TypeOfQuestion.TRUE_OR_FALSE;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.ezquize.databasemanagement.Repository;
import com.example.ezquize.models.Question;
import com.google.android.material.textfield.TextInputEditText;

public class TrueOrFalseCreationPage extends AppCompatActivity {

    private TextInputEditText question;
    private Repository repository;
    private Button saveButton;
    private RadioGroup choices;
    private int belongsToLesson, isUpdate, questionId;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_true_or_false_creation_page);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("True or False");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#373B3E")));
        question = findViewById(R.id.question_bipolar);
        choices = findViewById(R.id.bipolar_choices);
        saveButton = findViewById(R.id.bipolar_button);
        repository = new Repository(this);
        Bundle bundle = getIntent().getExtras();
        belongsToLesson = bundle.getInt("BELONGS_TO");
        isUpdate = bundle.getInt("UPDATE");
        if (isUpdate == 26) {
            saveButton.setText("UPDATE");
            questionId = bundle.getInt("QUESTION_ID");
            Question questionModel = repository.getQuestion(questionId);
            question.setText(questionModel.getQuestion());
            if(questionModel.getAnswer().equalsIgnoreCase("TRUE")){
                choices.check(R.id.true_rd_bt);
            }else{
                choices.check(R.id.false_rd_bt);
            }
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(question.getText().toString().isEmpty()){
                        Toast.makeText(TrueOrFalseCreationPage.this, "Please enter a question.", Toast.LENGTH_SHORT).show();
                    }else if(choices.getCheckedRadioButtonId() == -1){
                        Toast.makeText(TrueOrFalseCreationPage.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                    }else{
                        Question questionModel = new Question();
                        questionModel.setId(questionId);
                        questionModel.setQuestion(question.getText().toString());
                        int key = choices.getCheckedRadioButtonId();
                        RadioButton selected = findViewById(key);
                        String finalChoice = selected.getText().toString();
                        questionModel.setAnswer(finalChoice);
                        repository.updateQuestion(questionModel);
                        onBackPressed();
                    }
                }
            });
        }else{
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String quest = String.valueOf(question.getText());

                    if(quest.isEmpty()){
                        Toast.makeText(TrueOrFalseCreationPage.this, "Please enter a question.", Toast.LENGTH_SHORT).show();
                    }else if(choices.getCheckedRadioButtonId() == -1){
                        Toast.makeText(TrueOrFalseCreationPage.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                    }else{
                        int ansId =  choices.getCheckedRadioButtonId();
                        RadioButton radioButton = findViewById(ansId);
                        String answer = radioButton.getText().toString();
                        Question questionModel = new Question(quest, answer, TRUE_OR_FALSE, belongsToLesson);
                        repository.saveIdentificationQuestion(questionModel);
                        onBackPressed();
                    }

                }
            });
        }






    }
}