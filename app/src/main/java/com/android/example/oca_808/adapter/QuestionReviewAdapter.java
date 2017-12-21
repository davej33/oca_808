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
    private static QuestionsViewModel mViewModel;

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
        // get question number
        int qNum = holder.getAdapterPosition() + 1;
        String qNumString = "Question " + qNum;

        // get marked state and answered state
        String marked = mViewModel.getMarkedState(qNum);
        String answer = mViewModel.getAnswerSubmitted(qNum);

        // determine values for states
        boolean isAnswered = true;
        if(answer == null || answer.equals("")){
            isAnswered = false;
        }

        boolean isMarked = false;
        if(marked.equals("1")){
            isMarked = true;
        }

        // set states
        holder.questionNum.setText(qNumString);
        holder.answered.setChecked(isAnswered);
        holder.marked.setChecked(isMarked);
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
