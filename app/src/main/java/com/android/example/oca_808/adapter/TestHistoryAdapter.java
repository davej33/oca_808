package com.android.example.oca_808.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.example.oca_808.R;
import com.android.example.oca_808.db.AppDatabase;
import com.android.example.oca_808.db.entity.TestEntity;

import java.util.List;

/**
 * Created by charlotte on 12/7/17.
 */

public class TestHistoryAdapter extends RecyclerView.Adapter<TestHistoryAdapter.RecViewHolder> {
    private Context mContext;
    private static AppDatabase mDb;
    private List<TestEntity> mTestList;

    public TestHistoryAdapter(Context context, int testType){
        mContext = context;

        // instantiate db
        if(mDb == null) mDb  = AppDatabase.getDb(context);

        // fetch incomplete tests matching test type
        mTestList = mDb.testsDao().fetchIncompleteTests(testType);
    }

    @Override
    public RecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(mContext).inflate(R.layout.incomplete_test_item_view, parent, false);
       RecViewHolder holder = new RecViewHolder(view);



       return holder;
    }

    @Override
    public void onBindViewHolder(RecViewHolder holder, int position) {
        TestEntity currentTest = mTestList.get(position);

//        holder.title.setText();
    }

    @Override
    public int getItemCount() {
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
