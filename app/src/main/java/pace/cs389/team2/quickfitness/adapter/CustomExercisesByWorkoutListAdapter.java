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
import android.widget.Toast;

import java.util.List;

import pace.cs389.team2.quickfitness.ActivityWorkoutsList;
import pace.cs389.team2.quickfitness.R;
import pace.cs389.team2.quickfitness.model.ExercisesItem;

public class CustomExercisesByWorkoutListAdapter extends RecyclerView.Adapter<CustomExercisesByWorkoutListAdapter.ExerciseByWorkoutHolder> implements View.OnClickListener {

    private List<ExercisesItem> exercisesItems;
    ExercisesItem mExerciseItem;
    View itemView;
    RecyclerView mRecyclerView;

    public CustomExercisesByWorkoutListAdapter(List<ExercisesItem> exercisesItems, RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
        this.exercisesItems = exercisesItems;
    }

    @Override
    public int getItemCount() {
        return exercisesItems.size();
    }

    @Override
    public ExerciseByWorkoutHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.exercise_by_workout_adapter, viewGroup, false);
        itemView.setOnClickListener(this);

        return new ExerciseByWorkoutHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ExerciseByWorkoutHolder exerciseByWorkoutHolder, int i) {

       /* ActivityExercisesByWorkoutList exercisesByWorkoutList = new ActivityExercisesByWorkoutList();

        List<WorkoutExercisesItem> mExercisesIds = QuickFitnessDAO.getInstance(itemView.getContext()).listExercisesByWorkout(exercisesByWorkoutList.getWorkoutId());

        for (int index = 0; index < mExercisesIds.size(); index++) {
            exercisesItems.add(QuickFitnessDAO.getInstance(itemView.getContext()).exercisesById(mExercisesIds.get(index).getExerciseId()));
        }*/

        mExerciseItem = exercisesItems.get(i);

        exerciseByWorkoutHolder.mExercisesByWorkout.setText(mExerciseItem.getName());
    }


    public static class ExerciseByWorkoutHolder extends RecyclerView.ViewHolder {
        protected TextView mExercisesByWorkout;

        public ExerciseByWorkoutHolder(View v) {
            super(v);
            mExercisesByWorkout = (TextView) v.findViewById(R.id.txt_exercise_name);

        }
    }

    @Override
    public void onClick(final View view) {
        int itemPosition = mRecyclerView.getChildPosition(view);
        // WorkoutItem mItem = mWorkoutList.get(itemPosition);
/*        Intent intent = new Intent(itemView.getContext(), ActivityExerciseDetails.class);
        intent.putExtra(EXERCISE_TAG, mItem);
        itemView.getContext().startActivity(intent);*/
        ActivityWorkoutsList activityWorkoutsList = new ActivityWorkoutsList();
        //activityWorkoutsList.setWorkoutId(mItem.getId());

      //  Toast.makeText(itemView.getContext(), "Id: " + activityWorkoutsList.getWorkoutId(), Toast.LENGTH_LONG).show();

    }
}