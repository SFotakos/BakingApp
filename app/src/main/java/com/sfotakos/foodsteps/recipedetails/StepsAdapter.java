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
import com.sfotakos.foodsteps.Step;

import java.util.ArrayList;
import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepViewHolder> {

    private List<Step> stepList = new ArrayList<>();
    private IStepsAdapter mListener;

    public StepsAdapter(List<Step> stepList, IStepsAdapter listener) {
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
    public void onBindViewHolder(@NonNull StepViewHolder holder, final int position) {
        final Step step = stepList.get(position);

        holder.linearStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(step, position, stepList.size());
            }
        });

        String stepCount = Integer.toString(position + 1) + ". ";
        holder.tvStepCount.setText(stepCount);
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
        void onClick(Step step, int currentStep, int stepCount);
    }

}
