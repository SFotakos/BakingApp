package com.sfotakos.foodsteps;

/**
 * Created by spyridion on 04/03/18.
 */

public interface BaseContract {

    interface BaseView {

    }

    interface BasePresenter<T extends BaseView> {

        void attach (T view);
        void detach ();

    }
}
