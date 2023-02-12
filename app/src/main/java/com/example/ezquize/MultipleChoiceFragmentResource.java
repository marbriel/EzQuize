package com.example.ezquize;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MultipleChoiceFragmentResource#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MultipleChoiceFragmentResource extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // initialization of variables
    private ItemViewModel choice;
    private List<String> multipleChoices = new ArrayList<>();
    private RadioButton optionA, optionB, optionC;
    private RadioGroup groupOfChoices;

    public MultipleChoiceFragmentResource() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MultipleChoiceFragmentResource.
     */
    // TODO: Rename and change types and number of parameters
    public static MultipleChoiceFragmentResource newInstance(String param1, String param2) {
        MultipleChoiceFragmentResource fragment = new MultipleChoiceFragmentResource();
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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_multiple_choice_resource, container, false);
        this.multipleChoices = this.getArguments().getStringArrayList("CHOICES");
        Collections.shuffle(this.multipleChoices);


        optionA = view.getRootView().findViewById(R.id.option_a_choice);
        optionB = view.getRootView().findViewById(R.id.option_b_choice);
        optionC = view.getRootView().findViewById(R.id.option_c_choice);
        groupOfChoices = view.getRootView().findViewById(R.id.group_choice);
        choice = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);

        groupOfChoices.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = groupOfChoices.getCheckedRadioButtonId();
                RadioButton selectedButton = view.findViewById(id);
                String answer = selectedButton.getText().toString();
                choice.setChosenAnswerMultipleChoice(answer);
            }
        });



        optionA.setText(multipleChoices.get(0));
        optionB.setText(multipleChoices.get(1));
        optionC.setText(multipleChoices.get(2));
        return view;
    }

    public void changeBackgroundWhenCorrect(){
        optionA.setBackgroundColor(Color.GREEN);
    }




}