package com.sfotakos.foodsteps.recipestep;

import android.databinding.DataBindingUtil;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.sfotakos.foodsteps.R;
import com.sfotakos.foodsteps.general.Step;
import com.sfotakos.foodsteps.databinding.ActivityRecipeStepsBinding;

import java.util.ArrayList;

public class RecipeStepActivity extends AppCompatActivity {

    public static final String STEP_EXTRA = "STEP_EXTRA_DATA";
    public static final String STEP_CURRENT = "STEP_CURRENT_EXTRA_DATA";
    public static final String RECIPE_NAME_EXTRA = "RECIPE_NAME_EXTRA_DATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityRecipeStepsBinding mBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_steps);

        Toolbar toolbar = mBinding.toolbar;
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        if (getIntent().getExtras() != null) {
            String recipeName = getIntent().getExtras().getString(RECIPE_NAME_EXTRA);
            Integer currentStep = getIntent().getExtras().getInt(STEP_CURRENT);
            ArrayList<Step> steps = (ArrayList<Step>) getIntent().getExtras().getSerializable(STEP_EXTRA);
            if (steps == null || recipeName == null) //TODO treat this error better
                return;

            actionBar.setTitle(recipeName);

            setupFragment(steps, currentStep);
        }
    }

    private void setupFragment(ArrayList<Step> steps, int currentStep) {
        RecipeStepFragment recipeStepFragment =
                (RecipeStepFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (recipeStepFragment == null) {
            // Create the fragment
            recipeStepFragment = RecipeStepFragment.newInstance(steps, currentStep);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentFrame, recipeStepFragment).commit();
        }
    }

}
