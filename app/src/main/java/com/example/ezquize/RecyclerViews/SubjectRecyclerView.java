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

public class SubjectRecyclerView extends RecyclerView.Adapter<SubjectRecyclerView.SubjectViewHolder> {
    private Context context;
    private List<Subject> subjects;
    private SubjectCardClick subjectCardOnClick;
    private SubjectCardLongClick subjectCardOnLongClick;

    public SubjectRecyclerView(Context context, List<Subject> subjects, SubjectCardClick subjectCardOnClick, SubjectCardLongClick subjectCardOnLongClick) {
        this.context = context;
        this.subjects = sortedSubjects(subjects);
        this.subjectCardOnClick = subjectCardOnClick;
        this.subjectCardOnLongClick = subjectCardOnLongClick;
    }

    public void refreshData(List<Subject> subjects){
        this.subjects =sortedSubjects(subjects);
        notifyDataSetChanged();
    }

    private List<Subject> sortedSubjects(List<Subject> unsorted){
        Collections.sort(unsorted,  (subject1, subject2) -> subject1.getName().compareTo(subject2.getName()));
        return unsorted;
    }



    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.subject_card, parent, false);
        return new SubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        if(!subjects.isEmpty() || subjects.size() == 0){
            holder.subjectName.setText(subjects.get(position).getName());
            holder.subjectImage.setImageResource(subjects.get(position).getImageSource());
        }


    }

    @Override
    public int getItemCount() {
        return this.subjects.size();
    }

    public class SubjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private ImageView subjectImage;
        private TextView subjectName;
        public SubjectViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectImage = itemView.findViewById(R.id.subjectImage);
            subjectName = itemView.findViewById(R.id.subjectName);
            itemView.setOnClickListener(this::onClick);
            itemView.setOnLongClickListener(this::onLongClick);
        }

        @Override
        public void onClick(View view) {
            subjectCardOnClick.subjectOnClick(view, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            subjectCardOnLongClick.subjectOnLongClick(view, getAdapterPosition());
            return true;
        }
    }
}
