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
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import pace.cs389.team2.quickfitness.adapter.CustomExercisesListAdapter;
import pace.cs389.team2.quickfitness.model.ExercisesItem;
import pace.cs389.team2.quickfitness.utils.OrientationUtils;

public class ActivityExerciseDetails extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_details);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().replace(R.id.exercise_detail_place_holder, new FragmentExerciseDetails()).commit();
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
            Toast.makeText(this, "Clicked at Do Exercise", Toast.LENGTH_LONG).show();
            return true;
        }

        return false;
    }

    public static class FragmentExerciseDetails extends Fragment {


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setHasOptionsMenu(true);

            if (getActivity().getActionBar() != null) {
                if (OrientationUtils.isPortrait(getActivity().getResources().getConfiguration())) {
                    getActivity().getActionBar().show();

                } else {
                    getActivity().getActionBar().hide();
                }
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            ExercisesItem mExerciseItem = (ExercisesItem) getActivity().getIntent().getSerializableExtra(CustomExercisesListAdapter.EXERCISE_TAG);
            Toast.makeText(getActivity(), mExerciseItem.getName(), Toast.LENGTH_LONG).show();

            View mView = inflater.inflate(R.layout.fragment_exercises_detail, container,
                    false);

            return mView;
        }

    }

}


