package com.sfotakos.foodsteps.recipedetails;

import android.databinding.DataBindingUtil;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.sfotakos.foodsteps.R;
import com.sfotakos.foodsteps.Recipe;
import com.sfotakos.foodsteps.databinding.ActivityRecipeDetailsBinding;

public class RecipeDetailsActivity extends AppCompatActivity {

    public static final String RECIPE_EXTRA = "RECIPE_EXTRA_DATA";

    private ActivityRecipeDetailsBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_details);

        Toolbar toolbar = mBinding.toolbar;
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        if (getIntent().getExtras() != null) {
            Recipe recipe = getIntent().getExtras().getParcelable(RECIPE_EXTRA);
            if (recipe == null) //TODO treat this error better
                return;

            actionBar.setTitle(recipe.getName());

            setupFragment(recipe);
        }
    }

    private void setupFragment(Recipe recipe) {
        RecipeDetailsFragment recipeDetailsFragment =
                (RecipeDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (recipeDetailsFragment == null) {
            // Create the fragment
            recipeDetailsFragment = RecipeDetailsFragment.newInstance(recipe);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentFrame, recipeDetailsFragment).commit();
        }
    }
}
