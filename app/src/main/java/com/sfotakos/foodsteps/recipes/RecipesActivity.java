package com.sfotakos.foodsteps.recipes;

import android.databinding.DataBindingUtil;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.sfotakos.foodsteps.R;
import com.sfotakos.foodsteps.databinding.ActivityRecipesBinding;

public class RecipesActivity extends AppCompatActivity {

    private ActivityRecipesBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipes);

        Toolbar toolbar = mBinding.toolbar;
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.mipmap.ic_launcher);
        ab.setDisplayHomeAsUpEnabled(true);

        RecipesFragment tasksFragment =
                (RecipesFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (tasksFragment == null) {
            // Create the fragment
            tasksFragment = RecipesFragment.newInstance();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentFrame, tasksFragment).commit();
        }


    }
}
