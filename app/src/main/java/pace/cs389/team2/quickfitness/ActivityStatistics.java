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

import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;


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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_statistics, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        // if (id == R.id.action_settings) {
        //   return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class StatisticsFragment extends Fragment {

        public StatisticsFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


            View rootView = inflater.inflate(R.layout.activity_statistics, container, false);
            ValueLineChart mCubicValueLineChart = (ValueLineChart) rootView.findViewById(R.id.cubiclinechart);

            ValueLineSeries series = new ValueLineSeries();
            series.setColor(0xFF56B7F1);

            series.addPoint(new ValueLinePoint("Jan", 2.4f));
            series.addPoint(new ValueLinePoint("Feb", 3.4f));
            series.addPoint(new ValueLinePoint("Mar", .4f));
            series.addPoint(new ValueLinePoint("Apr", 1.2f));
            series.addPoint(new ValueLinePoint("Mai", 2.6f));
            series.addPoint(new ValueLinePoint("Jun", 1.0f));
            series.addPoint(new ValueLinePoint("Jul", 3.5f));
            series.addPoint(new ValueLinePoint("Aug", 2.4f));
            series.addPoint(new ValueLinePoint("Sep", 2.4f));
            series.addPoint(new ValueLinePoint("Oct", 3.4f));
            series.addPoint(new ValueLinePoint("Nov", .4f));
            series.addPoint(new ValueLinePoint("Dec", 1.3f));

            mCubicValueLineChart.addSeries(series);
            mCubicValueLineChart.startAnimation();

            return rootView;
        }
    }
}
