package com.sfotakos.foodsteps.recipes;

import com.sfotakos.foodsteps.BaseContract;

public interface RecipesContract {

    interface RecipesView extends BaseContract.BaseView{

    }

    interface RecipesPresenter extends BaseContract.BasePresenter <RecipesView> {

    }

}
