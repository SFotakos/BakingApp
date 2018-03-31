package com.sfotakos.foodsteps.widget;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Adds simple recipe persistence to work with widgets.
 */

class RecipeWidgetPreferenceUtil {

    static String widgetPrefs = "WIDGET_PREFERENCES";
    static String widgetRecipe = "WIDGET_SAVED_RECIPE";

    static void persistRecipe(Context context, String recipeJson, int widgetId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(widgetPrefs, 0);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putString(widgetRecipe + widgetId, recipeJson);
        sharedPrefEditor.apply();
    }

    static String getPersistedRecipe(Context context, int widgetId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(widgetPrefs, 0);
        return sharedPreferences.getString(widgetRecipe + widgetId, null);
    }

    static void deleteRecipe(Context context, int widgetId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(widgetPrefs, 0);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.remove(widgetRecipe + widgetId);
        sharedPrefEditor.apply();
    }
}
