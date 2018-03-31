package com.sfotakos.foodsteps.recipedetails;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sfotakos.foodsteps.general.Ingredient;
import com.sfotakos.foodsteps.R;

import java.util.ArrayList;
import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder> {

    private List<Ingredient> ingredientList = new ArrayList<>();

    public IngredientsAdapter(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_ingredient, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        final Ingredient ingredient = ingredientList.get(position);

        holder.tvIngredientName.setText(ingredient.getIngredient());

        String ingredientAmountMeasure =
                ingredient.getQuantity() + " (" + ingredient.getMeasure() + ")";
        holder.tvIngredientAmount.setText(ingredientAmountMeasure);
    }

    @Override
    public int getItemCount() {
        return ingredientList == null ? 0 : ingredientList.size();
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {

        final TextView tvIngredientName;
        final TextView tvIngredientAmount;

        IngredientViewHolder(View itemView) {
            super(itemView);

            tvIngredientName = itemView.findViewById(R.id.tv_ingredientName);
            tvIngredientAmount = itemView.findViewById(R.id.tv_ingredientAmount);
        }
    }
}
