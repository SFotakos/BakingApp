package com.sfotakos.foodsteps.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.sfotakos.foodsteps.JsonUtil;
import com.sfotakos.foodsteps.general.Recipe;

/**
 * Assign the adapter that populates the listView
 */

public class RecipeWidgetIngredientService extends RemoteViewsService {

    static final String RECIPE = "RECIPE_EXTRA";

    @Override
    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {
        String jsonString = intent.getStringExtra(RECIPE);
        Recipe recipe = JsonUtil.getRecipe(jsonString);

        return (new RecipeWidgetIngredientsProvider(this.getApplicationContext(), recipe));
    }

}
