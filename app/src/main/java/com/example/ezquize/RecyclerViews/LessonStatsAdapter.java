package com.example.ezquize.RecyclerViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ezquize.R;
import com.example.ezquize.listeners.LessonOnClickListener;
import com.example.ezquize.listeners.LessonOnLongClick;
import com.example.ezquize.listeners.PlayButtonListener;
import com.example.ezquize.models.Lessons;

import org.w3c.dom.Text;

import java.util.List;

public class LessonStatsAdapter extends RecyclerView.Adapter<LessonStatsAdapter.LessonStatsViewHolder> {
    private Context context;
    private List<Lessons> lessons;
    private LessonOnClickListener lessonOnClickListener;

    public LessonStatsAdapter(Context context, List<Lessons> lessons, LessonOnClickListener lessonOnClickListener) {
        this.context = context;
        this.lessons = lessons;
        this.lessonOnClickListener = lessonOnClickListener;
    }

    @NonNull
    @Override


    public LessonStatsAdapter.LessonStatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.lesson_stats_card, parent, false);
        return new LessonStatsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonStatsAdapter.LessonStatsViewHolder holder, int position) {
        if(!lessons.isEmpty() || lessons.size() == 0){
            holder.lessonNumber.setText(String.format("Lesson No. %s", lessons.get(position).getLessonNumber()));
            holder.lessonTitle.setText(lessons.get(position).getLessonName());
        }
    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }

    public class LessonStatsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView lessonNumber;
        private TextView lessonTitle;
        public LessonStatsViewHolder(@NonNull View itemView) {
            super(itemView);
            lessonNumber = itemView.findViewById(R.id.lesson_number_stats);
            lessonTitle = itemView.findViewById(R.id.name_statistics);
            itemView.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View view) {
            lessonOnClickListener.lessonOnClick(view, getAdapterPosition());
        }
    }
}
