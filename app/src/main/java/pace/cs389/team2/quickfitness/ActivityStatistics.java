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
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.charts.VerticalBarChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;
import java.util.List;

import pace.cs389.team2.quickfitness.data.QuickFitnessDAO;
import pace.cs389.team2.quickfitness.model.BodyInfoItem;
import pace.cs389.team2.quickfitness.model.ExercisesItem;
import pace.cs389.team2.quickfitness.model.WorkoutItem;
import pace.cs389.team2.quickfitness.preferences.UserLoggedPreference;


public class ActivityStatistics extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new StatisticsFragment())
                    .commit();
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class StatisticsFragment extends Fragment implements AdapterView.OnItemSelectedListener {

        UserLoggedPreference prefs;
        QuickFitnessDAO dao;
        BarChart mNormalBarChart;
        VerticalBarChart mVerticalBarChart;
        TextView txtNoData;
        LinearLayout mStatisticsLayout;
        List<WorkoutItem> workoutList;
        List<BodyInfoItem> bodyInfoList;
        List<Integer> colorList;
        LinearLayout mVerticalBarChartLayout;

        public StatisticsFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.activity_statistics, container, false);
            mNormalBarChart = (BarChart) rootView.findViewById(R.id.chart_bar_chart);
            PieChart mPieChart = (PieChart) rootView.findViewById(R.id.chart_pie_chart);
            mVerticalBarChart = (VerticalBarChart) rootView.findViewById(R.id.chart_vertical_bar_chart);
            Spinner spnFilter = (Spinner) rootView.findViewById(R.id.spn_filter_chart);
            txtNoData = (TextView) rootView.findViewById(R.id.txt_chart_no_data);
            mStatisticsLayout = (LinearLayout) rootView.findViewById(R.id.layout_statistics);
            mVerticalBarChartLayout = (LinearLayout) rootView.findViewById(R.id.layout_vertical_bar_chart);

            prefs = new UserLoggedPreference(getActivity());
            dao = QuickFitnessDAO.getInstance(getActivity());

            workoutList = dao.listWorkouts();
            bodyInfoList = dao.listBodyInfo();

            colorList = new ArrayList<>();
            colorList.add(0xFF123456);
            colorList.add(0xFF873F56);
            colorList.add(0xFF56B7F1);
            colorList.add(0xFF343456);
            colorList.add(0xFF1FF4AC);
            colorList.add(0xFF1BA4E6);


            List<ExercisesItem> listExercises;
            List<Integer> exercisesCounter = new ArrayList<>();

            for (int i = 0; i < workoutList.size(); i++) {
                listExercises = dao.listExercisesByWorkout(workoutList.get(i).getId());
                if (listExercises.size() > 0) {
                    exercisesCounter.add(listExercises.size());

                }
            }

            spnFilter.setOnItemSelectedListener(this);

            String stringSeparator = "       ";

            if (exercisesCounter.size() > 0) {
                mVerticalBarChartLayout.setVisibility(View.VISIBLE);

                for (int j = 0; j < exercisesCounter.size(); j++) {
                    int colorListSize = j % colorList.size();
                    mVerticalBarChart.addBar(new BarModel((float) exercisesCounter.get(j), colorList.get(colorListSize)));
                    mVerticalBarChart.setValueUnit(stringSeparator + workoutList.get(j).getName());
                    mVerticalBarChart.startAnimation();
                }
            } else {
                mVerticalBarChartLayout.setVisibility(View.GONE);
            }

            mPieChart.addPieSlice(new PieModel("Workouts Completed", 15, colorList.get(0)));
            mPieChart.addPieSlice(new PieModel("Workouts Skipped", 4, colorList.get(1)));

            return rootView;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            if (workoutList.size() > 0) {
                txtNoData.setVisibility(View.GONE);
                mStatisticsLayout.setVisibility(View.VISIBLE);

                if (position >= 0 && position <= 2) {
                    if (position == 0) {
                        mNormalBarChart.clearChart();
                        for (int i = 0; i < workoutList.size(); i++) {
                            int colorListSize = i % colorList.size();
                            mNormalBarChart.addBar(new BarModel((float) bodyInfoList.get(i).getWeight(), colorList.get(colorListSize)));
                        }
                    } else if (position == 1) {
                        mNormalBarChart.clearChart();
                        for (int i = 0; i < workoutList.size(); i++) {
                            int colorListSize = i % colorList.size();
                            mNormalBarChart.addBar(new BarModel((float) bodyInfoList.get(i).getBf(), colorList.get(colorListSize)));
                        }

                        mNormalBarChart.startAnimation();
                    } else if (position == 2) {
                        mNormalBarChart.clearChart();
                        for (int i = 0; i < workoutList.size(); i++) {
                            int colorListSize = i % colorList.size();
                            mNormalBarChart.addBar(new BarModel((float) bodyInfoList.get(i).getBmi(), colorList.get(colorListSize)));
                        }
                    }
                    mNormalBarChart.startAnimation();
                }

            } else {
                txtNoData.setVisibility(View.VISIBLE);
                mStatisticsLayout.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "You haven't created any workout.", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
