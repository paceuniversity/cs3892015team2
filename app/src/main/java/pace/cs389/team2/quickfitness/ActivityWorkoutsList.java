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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import pace.cs389.team2.quickfitness.adapter.CustomWorkoutsListAdapter;
import pace.cs389.team2.quickfitness.data.QuickFitnessDAO;
import pace.cs389.team2.quickfitness.model.WorkoutItem;

public class ActivityWorkoutsList extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            getFragmentManager().beginTransaction().replace(R.id.content_place_holder, new FragmentWorkouts()).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_to_workout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add_exercise) {
            Toast.makeText(this, "Add Exercise Clicked.", Toast.LENGTH_LONG).show();
            return true;
        }

        return false;
    }

    public static class FragmentWorkouts extends Fragment {


        private RecyclerView mRecyclerView;
        private QuickFitnessDAO dao;
        CustomWorkoutsListAdapter mWorkoutsAdapter;
        private TextView mListEmpty;

        public FragmentWorkouts() {
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


            View view = inflater.inflate(R.layout.activity_list_workouts, container,
                    false);


            mListEmpty = (TextView) view.findViewById(R.id.txt_list_empty);
            mRecyclerView = (RecyclerView) view.findViewById(R.id.workout_list);
            mRecyclerView.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            llm.setOrientation(LinearLayoutManager.VERTICAL);

            mRecyclerView.setLayoutManager(llm);


            dao = QuickFitnessDAO.getInstance(getActivity());

            setListExercisesAdapter(dao.listWorkouts());

            return view;
        }

        private void setListExercisesAdapter(List<WorkoutItem> mListAdapter) {

            if (mListAdapter.isEmpty()) {
                mListEmpty.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
            } else {
                mListEmpty.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
                mWorkoutsAdapter = new CustomWorkoutsListAdapter(mListAdapter, mRecyclerView);
                mRecyclerView.setAdapter(mWorkoutsAdapter);
            }


        }

    }
}


