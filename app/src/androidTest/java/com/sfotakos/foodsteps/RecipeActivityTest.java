package com.sfotakos.foodsteps;

import android.app.Activity;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.sfotakos.foodsteps.general.Recipe;
import com.sfotakos.foodsteps.recipes.RecipesActivity;
import com.sfotakos.foodsteps.recipes.RecipesFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Recipe Activity Test Class
 */

//TODO Wait for recipe to download using IdlingResource

@RunWith(AndroidJUnit4.class)
public class RecipeActivityTest {

    @Rule
    public ActivityTestRule<RecipesActivity> mActivityTestRule =
            new ActivityTestRule<>(RecipesActivity.class);

    @Before
    public void init(){
        ArrayList<Recipe> recipes = (ArrayList<Recipe>)
                JsonUtil.getRecipesFromAsset(mActivityTestRule.getActivity().getAssets());

        RecipesFragment recipesFragment =
                (RecipesFragment) mActivityTestRule.getActivity()
                        .getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (recipesFragment == null) {
            recipesFragment = RecipesFragment.newInstance(recipes);

            mActivityTestRule.getActivity().getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentFrame, recipesFragment).commit();
        }
    }

    @Test
    public void clickRecipe_NavigateToRecipeDetails() {
        onView(withId(R.id.rv_recipes))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

}
