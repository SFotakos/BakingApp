package com.sfotakos.foodsteps;

import android.content.res.AssetManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sfotakos.foodsteps.general.Recipe;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

import static android.content.ContentValues.TAG;

public class JsonUtil {

    public static List<Recipe> getRecipes(AssetManager assetManager) {
        String jsonString = getAssetsJSON(assetManager);
        Log.d(TAG, "Json: " + jsonString);
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Recipe>>(){}.getType();
        List<Recipe> recipes = gson.fromJson(jsonString, listType);
        return recipes;
    }

    public static Recipe getRecipe(String json) {
        Gson gson = new Gson();
        Type listType = new TypeToken<Recipe>(){}.getType();
        Recipe recipe = gson.fromJson(json, listType);
        return recipe;
    }

    /* Get File in Assets Folder */
    private static String getAssetsJSON(AssetManager assetManager) {
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

    /* Get File in Assets Folder */
    public static String generateJson(Object pojo) {
        Gson gson = new Gson();
        return gson.toJson(pojo);
    }
}
