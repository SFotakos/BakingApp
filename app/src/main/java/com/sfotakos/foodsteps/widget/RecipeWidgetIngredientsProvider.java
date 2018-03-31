package com.sfotakos.foodsteps.widget;

import android.app.LauncherActivity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.sfotakos.foodsteps.Ingredient;
import com.sfotakos.foodsteps.R;
import com.sfotakos.foodsteps.Recipe;

import java.util.ArrayList;

/**
 * Create adapter to populate ingredients list view
 */

class RecipeWidgetIngredientsProvider implements RemoteViewsService.RemoteViewsFactory {
    private ArrayList<Ingredient> ingredients = new ArrayList<>();
    private Context context = null;

    public RecipeWidgetIngredientsProvider(Context applicationContext, Recipe recipe) {
        this.context = applicationContext;

        if (recipe != null) {
            ingredients = recipe.getIngredients();
        }
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getCount() {
        return ingredients.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Ingredient ingredient = ingredients.get(position);

        String ingredientAmountMeasure =
                ingredient.getQuantity() + " (" + ingredient.getMeasure() + ")";

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.item_widget_ingredient);
        remoteViews.setTextViewText(R.id.tv_ingredientName, ingredient.getIngredient());
        remoteViews.setTextViewText(R.id.tv_ingredientAmount, ingredientAmountMeasure);

        return remoteViews;
    }
}
