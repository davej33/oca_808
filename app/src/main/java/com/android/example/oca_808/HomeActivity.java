package com.android.example.oca_808;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.example.oca_808.adapter.TestHistoryAdapter;
import com.android.example.oca_808.db.AppDatabase;
import com.android.example.oca_808.helper.TestGenerator;
import com.android.example.oca_808.view_model.QuestionViewModelFactory;
import com.android.example.oca_808.view_model.QuestionsViewModel;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG_TAG = HomeActivity.class.getSimpleName();
    private AppDatabase mDb;
    private PopupWindow mPopUpWindow;
    private View mPopUpView;
    private Context mContext;
    private ConstraintLayout mMainLayout;
    private LayoutInflater mLayoutInflater;
    private TestHistoryAdapter mTestHistoryAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // instantiate objects
        mDb = AppDatabase.getDb(this);
        mMainLayout = findViewById(R.id.home_activity);
        mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        mContext = this;

        setupSharedPref();

        QuestionsViewModel qvm = ViewModelProviders.of(this, new QuestionViewModelFactory(this.getApplication())).get(QuestionsViewModel.class);
        qvm.setQVM(qvm);

        TestGenerator.addQs(mContext); // TODO: only run once
        ImageButton testButton = findViewById(R.id.test_button);
        testButton.setOnClickListener(this);
    }

    private void setupSharedPref() {
        SharedPreferences shPref = PreferenceManager.getDefaultSharedPreferences(this);
        int checkSP = shPref.getInt(getResources().getString(R.string.sp_test_num_key), -99);
        if(checkSP < 0){
            SharedPreferences.Editor editor = shPref.edit();
            editor.putInt(getResources().getString(R.string.sp_test_num_key), 0);
            editor.apply();
        }

    }

    @Override
    public void onClick(View v) {
//        Log.w(LOG_TAG, "view id: " + v.getId());
        switch (v.getId()) {
            case R.id.test_button:
                inflateTestPopUp(v);
                mTestHistoryAdapter = new TestHistoryAdapter(this, 1);
                break;
            case R.id.new_test_tv:
                TestGenerator.createTestSim(this, 1);
                startActivity(new Intent(getApplicationContext(), QuestionsActivity.class));
//                mPopUpWindow.dismiss();

        }
    }

    private void inflateTestPopUp(View v) {

        // inflate layout
        mPopUpView = mLayoutInflater.inflate(R.layout.popup_test, (ViewGroup) v.getRootView(), false);
        Handler mainHandler = new Handler(getApplicationContext().getMainLooper());
        Runnable r = new Runnable() {
            @Override
            public void run() {
                // set recycler view
                RecyclerView mRecyclerView = mPopUpView.findViewById(R.id.rv_incomplete_tests);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                mRecyclerView.setLayoutManager(layoutManager);
                mRecyclerView.setAdapter(mTestHistoryAdapter);
            }
        };
        mainHandler.post(r);


        // Initialize new instance of popup window
        mPopUpWindow = new PopupWindow(
                mPopUpView,
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                true
        );

        // Set an elevation value for popup window
        // Call requires API level 21
        if (Build.VERSION.SDK_INT >= 21) {
            mPopUpWindow.setElevation(5.0f);
        }

        // show the popup
        mPopUpWindow.showAtLocation(mMainLayout, Gravity.CENTER, 0, 0);

        // dim popup background
        dimBehind(mPopUpWindow);

        // set onClickListener
        TextView newTest = mPopUpView.findViewById(R.id.new_test_tv);
        newTest.setOnClickListener(this);


    }

    public static void dimBehind(PopupWindow popupWindow) {
        View container = popupWindow.getContentView().getRootView();
        Context context = popupWindow.getContentView().getContext();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        p.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        p.dimAmount = 0.3f;
        wm.updateViewLayout(container, p);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(mPopUpWindow != null) mPopUpWindow.dismiss();
    }
}
