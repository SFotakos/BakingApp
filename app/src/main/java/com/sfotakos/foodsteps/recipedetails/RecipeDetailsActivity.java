package com.sfotakos.foodsteps.recipedetails;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.sfotakos.foodsteps.R;
import com.sfotakos.foodsteps.general.Recipe;
import com.sfotakos.foodsteps.databinding.ActivityRecipeDetailsBinding;
import com.sfotakos.foodsteps.general.Step;
import com.sfotakos.foodsteps.recipestep.RecipeStepActivity;
import com.sfotakos.foodsteps.recipestep.RecipeStepFragment;

import java.util.ArrayList;

public class RecipeDetailsActivity extends AppCompatActivity implements StepsAdapter.IStepsAdapter{

    public static final String RECIPE_EXTRA = "RECIPE_EXTRA_DATA";
    private boolean mIsMultiPane = false;
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityRecipeDetailsBinding mBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_details);

        Toolbar toolbar = mBinding.toolbar;
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        if (getIntent().getExtras() != null) {
            recipe = getIntent().getExtras().getParcelable(RECIPE_EXTRA);
            if (recipe == null) //TODO treat this error better
                return;

            actionBar.setTitle(recipe.getName());

            mIsMultiPane = getResources().getBoolean(R.bool.material_responsive_is_tablet);
            setupFragment(recipe);
        }
    }

    private void setupFragment(Recipe recipe) {
        if (mIsMultiPane) {

            RecipeDetailsFragment recipeDetailsFragment = (RecipeDetailsFragment)
                    getSupportFragmentManager().findFragmentById(R.id.recipeStepsContentFrame);
            if (recipeDetailsFragment == null) {
                // Create the fragment
                recipeDetailsFragment = RecipeDetailsFragment.newInstance(recipe);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.recipeStepsContentFrame, recipeDetailsFragment)
                        .commit();
            }

            RecipeStepFragment stepsDetailFragment = (RecipeStepFragment)
                    getSupportFragmentManager().findFragmentById(R.id.recipeStepDetailContentFrame);
            if (stepsDetailFragment == null) {
                // Create the fragment
                stepsDetailFragment = RecipeStepFragment.newInstance(recipe.getSteps(), 0);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.recipeStepDetailContentFrame, stepsDetailFragment)
                        .commit();
            }



        } else {
            RecipeDetailsFragment recipeDetailsFragment = (RecipeDetailsFragment)
                    getSupportFragmentManager().findFragmentById(R.id.contentFrame);
            if (recipeDetailsFragment == null) {
                // Create the fragment
                recipeDetailsFragment = RecipeDetailsFragment.newInstance(recipe);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.contentFrame, recipeDetailsFragment).commit();
            }
        }
    }

    @Override
    public void onClick(ArrayList<Step> steps, int currentStep) {
        if (!mIsMultiPane){
            Intent recipeStepsListIntent = new Intent(this, RecipeStepActivity.class);
            recipeStepsListIntent.putExtra(RecipeStepActivity.RECIPE_NAME_EXTRA, recipe.getName());
            recipeStepsListIntent.putExtra(RecipeStepActivity.STEP_EXTRA, steps);
            recipeStepsListIntent.putExtra(RecipeStepActivity.STEP_CURRENT, currentStep);
            startActivity(recipeStepsListIntent);
        } else {
            RecipeStepFragment stepsDetailFragment = (RecipeStepFragment)
                    getSupportFragmentManager().findFragmentById(R.id.recipeStepDetailContentFrame);
            if (stepsDetailFragment != null){
                stepsDetailFragment.loadStep(recipe.getSteps().get(currentStep), currentStep);
            }
        }
    }
}
