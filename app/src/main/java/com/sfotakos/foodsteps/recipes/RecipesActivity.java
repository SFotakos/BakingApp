package com.sfotakos.foodsteps.recipes;

import android.databinding.DataBindingUtil;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.sfotakos.foodsteps.ActivityUtils;
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
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_launcher);
        actionBar.setDisplayHomeAsUpEnabled(true);

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
