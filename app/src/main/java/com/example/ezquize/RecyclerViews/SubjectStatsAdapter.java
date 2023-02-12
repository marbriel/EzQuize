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
import com.example.ezquize.listeners.SubjectCardClick;
import com.example.ezquize.listeners.SubjectCardLongClick;
import com.example.ezquize.models.Subject;

import java.util.Collections;
import java.util.List;

public class SubjectStatsAdapter extends RecyclerView.Adapter<SubjectStatsAdapter.SubjectStatViewHolder> {
    private Context context;
    private List<Subject> subjects;
    private SubjectCardClick subjectCardOnClick;


    public SubjectStatsAdapter(Context context, List<Subject> subjects, SubjectCardClick subjectCardOnClick) {
        this.context = context;
        this.subjects = subjects;
        this.subjectCardOnClick = subjectCardOnClick;
    }

    @NonNull
    @Override
    public SubjectStatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.card_stats_subject, parent, false);
        return new SubjectStatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectStatViewHolder holder, int position) {
        if(!subjects.isEmpty() || subjects.size() == 0){
            holder.subjectName.setText(subjects.get(position).getName());
        }
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }


    public class SubjectStatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView subjectName;
        public SubjectStatViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectName = itemView.findViewById(R.id.subject_name_stats);
            itemView.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View view) {
            subjectCardOnClick.subjectOnClick(view, getAdapterPosition());
        }
    }
}
