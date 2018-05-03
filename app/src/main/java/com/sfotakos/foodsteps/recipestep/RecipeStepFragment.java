package com.sfotakos.foodsteps.recipestep;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.sfotakos.foodsteps.R;
import com.sfotakos.foodsteps.general.Step;
import com.sfotakos.foodsteps.databinding.FragmentRecipeStepBinding;

import java.util.ArrayList;

public class RecipeStepFragment extends Fragment {
    private static final String STEP_PARAM = "STEP_PARAM";
    private static final String STEP_CURRENT_PARAM = "STEP_CURRENT_PARAM";

    private static final String PLAYER_CURRENT_POSITION = "EXOPLAYER_POSITION";
    private static final String PLAYER_PLAY_WHEN_READY = "EXOPLAYER_PLAY_WHEN_READY";

    private static final String CURRENT_STEP = "CURRENT_STEP";

    private FragmentRecipeStepBinding mBinding;

    private ArrayList<Step> stepsExtra;
    private int currentStep;
    private long playerCurrentPosition;
    private boolean playWhenReady;

    private SimpleExoPlayer exoPlayer;

    public RecipeStepFragment() {
        // Required empty public constructor
    }

    public static RecipeStepFragment newInstance(ArrayList<Step> steps, int currentStep) {
        RecipeStepFragment fragment = new RecipeStepFragment();
        Bundle args = new Bundle();
        args.putSerializable(STEP_PARAM, steps);
        args.putInt(STEP_CURRENT_PARAM, currentStep);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            stepsExtra = (ArrayList<Step>) getArguments().getSerializable(STEP_PARAM);
            currentStep = getArguments().getInt(STEP_CURRENT_PARAM);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView =
                inflater.inflate(R.layout.fragment_recipe_step, container, false);

        mBinding = DataBindingUtil.bind(fragmentView);
        if (savedInstanceState != null) {
            currentStep = savedInstanceState.getInt(CURRENT_STEP);
        }

        return fragmentView;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {
            playerCurrentPosition = savedInstanceState.getLong(PLAYER_CURRENT_POSITION);
            playWhenReady = savedInstanceState.getBoolean(PLAYER_PLAY_WHEN_READY);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (exoPlayer != null) {
            outState.putLong(PLAYER_CURRENT_POSITION, playerCurrentPosition);
            outState.putBoolean(PLAYER_PLAY_WHEN_READY, playWhenReady);
            outState.putInt(CURRENT_STEP, currentStep);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (stepsExtra != null) {
            Step step = stepsExtra.get(currentStep);

            mBinding.linearPrevStep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    previousStep();
                }
            });

            mBinding.linearNextStep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nextStep();
                }
            });
            prevNextEnable();

            loadStep(step);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (exoPlayer != null) {
            playerCurrentPosition = exoPlayer.getCurrentPosition();
            playWhenReady = exoPlayer.getPlayWhenReady();
            stopReleaseExoPlayer();
        }
    }

    public void loadStep(Step step, int stepNumber) {
        currentStep = stepNumber;

        stopReleaseExoPlayer();
        playerCurrentPosition = 0;
        playWhenReady = false;

        prevNextEnable();
        loadStep(step);
    }

    private void loadStep(Step step) {
        showAndSetupExoPlayer(step);

        mBinding.tvStepFullDescription.setText(step.getDescription());

        String stepCountString = Integer.toString(currentStep) + "/" +
                Integer.toString(stepsExtra.size() - 1);
        mBinding.tvCurrentStep.setText(stepCountString);
    }

    private void nextStep() {
        playerCurrentPosition = 0;
        playWhenReady = false;

        currentStep++;
        stopReleaseExoPlayer();
        loadStep(stepsExtra.get(currentStep));
        prevNextEnable();
    }

    private void previousStep() {
        playerCurrentPosition = 0;
        playWhenReady = false;

        currentStep--;
        stopReleaseExoPlayer();
        loadStep(stepsExtra.get(currentStep));
        prevNextEnable();
    }

    private void prevNextEnable() {
        if (currentStep == 0) {
            mBinding.linearPrevStep.setVisibility(View.INVISIBLE);
        } else if (currentStep == stepsExtra.size() - 1) {
            mBinding.linearNextStep.setVisibility(View.INVISIBLE);
        } else {
            mBinding.linearPrevStep.setVisibility(View.VISIBLE);
            mBinding.linearNextStep.setVisibility(View.VISIBLE);
        }
    }

    private void showAndSetupExoPlayer(Step step) {
        if (!TextUtils.isEmpty(step.getVideoURL())) {
            setupExoPlayer(Uri.parse(step.getVideoURL()));

            if (playerCurrentPosition != C.TIME_UNSET) exoPlayer.seekTo(playerCurrentPosition);
            exoPlayer.setPlayWhenReady(playWhenReady);

            mBinding.exoPlayerViewStepVideo.setVisibility(View.VISIBLE);
        } else {
            mBinding.exoPlayerViewStepVideo.setVisibility(View.GONE);
        }
    }

    private void setupExoPlayer(Uri videoUri) {
        Context context = getContext();
        if (context == null) return;

        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        exoPlayer =
                ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);

        mBinding.exoPlayerViewStepVideo.setPlayer(exoPlayer);

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                Util.getUserAgent(getContext(), "com.sfotakos.foodsteps"), null);

        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(videoUri);

        exoPlayer.prepare(videoSource);
    }

    private void stopReleaseExoPlayer() {
        if (exoPlayer != null) {
            exoPlayer.stop();
            exoPlayer.release();
        }
    }
}
