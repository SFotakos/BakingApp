package com.sfotakos.foodsteps.recipes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sfotakos.foodsteps.JsonUtil;
import com.sfotakos.foodsteps.R;
import com.sfotakos.foodsteps.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder> {

    private List<Recipe> recipeList = new ArrayList<>();
    private IRecipesAdapter mListener;

    public void setRecipesList(List<Recipe> reviewList) {
        this.recipeList = reviewList;
        notifyDataSetChanged();
    }

    public RecipesAdapter(Context context, IRecipesAdapter listener) {
        //TODO CHANGE TO A NETWORK REQUEST
        recipeList = JsonUtil.getRecipes(context.getAssets());
        mListener = listener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        final Recipe recipe = recipeList.get(position);
        Context context = holder.tvRecipeName.getContext();

        holder.cvRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(recipe);
            }
        });

        holder.tvRecipeName.setText(recipe.getName());

        if (recipe.getImage() != null && !recipe.getImage().isEmpty()){
            Picasso.with(context)
                    .load(recipe.getImage())
                    .placeholder(R.drawable.ic_food)
                    .error(R.drawable.ic_food)
                    .into(holder.ivRecipeImage);
        }

        String inStepsAmount = String.format(
                context.getResources().getString(R.string.food_steps_amount),
                Integer.toString(recipe.getSteps().size()));
        holder.tvRecipeSteps.setText(inStepsAmount);

        String servingsAmount = String.format(
                context.getResources().getString(R.string.food_servings_amount),
                Integer.toString(recipe.getServings()));
        holder.tvRecipeServingAmount.setText(servingsAmount);
    }

    @Override
    public int getItemCount() {
        return recipeList == null ? 0 : recipeList.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {

        final CardView cvRecipe;

        final TextView tvRecipeName;
        final ImageView ivRecipeImage;

        final TextView tvRecipeSteps;

        final TextView tvRecipeServingAmount;

        RecipeViewHolder(View itemView) {
            super(itemView);

            cvRecipe = itemView.findViewById(R.id.cv_recipe);
            tvRecipeName = itemView.findViewById(R.id.tv_recipeName);
            ivRecipeImage = itemView.findViewById(R.id.iv_recipeImage);
            tvRecipeSteps = itemView.findViewById(R.id.tv_stepsAmount);
            tvRecipeServingAmount = itemView.findViewById(R.id.tv_recipeServingAmount);
        }
    }

    public interface IRecipesAdapter{
        void onClick(Recipe recipe);
    }


}
