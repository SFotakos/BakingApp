package com.sfotakos.foodsteps.recipesteps;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sfotakos.foodsteps.DividerItemDecoration;
import com.sfotakos.foodsteps.R;
import com.sfotakos.foodsteps.Recipe;
import com.sfotakos.foodsteps.databinding.FragmentRecipeDetailsBinding;
import com.sfotakos.foodsteps.recipes.RecipesAdapter;

public class RecipeDetailsFragment extends Fragment {
    private static final String RECIPE_PARAM = "RECIPE_PARAM";

    private FragmentRecipeDetailsBinding mBinding;

    private Recipe recipeExtra;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView =
                inflater.inflate(R.layout.fragment_recipe_details, container, false);

        mBinding = DataBindingUtil.bind(fragmentView);

        if (recipeExtra != null){
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
            rvRecipeSteps.setAdapter(new StepsAdapter(recipeExtra.getSteps()));
        }

        return fragmentView;
    }
}
