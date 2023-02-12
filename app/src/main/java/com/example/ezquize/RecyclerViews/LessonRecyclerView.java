package com.example.ezquize.RecyclerViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public class LessonRecyclerView extends RecyclerView.Adapter<LessonRecyclerView.LessonViewHolder> {

    private Context context;
    private List<Lessons> lessons;
    private LessonOnClickListener lessonOnClickListener;
    private PlayButtonListener playButtonListener;
    private LessonOnLongClick lessonOnLongClick;


    public LessonRecyclerView(Context context, List<Lessons> lessons, LessonOnClickListener lessonOnClickListener, PlayButtonListener playButton, LessonOnLongClick lessonOnLongClick) {
        this.context = context;
        this.lessons = lessons;
        this.lessonOnClickListener = lessonOnClickListener;
        this.playButtonListener = playButton;
        this.lessonOnLongClick = lessonOnLongClick;
    }

    @NonNull
    @Override
    public LessonRecyclerView.LessonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.lesson_card, parent, false);
        return new LessonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonRecyclerView.LessonViewHolder holder, int position) {
        if(!lessons.isEmpty() && lessons.size() > 0){
            holder.lessonName.setText(lessons.get(position).getLessonName());
            holder.lessonNumber.setText("Lesson No. "+lessons.get(position).getLessonNumber());
            holder.playButton.setOnClickListener((view )-> {
                playButtonListener.playButtonListener(view, position);
            });
        }

    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }



    public class LessonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView lessonNumber;
        private TextView lessonName;
        private ImageView playButton;
        public LessonViewHolder(@NonNull View itemView) {
            super(itemView);
            lessonName = itemView.findViewById(R.id.lesson_name);
            lessonNumber = itemView.findViewById(R.id.lesson_number);
            playButton = itemView.findViewById(R.id.play_btn);
            itemView.setOnLongClickListener(this::onLongClick);
            itemView.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View view) {
            lessonOnClickListener.lessonOnClick(view, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            lessonOnLongClick.lessonLongClick(view, getAdapterPosition());
            return true;
        }
    }
}
