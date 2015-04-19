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

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import pace.cs389.team2.quickfitness.adapter.CustomExercisesListAdapter;
import pace.cs389.team2.quickfitness.data.QuickFitnessDAO;
import pace.cs389.team2.quickfitness.dialog.WorkoutsListDialog;
import pace.cs389.team2.quickfitness.model.CategoryItem;
import pace.cs389.team2.quickfitness.model.ExercisesItem;
import pace.cs389.team2.quickfitness.model.WorkoutItem;
import pace.cs389.team2.quickfitness.preferences.UserLoggedPreference;
import pace.cs389.team2.quickfitness.utils.OrientationUtils;

/**
 * This class shows an activity to display details about an exercise. When the user clicks on one item in the 'Exercise List Screen', the app takes the user to this activity,
 * which will show more information about the item desired. Information contains: Exercise title, description, category, calories burned, duration, and a cool video demonstration
 * on how to do the exercise in practise.
 *
 * @author CS389 Team2
 * @since 03/25/2015
 */

public class ActivityExerciseDetails extends ActionBarActivity {

    private static ExercisesItem mExerciseItem;
    public static final String LOGIN_FLOW_KEY = "login_flow";
    public static final String CLASS_NAME = ActivityExerciseDetails.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_details);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().replace(R.id.exercise_detail_place_holder, new FragmentExerciseDetails()).commit();
        } else {
            mExerciseItem = (ExercisesItem) savedInstanceState.get(MainActivity.APP_TAG);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_to_workout_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        UserLoggedPreference prefs = new UserLoggedPreference(this);

        if (id == R.id.action_add_exercise) {
            if (prefs.isFirstTime()) {
                Toast.makeText(this, "Please, sign in before selecting this exercise.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, ActivityIntro.class);
                intent.putExtra(LOGIN_FLOW_KEY, CLASS_NAME);
                startActivity(intent);
                finish();
            } else {
                selectWorkoutDialog();
            }
            return true;
        }

        return false;
    }

    private void selectWorkoutDialog() {
        List<WorkoutItem> workoutItemList = QuickFitnessDAO.getInstance(getApplicationContext()).listWorkouts();

        if (workoutItemList.size() > 0) {
            WorkoutsListDialog workoutsListDialog = new WorkoutsListDialog();
            workoutsListDialog.setExerciseId(mExerciseItem.getId());
            workoutsListDialog.show(getFragmentManager(), "workouts_list");
        } else {
            Toast.makeText(this, "You don't have any workout.", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putSerializable(MainActivity.APP_TAG, mExerciseItem);
    }

    /**
     * FragmentExerciseDetails show inflates the view to display data on the screen.
     *
     * @author CS389 Team2
     * @since 03/25/2015
     */

    public static class FragmentExerciseDetails extends Fragment {


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            android.support.v7.app.ActionBar actionBar = ((ActionBarActivity) getActivity()).getSupportActionBar();

            mExerciseItem = (ExercisesItem) getActivity().getIntent().getSerializableExtra(CustomExercisesListAdapter.EXERCISE_TAG);

            if (getActivity().getActionBar() != null) {
                if (OrientationUtils.isPortrait(getActivity().getResources().getConfiguration())) {
                    actionBar.show();
                } else {
                    actionBar.hide();
                }
            }
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            mExerciseItem = (ExercisesItem) getActivity().getIntent().getSerializableExtra(CustomExercisesListAdapter.EXERCISE_TAG);

            View mView = inflater.inflate(R.layout.fragment_exercises_detail, container,
                    false);
            TextView mExerciseVideo;
            if (OrientationUtils.isPortrait(getActivity().getResources().getConfiguration())) {
                CategoryItem categoryItem = QuickFitnessDAO.getInstance(getActivity()).categoryById(mExerciseItem.getCategoryKey());

                mExerciseVideo = (TextView) mView.findViewById(R.id.txt_video_placeholder);
                TextView mExerciseTitle = (TextView) mView.findViewById(R.id.txt_video_title);
                TextView mExerciseDescription = (TextView) mView.findViewById(R.id.txt_video_description);
                TextView mExerciseCategory = (TextView) mView.findViewById(R.id.txt_exercise_category);
                TextView mExerciseLevel = (TextView) mView.findViewById(R.id.txt_workout_level);
                TextView mExerciseDuration = (TextView) mView.findViewById(R.id.txt_workout_duration);
                TextView mExerciseCalories = (TextView) mView.findViewById(R.id.txt_workout_calories);

                mExerciseVideo.setText(mExerciseItem.getVideoAnimation());
                mExerciseTitle.setText(mExerciseItem.getName());
                mExerciseDescription.setText(mExerciseItem.getDescription());
                mExerciseCategory.setText(categoryItem.getName());
                mExerciseLevel.setText(mExerciseItem.getLevel());
                mExerciseDuration.setText(String.valueOf(mExerciseItem.getDuration()) + " minutes");
                mExerciseCalories.setText(String.valueOf(mExerciseItem.getCalories()) + "g");
            } else {
                mExerciseVideo = (TextView) mView.findViewById(R.id.txt_video_placeholder);
                mExerciseVideo.setText(mExerciseItem.getVideoAnimation());
            }

            return mView;
        }

    }

}


