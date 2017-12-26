package com.android.example.oca_808;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.android.example.oca_808.fragment.ScoreFragment;
import com.android.example.oca_808.fragment.TestReviewFragment;

public class TestReviewActivity extends AppCompatActivity implements TestReviewFragment.OnFragmentInteractionListener,
        ScoreFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_review);

        getSupportFragmentManager().beginTransaction().add(R.id.review_frag_container, new TestReviewFragment()).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.test_review_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        getSupportFragmentManager().beginTransaction().replace(R.id.review_frag_container, new ScoreFragment()).commit();
        return super.onOptionsItemSelected(item);
    }
}
