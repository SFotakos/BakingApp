package com.sfotakos.foodsteps.widget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sfotakos.foodsteps.JsonUtil;
import com.sfotakos.foodsteps.R;
import com.sfotakos.foodsteps.general.NetworkUtils;
import com.sfotakos.foodsteps.general.Recipe;
import com.sfotakos.foodsteps.recipes.RecipesAdapter;

import java.io.IOException;
import java.util.ArrayList;

public class RecipeWidgetConfiguration extends Activity implements RecipesAdapter.IRecipesAdapter {

    private int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private RecyclerView recipeList;

    public RecipeWidgetConfiguration() {
        super();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_recipe_widget_configuration);
        
        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

        recipeList = findViewById(R.id.rv_recipes);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        recipeList.setLayoutManager(layoutManager);

        new FetchRecipes().execute();

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }
    }

    @Override
    public void onClick(Recipe recipe) {

        String recipeJson = JsonUtil.generateJson(recipe);
        RecipeWidgetPreferenceUtil.persistRecipe(this, recipeJson, mAppWidgetId);

        // It is the responsibility of the configuration activity to update the app widget
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        RecipeWidgetProvider.updateAppWidget(this, appWidgetManager, mAppWidgetId);

        // Make sure we pass back the original appWidgetId
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
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
                recipeList.setAdapter(new RecipesAdapter(RecipeWidgetConfiguration.this, recipes));
            } else {
//                showErrorMessage(getResources().getString(R.string.error_default));
            }
        }
    }


}