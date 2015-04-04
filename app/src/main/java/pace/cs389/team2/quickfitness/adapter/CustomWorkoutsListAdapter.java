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

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pace.cs389.team2.quickfitness.R;
import pace.cs389.team2.quickfitness.data.QuickFitnessDAO;
import pace.cs389.team2.quickfitness.model.WorkoutItem;
import pace.cs389.team2.quickfitness.model.WorkoutStatusItem;

public class CustomWorkoutsListAdapter extends RecyclerView.Adapter<CustomWorkoutsListAdapter.WorkoutsViewHolder> implements View.OnClickListener {

    public static final String EXERCISE_TAG = "exercise_ref";
    private List<WorkoutItem> mWorkoutList;
    View itemView;
    WorkoutItem mWorkoutItem;
    RecyclerView mRecyclerView;

    public CustomWorkoutsListAdapter(List<WorkoutItem> mWorkoutList, RecyclerView mRecyclerView) {
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

        WorkoutStatusItem itemStatus = QuickFitnessDAO.getInstance(itemView.getContext()).statusById(mWorkoutItem.getStatusKey());

        workoutsViewHolder.mWorkoutTitle.setText(mWorkoutItem.getName());
        workoutsViewHolder.mWorkoutStatus.setText(itemStatus.getStatus());
    }


    public static class WorkoutsViewHolder extends RecyclerView.ViewHolder {
        protected TextView mWorkoutTitle;
        protected TextView mWorkoutStatus;

        public WorkoutsViewHolder(View v) {
            super(v);
            mWorkoutTitle = (TextView) v.findViewById(R.id.txt_workout_name);
            mWorkoutStatus = (TextView) v.findViewById(R.id.txt_workout_status);

        }
    }

    @Override
    public void onClick(final View view) {
      /*  int itemPosition = mRecyclerView.getChildPosition(view);
        WorkoutItem mItem = mWorkoutList.get(itemPosition);
        Intent intent = new Intent(itemView.getContext(), ActivityExerciseDetails.class);
        intent.putExtra(EXERCISE_TAG, mItem);
        itemView.getContext().startActivity(intent);*/

    }
}