package com.sfotakos.foodsteps.recipes;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.sfotakos.foodsteps.ActivityUtils;
import com.sfotakos.foodsteps.JsonUtil;
import com.sfotakos.foodsteps.R;
import com.sfotakos.foodsteps.databinding.ActivityRecipesBinding;
import com.sfotakos.foodsteps.general.NetworkUtils;
import com.sfotakos.foodsteps.general.Recipe;

import java.io.IOException;
import java.util.ArrayList;

public class RecipesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityRecipesBinding mBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipes);

        Toolbar toolbar = mBinding.toolbar;
        setSupportActionBar(toolbar);

        ActivityUtils.applyFontForToolbarTitle(toolbar,
                ResourcesCompat.getFont(this, R.font.comic_black_rabbit));

        new FetchRecipes().execute();

    }

    private void setupFragment(ArrayList<Recipe> recipes){
        RecipesFragment recipesFragment =
                (RecipesFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (recipesFragment == null) {
            // Create the fragment
            recipesFragment = RecipesFragment.newInstance(recipes);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentFrame, recipesFragment).commit();
        }
    }

    private class FetchRecipes extends AsyncTask<Void, Void, ArrayList<Recipe>> {

        @Override
        protected void onPreExecute() {
//            mBinding.tvErrorMessage.setVisibility(View.GONE);
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Recipe> doInBackground(Void ... params) {

            try {
                String jsonResponse = NetworkUtils.getResponseFromHttpUrl(NetworkUtils.buildRecipesUrl());
                return JsonUtil.getRecipes(jsonResponse);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Recipe> recipes) {
            if (recipes != null) {
                setupFragment(recipes);
            } else {
//                showErrorMessage(getResources().getString(R.string.error_default));
            }
        }
    }
}
