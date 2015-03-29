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


import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pace.cs389.team2.quickfitness.adapter.CustomExercisesListAdapter;
import pace.cs389.team2.quickfitness.adapter.CustomSpinnerAdapter;
import pace.cs389.team2.quickfitness.model.ExercisesItem;
import pace.cs389.team2.quickfitness.model.SpinnerNavItem;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSetGoal extends Fragment implements ActionBar.OnNavigationListener {


    private CustomSpinnerAdapter mSpinnerAdapter;
    private ArrayList<SpinnerNavItem> mSpinnerList;
    private RecyclerView mRecyclerView;
    private List<ExercisesItem> mExercisesList;
    private ExercisesItem mExercisesItem;
    private ExercisesItem mExercisesItem2;
    private ExercisesItem mExercisesItem3;
    private ExercisesItem mExercisesItem4;
    private ExercisesItem mExercisesItem5;
    private ExercisesItem mExercisesItem6;
    private ExercisesItem mExercisesItem7;
    private ExercisesItem mExercisesItem8;
    private ExercisesItem mExercisesItem9;
    private CustomExercisesListAdapter mExercisesAdapter;

    private ActionBar actionBar;

    public FragmentSetGoal() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        actionBar = getActivity().getActionBar();

        // Hide the action bar title
        actionBar.setDisplayShowTitleEnabled(false);

        // Enabling Spinner dropdown navigation
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);


        View view = inflater.inflate(R.layout.activity_list_exercises, container,
                false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.cardList);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        mSpinnerList = new ArrayList<>();
        mSpinnerList.add(new SpinnerNavItem("All Categories"));
        mSpinnerList.add(new SpinnerNavItem("Cardio", R.drawable.circle_tag_spinner_green));
        mSpinnerList.add(new SpinnerNavItem("Endurance", R.drawable.circle_tag_spinner_orange));
        mSpinnerList.add(new SpinnerNavItem("Strength", R.drawable.circle_tag_spinner_purple));
        mSpinnerList.add(new SpinnerNavItem("Weight Loss", R.drawable.circle_tag_spinner_grey_blue));


        mExercisesItem = new ExercisesItem("Pull Down", "Exercise Description", R.drawable.wall2);
        mExercisesItem2 = new ExercisesItem("Treadmill", "Exercise Description", R.drawable.wall2);
        mExercisesItem3 = new ExercisesItem("Leg Press", "Exercise Description", R.drawable.wall2);
        mExercisesItem4 = new ExercisesItem("Shoulder Press", "Exercise Description", R.drawable.wall2);
        mExercisesItem5 = new ExercisesItem("Chest Pull Down", "Exercise Description", R.drawable.wall2);
        mExercisesItem6 = new ExercisesItem("Back Extension", "Exercise Description", R.drawable.wall2);
        mExercisesItem7 = new ExercisesItem("Leg Extension", "Exercise Description", R.drawable.wall2);
        mExercisesItem8 = new ExercisesItem("Back Leg", "Exercise Description", R.drawable.wall2);
        mExercisesItem9 = new ExercisesItem("Neck Extension", "Exercise Description", R.drawable.wall2);

        mExercisesList = new ArrayList<>();
        mExercisesList.add(mExercisesItem);
        mExercisesList.add(mExercisesItem2);
        mExercisesList.add(mExercisesItem3);
        mExercisesList.add(mExercisesItem4);
        mExercisesList.add(mExercisesItem5);
        mExercisesList.add(mExercisesItem6);
        mExercisesList.add(mExercisesItem7);
        mExercisesList.add(mExercisesItem8);
        mExercisesList.add(mExercisesItem9);

        // title drop down adapter
        mSpinnerAdapter = new CustomSpinnerAdapter(getActivity().getApplicationContext(), mSpinnerList);

        // assigning the spinner navigation
        actionBar.setListNavigationCallbacks(mSpinnerAdapter, this);


        setListExercisesAdapter(mExercisesList);

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

    }

    @Override
    public void onPause() {
        super.onPause();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
    }

    /**
     * On selecting action bar icons
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    /**
     * Actionbar navigation item select listener
     */
    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {


        return false;
    }

    private void setListExercisesAdapter(List<ExercisesItem> mListAdapter) {
        mExercisesAdapter = new CustomExercisesListAdapter(mListAdapter);
        mRecyclerView.setAdapter(mExercisesAdapter);
    }
}
