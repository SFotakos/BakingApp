package com.sfotakos.foodsteps.recipedetails;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sfotakos.foodsteps.general.DividerItemDecoration;
import com.sfotakos.foodsteps.R;
import com.sfotakos.foodsteps.general.Recipe;
import com.sfotakos.foodsteps.general.Step;
import com.sfotakos.foodsteps.databinding.FragmentRecipeDetailsBinding;
import com.sfotakos.foodsteps.recipestep.RecipeStepActivity;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class RecipeDetailsFragment extends Fragment implements StepsAdapter.IStepsAdapter {
    private static final String RECIPE_PARAM = "RECIPE_PARAM";

    private Recipe recipeExtra;
    private StepsAdapter.IStepsAdapter listener;

    public RecipeDetailsFragment() {
        // Required empty public constructor
    }

    public static RecipeDetailsFragment newInstance(Recipe recipe) {
        RecipeDetailsFragment fragment = new RecipeDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(RECIPE_PARAM, recipe);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recipeExtra = getArguments().getParcelable(RECIPE_PARAM);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView =
                inflater.inflate(R.layout.fragment_recipe_details, container, false);

        FragmentRecipeDetailsBinding mBinding = DataBindingUtil.bind(fragmentView);
        if (mBinding == null) return null;

        if (recipeExtra != null) {
            LinearLayoutManager ingredientsLayoutManager = new LinearLayoutManager(getContext(),
                    LinearLayoutManager.VERTICAL, false);

            RecyclerView rvRecipeIngredients = mBinding.included.rvRecipeIngredients;
            rvRecipeIngredients.setLayoutManager(ingredientsLayoutManager);
            rvRecipeIngredients.setAdapter(new IngredientsAdapter(recipeExtra.getIngredients()));

            LinearLayoutManager stepsLayoutManager = new LinearLayoutManager(getContext(),
                    LinearLayoutManager.VERTICAL, false);

            RecyclerView rvRecipeSteps = mBinding.included.rvRecipeSteps;
            rvRecipeSteps.setLayoutManager(stepsLayoutManager);
            rvRecipeSteps.addItemDecoration(new DividerItemDecoration(getContext()));
            rvRecipeSteps.setAdapter(new StepsAdapter(recipeExtra.getSteps(), this));
        }

        return fragmentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof StepsAdapter.IStepsAdapter) {
            listener = (StepsAdapter.IStepsAdapter) context;
        } else {
            throw new InvalidParameterException("Activity must implement IStepBundle interface");
        }
    }

    @Override
    public void onClick(ArrayList<Step> steps, int currentStep) {
        listener.onClick(steps, currentStep);
    }

}
