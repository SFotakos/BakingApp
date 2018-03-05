package com.sfotakos.foodsteps.recipes;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sfotakos.foodsteps.JsonUtil;
import com.sfotakos.foodsteps.R;
import com.sfotakos.foodsteps.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by spyridion on 05/03/18.
 */

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder> {

    private List<Recipe> recipeList = new ArrayList<>();

    public void setRecipesList(List<Recipe> reviewList) {
        this.recipeList = reviewList;
        notifyDataSetChanged();
    }

    public RecipesAdapter(Context context) {
        recipeList = JsonUtil.getRecipes(context.getAssets());
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);

        holder.recipeName.setText(recipe.getName());

    }

    @Override
    public int getItemCount() {
        return recipeList == null ? 0 : recipeList.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {

        TextView recipeName;

        RecipeViewHolder(View itemView) {
            super(itemView);

            recipeName = itemView.findViewById(R.id.tv_recipeName);

        }
    }
}
