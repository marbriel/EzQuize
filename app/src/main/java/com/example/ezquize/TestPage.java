package com.example.ezquize;

import static com.example.ezquize.models.TypeOfQuestion.IDENTIFICATION;
import static com.example.ezquize.models.TypeOfQuestion.MULTIPLE_CHOICE;
import static com.example.ezquize.models.TypeOfQuestion.TRUE_OR_FALSE;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ezquize.databasemanagement.Repository;
import com.example.ezquize.models.IdentificationModel;
import com.example.ezquize.models.MultipleChoiceModel;
import com.example.ezquize.models.Question;
import com.example.ezquize.models.TrueOrFalseModel;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;


public class TestPage extends AppCompatActivity{

    private FrameLayout frameLayout;
    private TextView score, currentQuestionNumber;
    private Button button;
    private ItemViewModel answerKey;
    private ItemViewModel multipleChoiceAnswerKey;
    private TextView questionCard;
    private String identificationKey, bipolarKey, multipleKey;
    private Chronometer timer;
    private long timeOffset;
    private boolean isTimerRunning = false;
    private List<Question> questions = new ArrayList<>();
    private int currentQuestion = 0;
    private ProgressBar progressBar;
    private int progress;
    private int totalPoints = 0;
    private MaterialCardView cardQuestionHolder;
    private MediaPlayer correctAnswerSounds, wrongAnswerSound, quizFinishSound;
    private Repository repository;
    private int lessonId;
    private float seconds;









    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_page);

        getSupportActionBar().hide();


        frameLayout = findViewById(R.id.choices_area);
        updateChoicesArea(new TrueOrFalseFragment());
        button = findViewById(R.id.check_for_answer);
        answerKey = new ViewModelProvider(this).get(ItemViewModel.class);
        multipleChoiceAnswerKey = new ViewModelProvider(this).get(ItemViewModel.class);
        questionCard = findViewById(R.id.question_show_card);
        timer = findViewById(R.id.timerClock);
        score = findViewById(R.id.total_points);
        score.setText(String.valueOf(totalPoints));
        currentQuestionNumber = findViewById(R.id.current_question_number);
        cardQuestionHolder = findViewById(R.id.card_question_holder);
        repository = new Repository(this);
        Bundle bundle = getIntent().getExtras();
        lessonId = bundle.getInt("LESSON_ID");

        //setting up sounds
        correctAnswerSounds = MediaPlayer.create(this, R.raw.correct_answer);
        wrongAnswerSound = MediaPlayer.create(this, R.raw.wrong_answer);
        quizFinishSound = MediaPlayer.create(this, R.raw.quiz_done);


        this.timeOffset = 0;
        startTimer();



        //set up progress bar
        progressBar = findViewById(R.id.progress_question_indicator);



        // setting initial question
        setQuestions();
        int questionsSize = questions.size();
        this.progress = (int) ((1.0/questionsSize)*100);
        progressBar.setProgress(this.progress);



        // Model View - View Model (connecting to fragment and sending back the data to Test Page of main activity)
        answerKey.getAnswer().observe(this, item ->{
            this.identificationKey = item;
        });

        multipleChoiceAnswerKey.getAnswerMultipleChoiceSelected().observe(this, item->{
            this.multipleKey = item;
        });

        answerKey.getChosenAnswerBiPolar().observe(this, item ->{
            this.bipolarKey = item;
        });


        //update score


        //initial current number showing
        currentQuestionNumber.setText(String.format("Question No. %d", currentQuestion+1));






        

        //initial question
        try {
            checkWhatChoicesToShow();
            updateQuestionToShow();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }



    private void setQuestions(){
        List<Question> shuffledQuestions = repository.getAllQuestions(lessonId);
        Collections.shuffle(shuffledQuestions);
        this.questions.addAll(shuffledQuestions);
    }



    private void checkWhatChoicesToShow() throws InterruptedException {
        switch(questions.get(currentQuestion).getTypeOfQuestion()){
            case IDENTIFICATION:
                updateChoicesArea(new IdentificationFragment());
                button.setOnClickListener(view -> {
                    if(identificationKey != null){
                        validateIdentificationAnswer(identificationKey, questions.get(currentQuestion).getAnswer());
                        try {
                            updateQuestion();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else{
                        Toast.makeText(this, "Please enter an answer", Toast.LENGTH_SHORT).show();
                    }
                    
                });
                break;
            case TRUE_OR_FALSE:
                updateChoicesArea(new TrueOrFalseFragment());
                button.setOnClickListener(view -> {
                    if(bipolarKey != null){
                        validateTrueOrFalseAnswer(bipolarKey, questions.get(currentQuestion).getAnswer());
                        try {
                            updateQuestion();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else{
                        Toast.makeText(this, "Please choose an answer", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case MULTIPLE_CHOICE:
                List<String> choices = repository.getChoicesOfCurrentQuestion(questions.get(currentQuestion).getId());
                updateChoicesAreaForMultipleChoice(new MultipleChoiceFragmentResource(), choices);
                button.setOnClickListener(view -> {
                    if(multipleKey != null){
                        validateMultipleChoiceAnswer(multipleKey, questions.get(currentQuestion).getAnswer());
                        try {
                            updateQuestion();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else{
                        Toast.makeText(this, "Please choose an answer", Toast.LENGTH_SHORT).show();
                    }
                });
                break;

        }
    }

    private List<String> extractChoices(HashMap<String, Boolean> choices) {
        List<String> extractedChoices = new ArrayList<>();
        for(Map.Entry<String, Boolean> choice: choices.entrySet()){
            extractedChoices.add(choice.getKey());
        }
        return extractedChoices;
    }


    private void updateChoicesArea(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(this.frameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }

    private void updateChoicesAreaForMultipleChoice(Fragment fragment, List<String> choices){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("CHOICES", (ArrayList<String>) choices);
        fragment.setArguments(bundle);
        fragmentTransaction.replace(this.frameLayout.getId(), fragment);
        fragmentTransaction.commit();

    }

    private void updateQuestion() throws InterruptedException {
        this.currentQuestion++;

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(currentQuestion >= questions.size()){
                    long time =  (SystemClock.elapsedRealtime() - timer.getBase());
                    long hours = (time /3600000);
                    long minutes = (time - hours*3600000)/60000;
                    seconds= (time - hours*3600000- minutes*60000)/1000 ;

                    double averagePoints = ((double) totalPoints / (double) questions.size()) * 100;
                    Intent intent = new Intent(TestPage.this,ScorePage.class);
                    intent.putExtra("LESSON_ID", lessonId);
                    intent.putExtra("SCORE", totalPoints);
                    intent.putExtra("OVER", questions.size());
                    intent.putExtra("TIME", seconds);
                    repository.saveAttempt(String.valueOf((int)averagePoints), lessonId);
                    stopTimer();
                    startActivity(intent);
                    finish();
                }else{
                    try {
                        checkWhatChoicesToShow();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    updateQuestionToShow();
                    updateCurrentNumberShowing();
                    updateProgressBar();
                    resetCardAnimation();

                }
            }
        }, 2000);

    }

    private void updateQuestionToShow(){
        questionCard.setText(questions.get(currentQuestion).getQuestion());
    }

    private void dialogExitBox(){
        new AlertDialog.Builder(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage("Your progress will be deleted once you exit the game.\n\n" +
                        "Do you really want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        stopTimer();
                        TestPage.this.finish();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        startTimer();
                    }
                }).create();
        dialog.show();

    }

    private void startTimer(){
       if(!isTimerRunning){
           timer.setBase(SystemClock.elapsedRealtime() - timeOffset);
           timer.start();
           isTimerRunning = true;
       }
    }

    private void pauseTimer(){
        if(isTimerRunning){
            timer.stop();
            timeOffset = SystemClock.elapsedRealtime() - timer.getBase();
            isTimerRunning = false;
        }
    }

    private void stopTimer(){
        timer.setBase(SystemClock.elapsedRealtime());
        timeOffset = 0;
    }

    private void updateProgressBar(){
        int newProgress = progressBar.getProgress() + progress;
        progressBar.setProgress(newProgress);
    }

    private void updateScore(){
        this.totalPoints += 1;
        score.setText(String.valueOf(this.totalPoints));
    }

    private void updateCurrentNumberShowing(){
        currentQuestionNumber.setText(String.format("Question No. %d", currentQuestion+1));
    }


    //card animations

    private void cardAnimationForCorrectAnswer(){
        cardQuestionHolder.setStrokeColor(Color.parseColor("#FFFFFF"));
        cardQuestionHolder.setBackgroundColor(Color.parseColor("#5CB85C"));
        correctAnswerSounds.start();
        button.setEnabled(false);

    }

    private void cardAnimationForWrongAnswer(){
        cardQuestionHolder.setStrokeColor(Color.parseColor("#FFFFFF"));
        cardQuestionHolder.setBackgroundColor(Color.parseColor("#D9534F"));
        questionCard.setText(questions.get(currentQuestion).getAnswer().toUpperCase());
        questionCard.setTextSize(20f);
        wrongAnswerSound.start();
        button.setEnabled(false);
    }

    private void resetCardAnimation(){
        cardQuestionHolder.setStrokeColor(Color.parseColor("#FFFFFF"));
        cardQuestionHolder.setBackgroundColor(Color.parseColor("#474747"));
        questionCard.setTextSize(14f);
        button.setEnabled(true);

    }


    //validate answers

    private void validateMultipleChoiceAnswer(String answer, String choices) {

        if (answer.equalsIgnoreCase(choices)) {
            updateScore();
            cardAnimationForCorrectAnswer();
        }else{
            cardAnimationForWrongAnswer();
        }

    }

    private void validateIdentificationAnswer(String answer, String key){
        if(key.equalsIgnoreCase(answer)){
            updateScore();
            cardAnimationForCorrectAnswer();
        }else{
            cardAnimationForWrongAnswer();
        }
    }

    private void validateTrueOrFalseAnswer(String answer, String key){
        if(key.equalsIgnoreCase(answer)){
            updateScore();
            cardAnimationForCorrectAnswer();
        }else{
            cardAnimationForWrongAnswer();
        }
    }






    
    
    // This function alert the user once they pressed back!!

    @Override
    public void onBackPressed() {
        pauseTimer();
        dialogExitBox();


        
    }
}