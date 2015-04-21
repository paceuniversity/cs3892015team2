/*
 *
 *  * Copyright (C) 2014 The Android Open Source Project
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package pace.cs389.team2.quickfitness.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pace.cs389.team2.quickfitness.ActivityExercisesByWorkoutList;
import pace.cs389.team2.quickfitness.R;
import pace.cs389.team2.quickfitness.data.QuickFitnessDAO;
import pace.cs389.team2.quickfitness.model.ExercisesItem;
import pace.cs389.team2.quickfitness.model.WorkoutItem;
import pace.cs389.team2.quickfitness.model.WorkoutStatusItem;

public class CustomWorkoutsListAdapter extends RecyclerView.Adapter<CustomWorkoutsListAdapter.WorkoutsViewHolder> implements View.OnClickListener {

    public static final String EXERCISE_TAG = "exercise_ref";
    View itemView;
    WorkoutItem mWorkoutItem;
    RecyclerView mRecyclerView;
    private List<WorkoutItem> mWorkoutList;
    private Context mContext;
    QuickFitnessDAO dao;

    public CustomWorkoutsListAdapter(Context mContext, List<WorkoutItem> mWorkoutList, RecyclerView mRecyclerView) {
        this.mContext = mContext;
        this.mRecyclerView = mRecyclerView;
        this.mWorkoutList = mWorkoutList;
    }

    @Override
    public int getItemCount() {
        return mWorkoutList.size();
    }

    @Override
    public WorkoutsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.workout_list_adapter, viewGroup, false);
        itemView.setOnClickListener(this);

        return new WorkoutsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WorkoutsViewHolder workoutsViewHolder, int i) {
        mWorkoutItem = mWorkoutList.get(i);

        dao = QuickFitnessDAO.getInstance(itemView.getContext());

        WorkoutStatusItem itemStatus = dao.statusById(mWorkoutItem.getStatusKey());

        List<ExercisesItem> exercisesItems = dao.listExercisesByWorkout(mWorkoutItem.getId());

        int sum = 0;
        int hour;
        int minutes;

        for (int index = 0; index < exercisesItems.size(); index++) {
            sum += exercisesItems.get(index).getDuration();
        }

        workoutsViewHolder.mWorkoutTitle.setText(mWorkoutItem.getName());

        hour = sum / 60;
        minutes = sum % 60;

        if (hour == 0 && minutes == 0) {

            workoutsViewHolder.mWorkoutDuration.setVisibility(View.GONE);
            workoutsViewHolder.mWorkoutDurationIcon.setVisibility(View.GONE);

        } else {
            workoutsViewHolder.mWorkoutDuration.setVisibility(View.VISIBLE);
            workoutsViewHolder.mWorkoutDurationIcon.setVisibility(View.VISIBLE);

            if (hour == 0) {
                workoutsViewHolder.mWorkoutDuration.setText(String.valueOf(minutes) + "min");

            } else if (minutes == 0) {
                workoutsViewHolder.mWorkoutDuration.setText(String.valueOf(hour) + "h");

            } else {
                workoutsViewHolder.mWorkoutDuration.setText(String.valueOf(hour) + "h:" + String.valueOf(minutes) + "min");

            }
        }

        if (mWorkoutItem.getStatusKey() == 2) {
            workoutsViewHolder.mWorkoutStatusIcon.setImageResource(R.drawable.circle_tag_spinner_green);
        } else if (mWorkoutItem.getStatusKey() == 3) {
            workoutsViewHolder.mWorkoutStatusIcon.setImageResource(R.drawable.circle_tag_spinner_orange);
        }
        workoutsViewHolder.mWorkoutStatus.setText(itemStatus.getStatus());

    }

    @Override
    public void onClick(final View view) {
        int itemPosition = mRecyclerView.getChildPosition(view);
        WorkoutItem mItem = mWorkoutList.get(itemPosition);
        Intent intent = new Intent(mContext, ActivityExercisesByWorkoutList.class);
        intent.putExtra(CustomWorkoutsListAdapter.EXERCISE_TAG, mItem);
        mContext.startActivity(intent);
    }

    public static class WorkoutsViewHolder extends RecyclerView.ViewHolder {
        protected TextView mWorkoutTitle;
        protected TextView mWorkoutDuration;
        protected ImageView mWorkoutDurationIcon;
        protected TextView mWorkoutStatus;
        protected ImageView mWorkoutStatusIcon;

        public WorkoutsViewHolder(View v) {
            super(v);
            mWorkoutTitle = (TextView) v.findViewById(R.id.txt_workout_name);
            mWorkoutDuration = (TextView) v.findViewById(R.id.txt_workout_time);
            mWorkoutDurationIcon = (ImageView) v.findViewById(R.id.img_workout_duration);
            mWorkoutStatus = (TextView) v.findViewById(R.id.txt_workout_status);
            mWorkoutStatusIcon = (ImageView) v.findViewById(R.id.img_workout_status);
        }
    }
}