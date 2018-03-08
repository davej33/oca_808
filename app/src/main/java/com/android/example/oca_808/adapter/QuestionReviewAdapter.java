package com.android.example.oca_808.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.example.oca_808.QuestionsActivity;
import com.android.example.oca_808.R;
import com.android.example.oca_808.view_model.QuestionsViewModel;

/**
 * Created by charlotte on 12/19/17.
 */

public class QuestionReviewAdapter extends RecyclerView.Adapter<QuestionReviewAdapter.QuestionViewHolder> {

    private Context mContext;
    private static QuestionsViewModel mViewModel;

    public QuestionReviewAdapter(Context context) {
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

        // get question number.
        final int qNumInt = holder.getAdapterPosition() + 1; // +1 accounts for null value at index 0
        String qNum;

        if (qNumInt < 10) {
            qNum = "0" + qNumInt;
        } else {
            qNum = String.valueOf(qNumInt);
        }
        String qNumString = "Q - " + qNum;

        // get marked state and answered state
        String marked = mViewModel.getMarkedState(qNumInt);
        String answer = mViewModel.getAnswerSubmitted(qNumInt);

        // determine values for states
        boolean isAnswered = true;
        if (answer == null || answer.equals("")) {
            isAnswered = false;
        }

        boolean isMarked = false;
        if (marked.equals("1")) {
            isMarked = true;
        }

        // set states
        holder.questionNum.setText(qNumString);
        holder.answered.setChecked(isAnswered);
        holder.marked.setChecked(isMarked);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            mViewModel.setmWhereWeAt(qNumInt);
            Intent intent = new Intent(mContext, QuestionsActivity.class);
            intent.putExtra("review", true);
            mContext.startActivity(intent);
            }
        });
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
