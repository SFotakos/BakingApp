package com.sfotakos.foodsteps.recipestep;

import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sfotakos.foodsteps.ActivityUtils;
import com.sfotakos.foodsteps.R;
import com.sfotakos.foodsteps.Step;
import com.sfotakos.foodsteps.databinding.FragmentRecipeStepBinding;

import java.util.ArrayList;

public class RecipeStepFragment extends Fragment {
    private static final String STEP_PARAM = "STEP_PARAM";
    private static final String STEP_CURRENT_PARAM = "STEP_CURRENT_PARAM";

    private FragmentRecipeStepBinding mBinding;

    private ArrayList<Step> stepsExtra;
    private int currentStep;

    public RecipeStepFragment() {
        // Required empty public constructor
    }

    public static RecipeStepFragment newInstance(ArrayList<Step> steps, int currentStep) {
        RecipeStepFragment fragment = new RecipeStepFragment();
        Bundle args = new Bundle();
        args.putSerializable(STEP_PARAM, steps);
        args.putInt(STEP_CURRENT_PARAM, currentStep);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            stepsExtra = (ArrayList<Step>) getArguments().getSerializable(STEP_PARAM);
            currentStep = getArguments().getInt(STEP_CURRENT_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView =
                inflater.inflate(R.layout.fragment_recipe_step, container, false);

        mBinding = DataBindingUtil.bind(fragmentView);

        if (stepsExtra != null) {
            Step step = stepsExtra.get(currentStep);

            mBinding.linearPrevStep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    previousStep();
                }
            });

            mBinding.linearNextStep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nextStep();
                }
            });
            prevNextEnable();

            loadStep(step);
        }

        return fragmentView;
    }

    private void loadStep(Step step){
        String videoURL = step.getVideoURL();
        if (videoURL == null || videoURL.isEmpty()) {
            mBinding.tvStepVideo.setVisibility(View.GONE);
        } else {
            mBinding.tvStepVideo.setVisibility(View.VISIBLE);
        }

        mBinding.tvStepFullDescription.setText(step.getDescription());

        String stepCountString = Integer.toString(currentStep) + "/" +
                Integer.toString(stepsExtra.size()-1);
        mBinding.tvCurrentStep.setText(stepCountString);
    }

    private void nextStep(){
        currentStep++;
        loadStep(stepsExtra.get(currentStep));
        prevNextEnable();
    }

    private void previousStep(){
        currentStep--;
        loadStep(stepsExtra.get(currentStep));
        prevNextEnable();
    }

    private void prevNextEnable(){
        if (currentStep == 0){
            ActivityUtils.setViewAndChildrenEnabled(mBinding.linearPrevStep, false);
            mBinding.ivPrevStep.setColorFilter(
                    ContextCompat.getColor(
                            getContext(), R.color.material_color_grey_300),
                    android.graphics.PorterDuff.Mode.SRC_IN);
        } else if (currentStep == stepsExtra.size()-1){
            ActivityUtils.setViewAndChildrenEnabled(mBinding.linearNextStep, false);
            mBinding.ivNextStep.setColorFilter(
                    ContextCompat.getColor(
                            getContext(), R.color.material_color_grey_300),
                    android.graphics.PorterDuff.Mode.SRC_IN);
        } else {
            ActivityUtils.setViewAndChildrenEnabled(mBinding.linearPrevStep, true);
            mBinding.ivPrevStep.setColorFilter(
                    ContextCompat.getColor(
                            getContext(), R.color.material_color_grey_600),
                    android.graphics.PorterDuff.Mode.SRC_IN);

            ActivityUtils.setViewAndChildrenEnabled(mBinding.linearNextStep, true);
            mBinding.ivNextStep.setColorFilter(
                    ContextCompat.getColor(
                            getContext(), R.color.material_color_grey_600),
                    android.graphics.PorterDuff.Mode.SRC_IN);

        }
    }

}
