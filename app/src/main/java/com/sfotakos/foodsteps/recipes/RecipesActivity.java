package com.sfotakos.foodsteps.recipes;

import android.databinding.DataBindingUtil;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.sfotakos.foodsteps.ActivityUtils;
import com.sfotakos.foodsteps.R;
import com.sfotakos.foodsteps.databinding.ActivityRecipesBinding;

public class RecipesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityRecipesBinding mBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipes);

        Toolbar toolbar = mBinding.toolbar;
        setSupportActionBar(toolbar);

        ActivityUtils.applyFontForToolbarTitle(toolbar,
                ResourcesCompat.getFont(this, R.font.comic_black_rabbit));

        setupFragment();
    }

    private void setupFragment(){
        RecipesFragment recipesFragment =
                (RecipesFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (recipesFragment == null) {
            // Create the fragment
            recipesFragment = RecipesFragment.newInstance();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentFrame, recipesFragment).commit();
        }
    }
}
