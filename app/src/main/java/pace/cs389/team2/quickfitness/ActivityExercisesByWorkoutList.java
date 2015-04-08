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

package pace.cs389.team2.quickfitness;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import pace.cs389.team2.quickfitness.adapter.CustomWorkoutsListAdapter;
import pace.cs389.team2.quickfitness.adapter.ExercisesByWorkoutCustomAdapter;
import pace.cs389.team2.quickfitness.data.QuickFitnessDAO;
import pace.cs389.team2.quickfitness.model.ExercisesItem;
import pace.cs389.team2.quickfitness.model.WorkoutItem;

public class ActivityExercisesByWorkoutList extends ActionBarActivity {

    static WorkoutItem mWorkoutItem;
    private ListView mListExercises;
    private TextView mTextListEmpty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_exercises_by_workout);

        mListExercises = (ListView) findViewById(R.id.exercise_by_workout_list);
        mTextListEmpty = (TextView) findViewById(R.id.txt_list_empty);

        mWorkoutItem = (WorkoutItem) getIntent().getSerializableExtra(CustomWorkoutsListAdapter.EXERCISE_TAG);

        QuickFitnessDAO dao = QuickFitnessDAO.getInstance(getApplicationContext());

        setListExercisesAdapter(dao.listExercisesByWorkout(mWorkoutItem.getId()));

    }


    private void setListExercisesAdapter(List<ExercisesItem> mListAdapter) {

        if (mListAdapter.isEmpty()) {
            mTextListEmpty.setVisibility(View.VISIBLE);
            mListExercises.setVisibility(View.GONE);
        } else {
            mTextListEmpty.setVisibility(View.GONE);
            mListExercises.setVisibility(View.VISIBLE);
            mListExercises.setAdapter(new ExercisesByWorkoutCustomAdapter(this, mListAdapter));

        }


    }


}
