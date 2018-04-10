package com.sfotakos.foodsteps;

import android.content.res.AssetManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sfotakos.foodsteps.general.Recipe;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class JsonUtil {

    public static ArrayList<Recipe> getRecipes(String recipeJson) {
        Log.d(TAG, "Json: " + recipeJson);
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Recipe>>(){}.getType();
        return gson.fromJson(recipeJson, listType);
    }

    public static Recipe parseRecipeFromJson(String json) {
        Gson gson = new Gson();
        Type listType = new TypeToken<Recipe>(){}.getType();
        Recipe recipe = gson.fromJson(json, listType);
        return recipe;
    }

    /* Get File in Assets Folder */
    public static String generateJson(Object pojo) {
        Gson gson = new Gson();
        return gson.toJson(pojo);
    }

    public static List<Recipe> getRecipesFromAsset(AssetManager assetManager) {
        String jsonString = getRecipeFromAssetsJSON(assetManager);
        Log.d(TAG, "Json: " + jsonString);
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Recipe>>(){}.getType();
        List<Recipe> recipes = gson.fromJson(jsonString, listType);
        return recipes;
    }

    /* Get File in Assets Folder */
    private static String getRecipeFromAssetsJSON(AssetManager assetManager) {
        String json = null;
        try {
            InputStream inputStream = assetManager.open("bakingRecipes.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return json;
    }
}
