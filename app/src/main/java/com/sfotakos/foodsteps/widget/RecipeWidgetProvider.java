package com.sfotakos.foodsteps.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.sfotakos.foodsteps.JsonUtil;
import com.sfotakos.foodsteps.R;
import com.sfotakos.foodsteps.Recipe;
import com.sfotakos.foodsteps.recipedetails.RecipeDetailsActivity;

import java.util.List;

import static com.sfotakos.foodsteps.widget.RecipeWidgetConfiguration.WIDGET_SELECTED_RECIPE;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                       int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
        Recipe recipe = JsonUtil.getRecipe(RecipeWidgetPreferenceUtil.getPersistedRecipe(context, appWidgetId));

        if (recipe != null) {

            Intent recipeDetailsIntent = new Intent(context, RecipeDetailsActivity.class);
            recipeDetailsIntent.putExtra(RecipeDetailsActivity.RECIPE_EXTRA, recipe);

            PendingIntent recipeDetailsPendingIntent =
                    PendingIntent.getActivity(context, appWidgetId, recipeDetailsIntent, 0);

            views.setOnClickPendingIntent(R.id.rl_widget_root, recipeDetailsPendingIntent);
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

