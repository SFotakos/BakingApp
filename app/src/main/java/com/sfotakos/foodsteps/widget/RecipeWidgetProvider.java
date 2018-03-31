package com.sfotakos.foodsteps.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.sfotakos.foodsteps.JsonUtil;
import com.sfotakos.foodsteps.R;
import com.sfotakos.foodsteps.general.Recipe;
import com.sfotakos.foodsteps.recipedetails.RecipeDetailsActivity;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                       int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);

        String jsonRecipe = RecipeWidgetPreferenceUtil.getPersistedRecipe(context, appWidgetId);
        Recipe recipe = JsonUtil.parseRecipeFromJson(jsonRecipe);
        if (recipe != null) {
            views.setTextViewText(R.id.tv_recipeName, recipe.getName());

//            // Adding ingredients to listView
            Intent widgetServiceIntent = new Intent(context, RecipeWidgetIngredientService.class);
            widgetServiceIntent.putExtra(RecipeWidgetIngredientService.RECIPE, jsonRecipe);
            widgetServiceIntent.setData(Uri.parse(
                    widgetServiceIntent.toUri(Intent.URI_INTENT_SCHEME)));
            views.setRemoteAdapter(R.id.lv_ingredients, widgetServiceIntent);

            // Pending intent that opens activity with the proper recipe
            Intent recipeDetailsIntent = new Intent(context, RecipeDetailsActivity.class);
            recipeDetailsIntent.putExtra(RecipeDetailsActivity.RECIPE_EXTRA, recipe);
            PendingIntent recipeDetailsPendingIntent =
                    PendingIntent.getActivity(context, appWidgetId, recipeDetailsIntent, 0);
            views.setOnClickPendingIntent(R.id.ll_widget_root, recipeDetailsPendingIntent);
        }

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            RecipeWidgetPreferenceUtil.deleteRecipe(context, appWidgetId);
        }
        super.onDeleted(context, appWidgetIds);
    }
}

