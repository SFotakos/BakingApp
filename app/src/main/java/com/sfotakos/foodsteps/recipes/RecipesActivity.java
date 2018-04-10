package com.sfotakos.foodsteps.recipes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.sfotakos.foodsteps.ActivityUtils;
import com.sfotakos.foodsteps.JsonUtil;
import com.sfotakos.foodsteps.R;
import com.sfotakos.foodsteps.databinding.ActivityRecipesBinding;
import com.sfotakos.foodsteps.general.NetworkUtils;
import com.sfotakos.foodsteps.general.Recipe;

import java.io.IOException;
import java.util.ArrayList;

public class RecipesActivity extends AppCompatActivity {

    private ActivityRecipesBinding mBinding;
    private ConnectivityReceiver connectivityReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipes);

        Toolbar toolbar = mBinding.toolbar;
        setSupportActionBar(toolbar);

        ActivityUtils.applyFontForToolbarTitle(toolbar,
                ResourcesCompat.getFont(this, R.font.comic_black_rabbit));

        if (ActivityUtils.hasConnection(this)) {
            new FetchRecipes().execute();
        } else {
            showErrorMessage(getResources().getString(R.string.error_no_connectivity));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (connectivityReceiver != null) {
            unregisterReceiver(connectivityReceiver);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        connectivityReceiver = new ConnectivityReceiver();
        registerReceiver(connectivityReceiver, filter);
    }

    private void setupFragment(ArrayList<Recipe> recipes) {
        showRecipes();
        RecipesFragment recipesFragment =
                (RecipesFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (recipesFragment == null) {
            // Create the fragment
            recipesFragment = RecipesFragment.newInstance(recipes);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentFrame, recipesFragment).commit();
        }
    }

    private void showRecipes() {
        mBinding.tvErrorMessage.setVisibility(View.GONE);
        mBinding.contentFrame.setVisibility(View.VISIBLE);
    }

    public void showErrorMessage(String errorMessage) {
        mBinding.tvErrorMessage.setText(errorMessage);
        mBinding.tvErrorMessage.setVisibility(View.VISIBLE);
    }

    private class FetchRecipes extends AsyncTask<Void, Void, ArrayList<Recipe>> {

        @Override
        protected void onPreExecute() {
            mBinding.tvErrorMessage.setVisibility(View.GONE);
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Recipe> doInBackground(Void... params) {

            try {
                String jsonResponse = NetworkUtils.getResponseFromHttpUrl(NetworkUtils.buildRecipesUrl());
                return JsonUtil.getRecipes(jsonResponse);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Recipe> recipes) {
            if (recipes != null) {
                setupFragment(recipes);
            } else {
                showErrorMessage(getResources().getString(R.string.error_default));
            }
        }
    }

    private class ConnectivityReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (ActivityUtils.hasConnection(context)) {
                new FetchRecipes().execute();
            } else {
                showErrorMessage(getResources().getString(R.string.error_no_connectivity));
            }
        }
    }
}
