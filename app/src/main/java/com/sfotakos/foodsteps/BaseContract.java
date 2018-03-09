package com.sfotakos.foodsteps;

public interface BaseContract {

    interface BaseView {

    }

    interface BasePresenter<T extends BaseView> {

        void attach (T view);
        void detach ();

    }
}
