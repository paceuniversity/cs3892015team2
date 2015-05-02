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
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import pace.cs389.team2.quickfitness.adapter.WorkoutCategoriesListAdapter;
import pace.cs389.team2.quickfitness.data.QuickFitnessDAO;
import pace.cs389.team2.quickfitness.model.WorkoutItem;
import pace.cs389.team2.quickfitness.model.WorkoutStatusItem;
import pace.cs389.team2.quickfitness.preferences.UserLoggedPreference;

public class FragmentMainContent extends Fragment {

    TextView mTextCurrentWorkout;
    CardView mCurrentWorkoutCard;
    RecyclerView mRecyclerView;
    WorkoutCategoriesListAdapter mExercisesAdapter;
    TextView mTextNoDataAvailable;

    public FragmentMainContent() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        QuickFitnessDAO dao = QuickFitnessDAO.getInstance(getActivity());

        UserLoggedPreference prefs = new UserLoggedPreference(getActivity());

        WorkoutStatusItem status = dao.statusById(getActivity().getResources().getString(R.string.workout_status_doing));

        WorkoutStatusItem statusDoing = dao.statusById(status.getStatus());

        WorkoutItem mActiveWorkout = dao.getActivityWorkout(statusDoing.getId());

        View view = inflater.inflate(R.layout.fragment_main_content, container,
                false);

        PieChart mPieChart = (PieChart) view.findViewById(R.id.piechart);

        mTextCurrentWorkout = (TextView) view.findViewById(R.id.txt_current_workout);
        mCurrentWorkoutCard = (CardView) view.findViewById(R.id.card_current_workout);
        mTextNoDataAvailable = (TextView) view.findViewById(R.id.txt_no_data_card);

        if (!prefs.isFirstTime()) {

            mPieChart.setVisibility(View.VISIBLE);

            mPieChart.addPieSlice(new PieModel("Workouts Completed", 15, Color.parseColor("#56B7F1")));
            mPieChart.addPieSlice(new PieModel("Workouts Skipped", 4, Color.parseColor("#FE6DA8")));

            if (mActiveWorkout != null) {
                mCurrentWorkoutCard.setVisibility(View.VISIBLE);
                mTextCurrentWorkout.setText(mActiveWorkout.getName());
            } else {
                mCurrentWorkoutCard.setVisibility(View.GONE);
                mTextCurrentWorkout.setText("No workouts.");
            }
        } else {
            mPieChart.setVisibility(View.GONE);
            mCurrentWorkoutCard.setVisibility(View.GONE);
            mTextNoDataAvailable.setVisibility(View.VISIBLE);
        }

        mRecyclerView = (RecyclerView) view.findViewById(R.id.cardList);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);

        mRecyclerView.setLayoutManager(llm);

        dao = QuickFitnessDAO.getInstance(getActivity());

        mExercisesAdapter = new WorkoutCategoriesListAdapter(getActivity(), dao.listExercisesCategoriesExceptFirst(), mRecyclerView);
        mRecyclerView.setAdapter(mExercisesAdapter);

        return view;

    }


}
