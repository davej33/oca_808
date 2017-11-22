package com.android.example.oca_808.fragment;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.example.oca_808.R;
import com.android.example.oca_808.view_model.QuestionsViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AnswerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AnswerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnswerFragment extends Fragment {

    private static final String LOG_TAG = AnswerFragment.class.getSimpleName();
    private RadioButton radio_a;
    private RadioButton radio_b;
    private RadioButton radio_c;
    private RadioButton radio_d;
    private RadioButton radio_e;
    private RadioButton radio_f;
    private RadioGroup radioGroup;
    private static QuestionsViewModel mViewModel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AnswerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AnswerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AnswerFragment newInstance(String param1, String param2) {
        AnswerFragment fragment = new AnswerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(mViewModel == null) {
            mViewModel = new QuestionsViewModel(getContext());
            Log.w(LOG_TAG, "viewModel was null");
        }
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_answer, container, false);

        setViewValues(view);

        return view;
    }


    private void setViewValues(View view) {
        radioGroup = view.findViewById(R.id.radio_group);
        radio_a = view.findViewById(R.id.radioButton_a);
        radio_b = view.findViewById(R.id.radioButton_b);
        radio_c = view.findViewById(R.id.radioButton_c);
        radio_d = view.findViewById(R.id.radioButton_d);
        radio_e = view.findViewById(R.id.radioButton_e);
        radio_f = view.findViewById(R.id.radioButton_f);

        radio_a.setText(mViewModel.getCurrentQuestion().getA());
        radio_b.setText(mViewModel.getCurrentQuestion().getB());
        radio_c.setText(mViewModel.getCurrentQuestion().getC());
        radio_d.setText(mViewModel.getCurrentQuestion().getD());
        radio_e.setText(mViewModel.getCurrentQuestion().getE());
        radio_f.setText(mViewModel.getCurrentQuestion().getF());
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
