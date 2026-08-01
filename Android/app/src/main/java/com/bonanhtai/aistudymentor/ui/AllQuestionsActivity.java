package com.bonanhtai.aistudymentor.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bonanhtai.aistudymentor.R;
import com.bonanhtai.aistudymentor.api.ApiCallback;
import com.bonanhtai.aistudymentor.model.QuestionHistory;
import com.bonanhtai.aistudymentor.repository.CallAPI;

import java.util.ArrayList;
import java.util.List;

public class AllQuestionsActivity extends AppCompatActivity {

    private RecyclerView rvAllQuestions;
    private ProgressBar progressBar;
    private CallAPI callAPI;
    private QuestionsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_questions);

        initViews();
        callAPI = new CallAPI(this);
        loadQuestions();
    }

    private void initViews() {
        rvAllQuestions = findViewById(R.id.rvAllQuestions);
        progressBar = findViewById(R.id.progressBar);
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        rvAllQuestions.setLayoutManager(new LinearLayoutManager(this));
        adapter = new QuestionsAdapter(new ArrayList<>());
        rvAllQuestions.setAdapter(adapter);
    }

    private void loadQuestions() {
        progressBar.setVisibility(View.VISIBLE);
        callAPI.getQuestionsAPI(new ApiCallback<List<QuestionHistory>>() {
            @Override
            public void onSuccess(List<QuestionHistory> data) {
                progressBar.setVisibility(View.GONE);
                adapter.updateData(data);
            }

            @Override
            public void onError(Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(AllQuestionsActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.ViewHolder> {
        private final List<QuestionHistory> questionList;

        public QuestionsAdapter(List<QuestionHistory> questionList) {
            this.questionList = questionList;
        }

        public void updateData(List<QuestionHistory> newList) {
            questionList.clear();
            questionList.addAll(newList);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recent_question, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            QuestionHistory item = questionList.get(position);
            
            holder.tvQuestionText.setText(item.getQuestionText());
            holder.tvSubjectBadge.setText(item.getSubject() != null ? item.getSubject() : "General");
            holder.tvPrimaryAnswer.setText(item.getPrimaryAnswer());
            
            if (item.getSimplifiedExplanation() != null && !item.getSimplifiedExplanation().isEmpty()) {
                holder.tvSimplifiedExplanation.setText(item.getSimplifiedExplanation());
                holder.tvSimplifiedExplanation.setVisibility(View.VISIBLE);
                holder.tvLabelExplain.setVisibility(View.VISIBLE);
            } else {
                holder.tvSimplifiedExplanation.setVisibility(View.GONE);
                holder.tvLabelExplain.setVisibility(View.GONE);
            }

            if (item.getAskedAt() != null) {
                holder.tvAskedAt.setText("Asked at: " + item.getAskedAt().toString());
            } else {
                holder.tvAskedAt.setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            return questionList.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvQuestionText, tvPrimaryAnswer, tvSimplifiedExplanation;
            TextView tvSubjectBadge, tvLabelExplain, tvAskedAt;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvQuestionText = itemView.findViewById(R.id.tvQuestionText);
                tvPrimaryAnswer = itemView.findViewById(R.id.tvPrimaryAnswer);
                tvSimplifiedExplanation = itemView.findViewById(R.id.tvSimplifiedExplanation);
                tvSubjectBadge = itemView.findViewById(R.id.tvSubjectBadge);
                tvLabelExplain = itemView.findViewById(R.id.tvLabelExplain);
                tvAskedAt = itemView.findViewById(R.id.tvAskedAt);
            }
        }
    }
}
