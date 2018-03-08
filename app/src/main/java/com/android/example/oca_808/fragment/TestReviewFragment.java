package com.android.example.oca_808.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.example.oca_808.R;
import com.android.example.oca_808.adapter.QuestionReviewAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TestReviewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TestReviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TestReviewFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private QuestionReviewAdapter mAdapter;


    private OnFragmentInteractionListener mListener;

    public TestReviewFragment() {
        // Required empty public constructor
    }


    public static TestReviewFragment newInstance() {
        return new TestReviewFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_test_review, container, false);

        // setup adapter
        mRecyclerView = view.findViewById(R.id.test_review_rv);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mAdapter = new QuestionReviewAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
