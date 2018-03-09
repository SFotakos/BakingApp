package com.sfotakos.foodsteps.recipestep;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sfotakos.foodsteps.R;
import com.sfotakos.foodsteps.Step;
import com.sfotakos.foodsteps.databinding.FragmentRecipeStepBinding;

public class RecipeStepFragment extends Fragment {
    private static final String STEP_PARAM = "STEP_PARAM";
    private static final String STEP_CURRENT_PARAM = "STEP_CURRENT_PARAM";
    private static final String STEP_COUNT_PARAM = "STEP_COUNT_PARAM";

    private FragmentRecipeStepBinding mBinding;

    private Step stepExtra;
    private int currentStep;
    private int stepCount;

    public RecipeStepFragment() {
        // Required empty public constructor
    }

    public static RecipeStepFragment newInstance(Step step, int currentStep, int stepCount) {
        RecipeStepFragment fragment = new RecipeStepFragment();
        Bundle args = new Bundle();
        args.putParcelable(STEP_PARAM, step);
        args.putInt(STEP_CURRENT_PARAM, currentStep);
        args.putInt(STEP_COUNT_PARAM, stepCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            stepExtra = getArguments().getParcelable(STEP_PARAM);
            currentStep = getArguments().getInt(STEP_CURRENT_PARAM);
            stepCount = getArguments().getInt(STEP_COUNT_PARAM);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView =
                inflater.inflate(R.layout.fragment_recipe_step, container, false);

        mBinding = DataBindingUtil.bind(fragmentView);

        if (stepExtra != null) {

            mBinding.tvStepFullDescription.setText(stepExtra.getDescription());

            String stepCountString = Integer.toString(currentStep+1) + "/" + stepCount;
            mBinding.tvCurrentStep.setText(stepCountString);
        }

        return fragmentView;
    }
}
