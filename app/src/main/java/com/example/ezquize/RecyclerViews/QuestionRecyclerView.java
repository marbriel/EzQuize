package com.example.ezquize.RecyclerViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ezquize.R;
import com.example.ezquize.models.Question;

import java.util.List;

public class QuestionRecyclerView extends RecyclerView.Adapter<QuestionRecyclerView.QuestionViewHolder> {

    private List<Question> questions;
    private Context context;
    private QuestionCardOnClickListener questionCardOnClickListener;
    private QuestionCardOnLongClickListener questionCardOnLongClickListener;


    public QuestionRecyclerView(List<Question> questions, Context context,  QuestionCardOnLongClickListener questionCardOnLongClickListener) {
        this.questions = questions;
        this.context = context;
        this.questionCardOnLongClickListener = questionCardOnLongClickListener;
    }

    @NonNull
    @Override
    public QuestionRecyclerView.QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.question_card, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionRecyclerView.QuestionViewHolder holder, int position) {
        if(questions.size() > 0 && questions != null){
            holder.cardQuestion.setText(String.format(questions.get(position).getQuestion()));
            holder.cardAnswer.setText(String.format("Answer\n%s",questions.get(position).getAnswer().toUpperCase()));
        }

    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public class QuestionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        private TextView cardQuestion, cardAnswer;
        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            cardQuestion = itemView.findViewById(R.id.card_question);
            cardAnswer = itemView.findViewById(R.id.card_answer);
            itemView.setOnLongClickListener(this::onLongClick);

        }

        @Override
        public void onClick(View view) {
            questionCardOnClickListener.questionCardClick(view, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            questionCardOnLongClickListener.questionCardLongClick(view, getAdapterPosition());
            return true;
        }
    }



    public interface QuestionCardOnClickListener{
        void questionCardClick(View view, int position);
    }

    public interface QuestionCardOnLongClickListener{
        boolean questionCardLongClick(View view, int position);
    }
}
