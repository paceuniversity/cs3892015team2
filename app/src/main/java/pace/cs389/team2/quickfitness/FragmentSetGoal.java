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
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSetGoal extends Fragment implements ActionBar.OnNavigationListener {


    private ArrayAdapter<String> mSpinnerAdapter;
    private ListView mSetGoalCategories;
    private String[] mSpinnerItems = {"All Categories", "Strength", "Cardio", "Weight Loss", "Endurance"};
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

        mSpinnerAdapter = new ArrayAdapter<String>(actionBar.getThemedContext(), android.R.layout.simple_list_item_1, mSpinnerItems);
        mListAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, mListItems);
        mSetGoalCategories = (ListView) view.findViewById(R.id.set_goal_list_categories);

        actionBar.setListNavigationCallbacks(mSpinnerAdapter, this);
        setListExercisesAdapter(mListItems);

        return view;

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
        String spinnerSelectedItem = mSpinnerAdapter.getItem(itemPosition);
        Toast.makeText(getActivity(), spinnerSelectedItem, Toast.LENGTH_LONG).show();

        String[] mListItems1 = {"Pull Down", "Treadmill", "Spin"};
        String[] mListItems2 = {"Hammer Press", "Leg Press", "Shoulder Press"};

        if (spinnerSelectedItem.equals("Strength")) {
            setListExercisesAdapter(mListItems1);
        } else if (spinnerSelectedItem.equals("Cardio")) {
            setListExercisesAdapter(mListItems2);
        }

        return true;
    }

    private void setListExercisesAdapter(String[] mList) {
        mListAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, mList);
        mSetGoalCategories.setAdapter(mListAdapter);
    }
}
