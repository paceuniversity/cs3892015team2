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
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import pace.cs389.team2.quickfitness.adapter.CustomWorkoutsListAdapter;
import pace.cs389.team2.quickfitness.data.QuickFitnessDAO;
import pace.cs389.team2.quickfitness.model.WorkoutItem;

import static pace.cs389.team2.quickfitness.utils.OrientationUtils.isPortrait;

public class ActivityWorkoutsList extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            getFragmentManager().beginTransaction().replace(R.id.content_place_holder, new FragmentWorkouts()).commit();
        }
    }

    public static class FragmentWorkouts extends Fragment implements View.OnClickListener {


        CustomWorkoutsListAdapter mWorkoutsAdapter;
        private RecyclerView mRecyclerView;
        private QuickFitnessDAO dao;
        private TextView mListEmpty;
        private com.shamanland.fab.FloatingActionButton actionButton;

        public FragmentWorkouts() {
        }


        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == 100 && resultCode == RESULT_OK) {
                if (mWorkoutsAdapter != null) {
                    mWorkoutsAdapter.notifyDataSetChanged();
                }
                setListExercisesAdapter(dao.listWorkouts());
                Toast.makeText(getActivity().getApplicationContext(), "Workout created.", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onResume() {
            super.onResume();
            setListExercisesAdapter(dao.listWorkouts());
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


            setHasOptionsMenu(true);

            View view = inflater.inflate(R.layout.activity_list_workouts, container,
                    false);


            mListEmpty = (TextView) view.findViewById(R.id.txt_list_empty);
            mRecyclerView = (RecyclerView) view.findViewById(R.id.workout_list);
            actionButton = (com.shamanland.fab.FloatingActionButton) view.findViewById(R.id.action_button);
            mRecyclerView.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            llm.setOrientation(LinearLayoutManager.VERTICAL);

            if (isPortrait(getActivity().getResources().getConfiguration())) {
                mRecyclerView.setLayoutManager(llm);
            } else {
                ((ActionBarActivity) getActivity()).getSupportActionBar().show();
                mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            }


            dao = QuickFitnessDAO.getInstance(getActivity());

            setListExercisesAdapter(dao.listWorkouts());

            actionButton.setOnClickListener(this);

            return view;
        }

        private void setListExercisesAdapter(List<WorkoutItem> mListAdapter) {

            if (mListAdapter.isEmpty()) {
                mListEmpty.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
            } else {
                mListEmpty.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
                mWorkoutsAdapter = new CustomWorkoutsListAdapter(getActivity(), mListAdapter, mRecyclerView);
                mRecyclerView.setAdapter(mWorkoutsAdapter);
            }


        }

        @Override
        public void onClick(View v) {
            startActivityForResult(new Intent(getActivity(), ActivityAddWorkout.class), 100);
        }
    }
}


