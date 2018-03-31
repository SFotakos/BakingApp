package com.sfotakos.foodsteps.recipedetails;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sfotakos.foodsteps.R;
import com.sfotakos.foodsteps.general.Step;

import java.util.ArrayList;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepViewHolder> {

    private ArrayList<Step> stepList = new ArrayList<>();
    private final IStepsAdapter mListener;

    public StepsAdapter(ArrayList<Step> stepList, IStepsAdapter listener) {
        this.stepList = stepList;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_step, parent, false);
        return new StepViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final Step step = stepList.get(position);

        holder.linearStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(stepList, position);
            }
        });

        if (position != 0) {
            String stepCount = position + ". ";
            holder.tvStepCount.setText(stepCount);
            holder.tvStepCount.setVisibility(View.VISIBLE);
        } else {
            holder.tvStepCount.setVisibility(View.INVISIBLE);
        }

        holder.tvStepDescription.setText(step.getShortDescription());

        if (step.getVideoURL() != null && !step.getVideoURL().isEmpty()) {
            holder.ivVideo.setImageResource(android.R.drawable.ic_media_play);
            holder.ivVideo.setVisibility(View.VISIBLE);
        } else {
            holder.ivVideo.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return stepList == null ? 0 : stepList.size();
    }

    class StepViewHolder extends RecyclerView.ViewHolder {

        final LinearLayout linearStep;
        final TextView tvStepCount;
        final TextView tvStepDescription;
        final ImageView ivVideo;

        StepViewHolder(View itemView) {
            super(itemView);

            linearStep = itemView.findViewById(R.id.linear_itemStep);
            tvStepCount = itemView.findViewById(R.id.tv_stepCount);
            tvStepDescription = itemView.findViewById(R.id.tv_stepDescription);
            ivVideo = itemView.findViewById(R.id.iv_video);
        }
    }

    public interface IStepsAdapter{
        void onClick(ArrayList<Step> steps, int currentStep);
    }

}
