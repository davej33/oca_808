package com.android.example.oca_808.adapter;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.example.oca_808.QuestionsActivity;
import com.android.example.oca_808.R;
import com.android.example.oca_808.db.AppDatabase;
import com.android.example.oca_808.db.entity.TestEntity;
import com.android.example.oca_808.view_model.QuestionViewModelFactory;
import com.android.example.oca_808.view_model.QuestionsViewModel;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by charlotte on 12/7/17.
 */

public class TestHistoryAdapter extends RecyclerView.Adapter<TestHistoryAdapter.RecViewHolder> {
    private Context mContext;
    private static AppDatabase mDb;
    private List<TestEntity> mTestList;

    public TestHistoryAdapter(Context context, int testType) {
        mContext = context;

        // instantiate db
        if (mDb == null) mDb = AppDatabase.getDb(context);

        // fetch incomplete tests matching test type
        mTestList = mDb.testsDao().fetchIncompleteTests(testType);
        Log.w("adapter", "Adapter ---- " + mTestList.size());
        swapList(mTestList);
    }

    private void swapList(List<TestEntity> testList) {
        mTestList = testList;
        notifyDataSetChanged();
    }

    @Override
    public RecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.incomplete_test_item_view, parent, false);
        return new RecViewHolder(view);


    }

    @Override
    public void onBindViewHolder(final RecViewHolder holder, int position) {

        final TestEntity currentTest = mTestList.get(position);

        holder.title.setText(currentTest.title);
        holder.date.setText(parseDate(currentTest.endDateTime));
        holder.progressBar.setProgress(currentTest.progress);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuestionsViewModel.getQVM().getTest(currentTest._id);
                mContext.startActivity(new Intent(mContext, QuestionsActivity.class));
            }
        });
    }

    public String parseDate(long endDateTime) {
        if (Build.VERSION.SDK_INT > 25) {
            LocalDateTime date =
                    LocalDateTime.ofInstant(Instant.ofEpochMilli(endDateTime), ZoneId.systemDefault());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.YY HH:mm");
            return date.format(formatter);
        }
        return "Add date conversion for < 26";
    }

    @Override
    public int getItemCount() {
        Log.w("adapter", "get count: " + mTestList.size());
        return mTestList.size();
    }

    class RecViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView date;
        private ProgressBar progressBar;

        public RecViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.test_title_tv);
            date = itemView.findViewById(R.id.test_last_worked_datetime);
            progressBar = itemView.findViewById(R.id.test_progress);
        }
    }
}
