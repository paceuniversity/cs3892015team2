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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pace.cs389.team2.quickfitness.adapter.CustomSpinnerAdapter;
import pace.cs389.team2.quickfitness.model.SpinnerNavItem;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSetGoal extends Fragment implements ActionBar.OnNavigationListener {


    private CustomSpinnerAdapter mSpinnerAdapter;
    private ArrayList<SpinnerNavItem> mSpinnerList;
    private ListView mSetGoalCategories;
    private SpinnerNavItem mNavSpinner;
    private String[] mListItems = {"Pull Down", "Treadmill", "Spin", "Hammer Press", "Leg Press", "Shoulder Press"};
    private ArrayAdapter<String> mListAdapter;

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


        View view = inflater.inflate(R.layout.fragment_set_goal, container,
                false);

        mListAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, mListItems);
        mSetGoalCategories = (ListView) view.findViewById(R.id.set_goal_list_categories);

        mSpinnerList = new ArrayList<SpinnerNavItem>();
        mSpinnerList.add(new SpinnerNavItem("All Categories"));
        mSpinnerList.add(new SpinnerNavItem("Cardio", R.drawable.circle_tag_spinner_green));
        mSpinnerList.add(new SpinnerNavItem("Endurance", R.drawable.circle_tag_spinner_orange));
        mSpinnerList.add(new SpinnerNavItem("Strength", R.drawable.circle_tag_spinner_purple));
        mSpinnerList.add(new SpinnerNavItem("Weight Loss", R.drawable.circle_tag_spinner_grey_blue));


        // title drop down adapter
        mSpinnerAdapter = new CustomSpinnerAdapter(getActivity().getApplicationContext(), mSpinnerList);

        // assigning the spinner navigation
        actionBar.setListNavigationCallbacks(mSpinnerAdapter, this);


        setListExercisesAdapter(mListItems);

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

    private void setListExercisesAdapter(String[] mList) {
        mListAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, mList);
        mSetGoalCategories.setAdapter(mListAdapter);
    }
}
