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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import pace.cs389.team2.quickfitness.adapter.CustomExercisesListAdapter;
import pace.cs389.team2.quickfitness.adapter.CustomSpinnerAdapter;
import pace.cs389.team2.quickfitness.data.QuickFitnessDAO;
import pace.cs389.team2.quickfitness.model.ExercisesItem;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSetGoal extends Fragment implements ActionBar.OnNavigationListener {


    CustomSpinnerAdapter mSpinnerAdapter;
    private RecyclerView mRecyclerView;
    private QuickFitnessDAO dao;
    CustomExercisesListAdapter mExercisesAdapter;

    private ActionBar actionBar;

    public FragmentSetGoal() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        actionBar = getActivity().getActionBar();

        if (actionBar != null) {
            // Hide the action bar title
            actionBar.setDisplayShowTitleEnabled(false);

            // Enabling Spinner dropdown navigation
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        }

        View view = inflater.inflate(R.layout.activity_list_exercises, container,
                false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.cardList);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        dao = QuickFitnessDAO.getInstance(getActivity());

        // title drop down adapter
        mSpinnerAdapter = new CustomSpinnerAdapter(getActivity().getApplicationContext(), dao.listExercisesCategories());

        // assigning the spinner navigation
        actionBar.setListNavigationCallbacks(mSpinnerAdapter, this);

        Toast.makeText(getActivity(), "Size: " + dao.listExercises().size(), Toast.LENGTH_LONG).show();
        setListExercisesAdapter(dao.listExercises());

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();

        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        }
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
        int pos = itemPosition + 1;

        if (pos > 0 && pos <= 5) {
            if ((pos) == 1) {
                setListExercisesAdapter(dao.listExercises());
            } else {
                setListExercisesAdapter(dao.listExercisesById(pos));
            }

        } else {
            Log.i(MainActivity.APP_TAG, "Invalid Option.");
        }

        return true;
    }

    private void setListExercisesAdapter(List<ExercisesItem> mListAdapter) {
        mExercisesAdapter = new CustomExercisesListAdapter(mListAdapter);
        mRecyclerView.setAdapter(mExercisesAdapter);
    }


}
