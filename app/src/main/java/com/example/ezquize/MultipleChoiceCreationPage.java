package com.example.ezquize;

import static com.example.ezquize.models.TypeOfQuestion.MULTIPLE_CHOICE;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.ezquize.databasemanagement.Repository;
import com.example.ezquize.models.Choice;
import com.example.ezquize.models.Question;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class MultipleChoiceCreationPage extends AppCompatActivity {

    private TextInputEditText question;
    private EditText optionA, optionB, optionC;
    private RadioGroup answer;
    private Button button;
    private int lessonId, questionId, isUpdate;
    private Repository repository;
    List<Choice> choices = new ArrayList<>();

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_choice_creation_page);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Multiple Choice");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#373B3E")));
        question = findViewById(R.id.multiple_choice_question);
        optionA = findViewById(R.id.option_a);
        optionB = findViewById(R.id.option_b);
        optionC = findViewById(R.id.option_c);
        answer = findViewById(R.id.multiple_choices_buttons);
        button = findViewById(R.id.multiple_choices_button);
        Bundle bundle = getIntent().getExtras();
        lessonId = bundle.getInt("BELONGS_TO");
        isUpdate = bundle.getInt("UPDATE");
        repository = new Repository(this);
        if(isUpdate == 26){
            button.setText("UPDATE");
            questionId = bundle.getInt("QUESTION_ID");
            Question questionModel = repository.getQuestion(questionId);
            choices = repository.getChoices(questionId);
            question.setText(questionModel.getQuestion());
            optionA.setText(choices.get(0).getAnswer());
            optionB.setText(choices.get(1).getAnswer());
            optionC.setText(choices.get(2).getAnswer());
            if(optionA.getText().toString().equalsIgnoreCase(questionModel.getAnswer())){
                answer.check(R.id.optionA_rdt);
            }else if(optionB.getText().toString().equalsIgnoreCase(questionModel.getAnswer())){
                answer.check(R.id.optionB_rdt);
            }else{
                answer.check(R.id.optionC_rdt);
            }

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(question.getText().toString().isEmpty()){
                        Toast.makeText(MultipleChoiceCreationPage.this, "Please enter a question.", Toast.LENGTH_SHORT).show();
                    }else if(optionA.getText().toString().isEmpty() || optionB.getText().toString().isEmpty() || optionC.getText().toString().isEmpty()){
                        Toast.makeText(MultipleChoiceCreationPage.this, "Please enter the choices", Toast.LENGTH_SHORT).show();
                    }else if(answer.getCheckedRadioButtonId() == -1){
                        Toast.makeText(MultipleChoiceCreationPage.this, "Please choose an answer", Toast.LENGTH_SHORT).show();
                    }else{
                        int selectedChoice = answer.getCheckedRadioButtonId();
                        RadioButton selectedRadio = findViewById(selectedChoice);
                        String textAnswer = String.valueOf(selectedRadio.getText());
                        String textQuestion = String.valueOf(question.getText());
                        String a = String.valueOf(optionA.getText());
                        String b = String.valueOf(optionB.getText());
                        String c = String.valueOf(optionC.getText());
                        String[] choicesToCheck = new String[]{a,b,c};
                        String finalAnswer = keyAnswer(choicesToCheck, textAnswer);
                        Question questionToUpdate = new Question();
                        questionToUpdate.setId(questionId);
                        questionToUpdate.setQuestion(textQuestion);
                        questionToUpdate.setAnswer(finalAnswer);

                        repository.updateQuestion(questionToUpdate);
                        for (int i = 0; i < choicesToCheck.length; i++){
                            repository.updateChoices(choicesToCheck[i], choices.get(i).getId());
                        }
                        onBackPressed();
                    }

                }
            });
        }else{
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(question.getText().toString().isEmpty()){
                        Toast.makeText(MultipleChoiceCreationPage.this, "Please enter a question", Toast.LENGTH_SHORT).show();
                    }else if(optionA.getText().toString().isEmpty() || optionB.getText().toString().isEmpty() || optionC.getText().toString().isEmpty()){
                        Toast.makeText(MultipleChoiceCreationPage.this, "Please enter the choices", Toast.LENGTH_SHORT).show();
                    }else if(answer.getCheckedRadioButtonId() == -1){
                        Toast.makeText(MultipleChoiceCreationPage.this, "Please choose the answer", Toast.LENGTH_SHORT).show();
                    }else{
                        int selectedId = answer.getCheckedRadioButtonId();
                        RadioButton radioButton = findViewById(selectedId);
                        String textAnswer = String.valueOf(radioButton.getText());
                        String textQuestion = String.valueOf(question.getText());
                        String a = String.valueOf(optionA.getText());
                        String b = String.valueOf(optionB.getText());
                        String c = String.valueOf(optionC.getText());
                        String[] choices = new String[]{a,b,c};
                        String finalAnswer = keyAnswer(choices, textAnswer);
                        Question questionModel = new Question(textQuestion, finalAnswer, MULTIPLE_CHOICE, lessonId);

                        long id = repository.saveIdentificationQuestion(questionModel);
                        for (int i = 0; i < choices.length; i++){
                            repository.saveChoices(choices[i], id);
                        }
                        onBackPressed();
                    }

                }
            });
        }



    }

    private String keyAnswer(String[] choices, String option){
        String answer = "";
        if(option.equalsIgnoreCase("option a")){
            answer = choices[0];
        }else if(option.equalsIgnoreCase("option b")){
            answer = choices[1];
        }else{
            answer = choices[2];
        }
        return answer;
    }


}