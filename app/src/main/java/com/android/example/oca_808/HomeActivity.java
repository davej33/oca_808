package com.android.example.oca_808;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.example.oca_808.db.AppDatabase;
import com.android.example.oca_808.db.entity.QuestionEntity;
import com.android.example.oca_808.db.entity.TestEntity;
import com.android.example.oca_808.helper.TestGenerator;
import com.android.example.oca_808.view_model.QuestionsViewModel;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG_TAG = HomeActivity.class.getSimpleName();
    private AppDatabase mDb;
    private PopupWindow mPopUpWindow;
    private View mPopUpView;
    private Context mContext;
    private ConstraintLayout mMainLayout;
    private LayoutInflater mLayoutInflater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // instantiate objects
        mDb = AppDatabase.getDb(this);
        mMainLayout = findViewById(R.id.home_activity);
        mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        mContext = this;


        TestGenerator.addQs(mContext); // TODO: only run once
        ImageButton testButton = findViewById(R.id.test_button);
        testButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.w(LOG_TAG, "view id: " + v.getId());
        switch (v.getId()) {
            case R.id.test_button:
                inflateTestPopUp(v);
                break;
            case R.id.new_test_tv:
                TestGenerator.createTestSim();
                new QuestionsViewModel(getApplicationContext());
                startActivity(new Intent(getApplicationContext(), QuestionsActivity.class));

        }
    }

    private void inflateTestPopUp(View v) {

        // inflate layout
        mPopUpView = mLayoutInflater.inflate(R.layout.popup_test, (ViewGroup) v.getRootView(), false);

        // Initialize a new instance of popup window
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
}
