package com.sfotakos.foodsteps.recipes;

import com.sfotakos.foodsteps.BaseContract;

/**
 * Created by spyridion on 04/03/18.
 */

public interface RecipesContract {

    interface RecipesView extends BaseContract.BaseView{

    }

    interface RecipesPresenter extends BaseContract.BasePresenter <RecipesView> {

    }

}
