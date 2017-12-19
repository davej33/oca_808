package com.android.example.oca_808.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.example.oca_808.R;
import com.android.example.oca_808.view_model.QuestionsViewModel;

/**
 * Created by charlotte on 12/19/17.
 */

public class QuestionReviewAdapter extends RecyclerView.Adapter<QuestionReviewAdapter.QuestionViewHolder>  {

    private Context mContext;
    private QuestionsViewModel mViewModel;

    public QuestionReviewAdapter(Context context){
        mContext = context;
        mViewModel = QuestionsViewModel.getQVM();
    }
    @Override
    public QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.test_question_review_item, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuestionViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mViewModel.getQuestionCount();
    }

    public class QuestionViewHolder extends RecyclerView.ViewHolder {

        TextView questionNum;
        ToggleButton marked;
        ToggleButton answered;

        public QuestionViewHolder(View itemView) {
            super(itemView);

            questionNum = itemView.findViewById(R.id.question_review_tv);
            marked = itemView.findViewById(R.id.q_review_marked_tv);
            answered = itemView.findViewById(R.id.q_review_answered_tv);
        }
    }
}
