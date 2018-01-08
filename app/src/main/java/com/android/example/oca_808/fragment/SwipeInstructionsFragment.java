package com.android.example.oca_808.fragment;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.example.oca_808.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SwipeInstructionsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SwipeInstructionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SwipeInstructionsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public SwipeInstructionsFragment() {
        // Required empty public constructor
    }


    public static SwipeInstructionsFragment newInstance(String param1, String param2) {
        return new SwipeInstructionsFragment();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_swipe_instructions, container, false);

        ImageView robot = view.findViewById(R.id.swipe_instr_robot);
        ObjectAnimator animator = ObjectAnimator.ofFloat(robot, "y", 600);
        animator.setRepeatMode(animator.REVERSE);
        animator.setRepeatCount(1);
        animator.setDuration(2000);
        animator.start();

        return view;
    }

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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
