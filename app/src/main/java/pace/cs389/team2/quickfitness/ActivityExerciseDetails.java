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
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pace.cs389.team2.quickfitness.adapter.CustomExercisesListAdapter;
import pace.cs389.team2.quickfitness.data.QuickFitnessDAO;
import pace.cs389.team2.quickfitness.dialog.WorkoutsListDialog;
import pace.cs389.team2.quickfitness.model.CategoryItem;
import pace.cs389.team2.quickfitness.model.ExercisesItem;
import pace.cs389.team2.quickfitness.utils.OrientationUtils;

public class ActivityExerciseDetails extends ActionBarActivity {

    private static ExercisesItem mExerciseItem;

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

        if (id == R.id.action_add_exercise) {
            WorkoutsListDialog workoutsListDialog = new WorkoutsListDialog();
            workoutsListDialog.setmExerciseId(mExerciseItem.getId());
            workoutsListDialog.show(getFragmentManager(), "workouts_list");

            return true;
        }

        return false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putSerializable(MainActivity.APP_TAG, mExerciseItem);
    }

    public static class FragmentExerciseDetails extends Fragment {


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            android.support.v7.app.ActionBar actionBar = ((ActionBarActivity) getActivity()).getSupportActionBar();

            mExerciseItem = (ExercisesItem) getActivity().getIntent().getSerializableExtra(CustomExercisesListAdapter.EXERCISE_TAG);

            if (getActivity().getActionBar() != null) {
                if (OrientationUtils.isPortrait(getActivity().getResources().getConfiguration())) {
                    actionBar.show();

                    if (mExerciseItem != null) {
                        if (mExerciseItem.getCategoryKey() == 2) {
                            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.material_green)));
                            //getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>" + appName + " </font>"));
                        } else if (mExerciseItem.getCategoryKey() == 3) {
                            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.material_orange)));
                        } else if (mExerciseItem.getCategoryKey() == 4) {
                            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.material_purple)));
                        } else if (mExerciseItem.getCategoryKey() == 5) {
                            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.material_blue_grey)));
                        }
                    } else {
                        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.background_light_gray)));
                    }


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
                mExerciseDuration.setText(String.valueOf(mExerciseItem.getDuration()));
                mExerciseCalories.setText(String.valueOf(mExerciseItem.getCalories()) + "g");
            } else {
                mExerciseVideo = (TextView) mView.findViewById(R.id.txt_video_placeholder);
                mExerciseVideo.setText(mExerciseItem.getVideoAnimation());
            }

            return mView;
        }

    }

}


