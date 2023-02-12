package com.example.ezquize.RecyclerViews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ezquize.R;
import com.example.ezquize.listeners.SubjectAddListener;
import com.example.ezquize.models.Subject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class AddSubjectRecyclerView extends RecyclerView.Adapter<AddSubjectRecyclerView.AddSubjectViewHolder> implements Filterable {
    private Context context;
    private List<Subject> subjects = new ArrayList<>();
    private List<Subject> filteredSubjects = new ArrayList<>();
    private SubjectAddListener subjectOnClickListener;

    public AddSubjectRecyclerView(Context context, List<Subject> subjects, SubjectAddListener subjectListener) {
        this.context = context;
        this.subjects = subjects;
        this.filteredSubjects = subjects;
        this.subjectOnClickListener = subjectListener;
    }

    @NonNull
    @Override
    public AddSubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.subject_information, parent, false);
        return new AddSubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddSubjectViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if(!subjects.isEmpty() && subjects.size()>0){
            holder.subjectName.setText(subjects.get(position).getName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    subjectOnClickListener.subjectAddListener(subjects.get(position));
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                if(charSequence == null || charSequence.length() == 0){
                    filterResults.values = filteredSubjects;
                    filterResults.count = filteredSubjects.size();
                }else{
                    String character = charSequence.toString().toLowerCase();
                    List<Subject> subjectsModelFiltered = new ArrayList<>();
                    for(Subject subject : filteredSubjects){
                        if(subject.getName().toLowerCase().contains(character)){
                            subjectsModelFiltered.add(subject);
                        }
                    }
                    filterResults.values = subjectsModelFiltered;
                    filterResults.count = subjectsModelFiltered.size();
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                subjects = (List<Subject>) filterResults.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }


    public class AddSubjectViewHolder extends RecyclerView.ViewHolder{

        private TextView subjectName;

        public AddSubjectViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectName = itemView.findViewById(R.id.subject_name_add);
        }


    }

}
