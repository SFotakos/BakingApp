package com.sfotakos.foodsteps.recipes;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sfotakos.foodsteps.R;
import com.sfotakos.foodsteps.Recipe;
import com.sfotakos.foodsteps.databinding.FragmentRecipesBinding;
import com.sfotakos.foodsteps.recipesteps.RecipeDetailsActivity;

public class RecipesFragment extends Fragment implements RecipesAdapter.IRecipesAdapter {

    private FragmentRecipesBinding mBinding;

    public RecipesFragment() {
        // Required empty public constructor
    }

    public static RecipesFragment newInstance() {
        return new RecipesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView
                = inflater.inflate(R.layout.fragment_recipes, container, false);
        mBinding = DataBindingUtil.bind(fragmentView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        mBinding.rvRecipes.setLayoutManager(layoutManager);
        mBinding.rvRecipes.setAdapter(new RecipesAdapter(getContext(), this));

        return fragmentView;
    }

    @Override
    public void onClick(Recipe recipe) {
        Intent recipeStepsListIntent = new Intent(getActivity(), RecipeDetailsActivity.class);
        recipeStepsListIntent.putExtra(RecipeDetailsActivity.RECIPE_EXTRA, recipe);
        getActivity().startActivity(recipeStepsListIntent);
    }
}
