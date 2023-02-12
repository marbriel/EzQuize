package com.example.ezquize;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ezquize.databasemanagement.Repository;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class Tabulate extends AppCompatActivity {

    private Repository repository;
    private List<String> attempts;
    private int lessonId;
    private Bundle bundle;
    private BarChart barChart;
    private List<Integer> yAxis = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabulate);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFC947")));
        actionBar.setTitle("Graph");
        repository = new Repository(this);
        bundle = getIntent().getExtras();
        lessonId = bundle.getInt("LESSON_ID");
        attempts = repository.fetchAllAttempts(lessonId);
        convertStringToIntData();
        barChart = findViewById(R.id.result);
        initCharts();
    }


    private void initCharts(){
        List<BarEntry> entries = new ArrayList<>();
        for(int i = 0; i < attempts.size(); i++){
            entries.add(new BarEntry(i + 1 , this.yAxis.get(i)));
        }

        BarDataSet setData = new BarDataSet(entries, "Data Set");
        BarData data = new BarData(setData);
        barChart.setFitBars(true);
        barChart.setData(data);

        setData.setColors(ColorTemplate.JOYFUL_COLORS);
        setData.setValueTextColor(Color.BLACK);
        setData.setValueTextSize(16f);
        barChart.getDescription().setText("Scores");
        barChart.getDescription().setEnabled(true);
        barChart.animateY(3000);
        barChart.setMaxVisibleValueCount(100);


    }

    private void convertStringToIntData(){
        for (String attempt: attempts) {
            yAxis.add(Integer.parseInt(attempt));
        }

    }
}