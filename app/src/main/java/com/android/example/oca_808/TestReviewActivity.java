package com.android.example.oca_808;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.example.oca_808.fragment.TestReviewFragment;

public class TestReviewActivity extends AppCompatActivity implements TestReviewFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_review);

        getSupportFragmentManager().beginTransaction().add(R.id.review_frag_container, new TestReviewFragment()).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
