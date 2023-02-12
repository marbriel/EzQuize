package com.example.ezquize;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ezquize.databasemanagement.Repository;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ScorePage extends AppCompatActivity {

    private int score, over, average, lessonId, wrong;
    private float time;
    private PieChart pieChartResult;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    private TextView scoreView, timerView;
    private String timeToShow, scoreToShow;
    private Button button;
    private Repository repository;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_page);
        getSupportActionBar().hide();


        //initialization

        Bundle bundle = getIntent().getExtras();
        score = bundle.getInt("SCORE");
        over = bundle.getInt("OVER");
        wrong = over - score;
        average = (int) (((double) score / (double) over) * 100);
        time = bundle.getFloat("TIME");
        lessonId = bundle.getInt("LESSON_ID");
        repository = new Repository(this);
        /*rightPercentage = Double.parseDouble(df.format((score/over)*100));
        wrongPercentage = Double.parseDouble(df.format((wrong/over)*100));
        */
        scoreView = findViewById(R.id.score_view);
        timerView = findViewById(R.id.time);
        button = findViewById(R.id.gobacktomain);
        setTimeToShow();
        timerView.setText(timeToShow);
        scoreView.setText(String.format("Score: %d/%d", score, over));



        //pie chart set up
        pieChartResult = findViewById(R.id.pie_chart_result);
        initPieChartData();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }



    private void initPieChartData(){
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(score, "Correct"));
        entries.add(new PieEntry(wrong, "Wrong"));

        PieDataSet dataSet = new PieDataSet(entries, "Entries");
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        dataSet.setValueTextColor(Color.parseColor("#373B3E"));
        dataSet.setValueTextSize(10f);


        PieData data = new PieData(dataSet);
        pieChartResult.setData(data);
        pieChartResult.getDescription().setEnabled(false);
        pieChartResult.setCenterText("Summary");
        pieChartResult.getLegend().setEnabled(false);
        pieChartResult.animate();

    }


    private void setTimeToShow(){
        if(time < 60){
            if(time<10){
                timeToShow = String.format("Time: 00:0%d", (int)time);
            }else{
                timeToShow = String.format("Time: 00:%d", (int)time);
            }
        }else{
            int minutes =(int) (time/60);
            int seconds = (int)(time % 60 );
            if(seconds < 10){
                timeToShow = String.format("Time: 00:0%d", seconds);
            }else{
                timeToShow = String.format("Time: %d:%d", minutes, seconds);
            }

        }
    }
}