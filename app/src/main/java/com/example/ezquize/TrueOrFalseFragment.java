package com.example.ezquize;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrueOrFalseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrueOrFalseFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //initialize RadioButtons
    private RadioGroup radioGroup;
    private RadioButton trueChoice, falseChoice;

    //initialize Item View Model
    private ItemViewModel answerKey;

    public TrueOrFalseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TrueOrFalseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TrueOrFalseFragment newInstance(String param1, String param2) {
        TrueOrFalseFragment fragment = new TrueOrFalseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_true_or_false, container, false);
        radioGroup = view.findViewById(R.id.key_bipolar_group);
        trueChoice = view.findViewById(R.id.true_bipolar);
        falseChoice = view.findViewById(R.id.false_bipolar);
        answerKey = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = radioGroup.getCheckedRadioButtonId();
                RadioButton checkButton = view.findViewById(id);
                String answer = checkButton.getText().toString();
                answerKey.setChosenBiPolar(answer);
            }
        });




        return view;
    }
}