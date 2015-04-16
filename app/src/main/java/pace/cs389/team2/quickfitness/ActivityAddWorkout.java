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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import pace.cs389.team2.quickfitness.data.QuickFitnessDAO;
import pace.cs389.team2.quickfitness.dialog.TimeDialogFragment;
import pace.cs389.team2.quickfitness.model.WorkoutItem;


public class ActivityAddWorkout extends ActionBarActivity implements TimeDialogFragment.OnTimeChanged {

    private static EditText edtSetName;
    private static EditText edtSetTime;
    private static EditText edtSetDaysOfWeek;
    private static EditText edtSetWorkoutDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    public void promptSetTimeDialog(View view) {
        TimeDialogFragment timeDialogFragment = new TimeDialogFragment();
        timeDialogFragment.show(getFragmentManager(), "set_time_fragment");
    }

    public void promptSetDaysDialog(View view) {

        buildNumberPickerDialog("Number of days", "Set number of days", 1, 7);
    }

    public void promptSetDurationDialog(View view) {

        buildNumberPickerDialog("Workout duration", "Set workout duration.", 10, 45);
    }

    public void buildNumberPickerDialog(String dialogTitle, String dialogDesc, int minValue, int maxValue) {

        final AlertDialog.Builder mNumberPickerDialog = new AlertDialog.Builder(this);

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        View mView = inflater.inflate(R.layout.numberpicker_dialog_adapter, null);

        mNumberPickerDialog.setTitle(dialogTitle);

        mNumberPickerDialog.setView(mView);

        TextView mTextDialogDesc = (TextView) mView.findViewById(R.id.txt_number_picker_desc);
        final NumberPicker mNumberPicker = (NumberPicker) mView.findViewById(R.id.numberPicker);

        mTextDialogDesc.setText(dialogDesc);

        mNumberPicker.setMaxValue(maxValue);
        mNumberPicker.setMinValue(minValue);
        mNumberPicker.setWrapSelectorWheel(false);

        mNumberPickerDialog.setPositiveButton("Set", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mNumberPicker.getMinValue() == 1 && mNumberPicker.getMaxValue() == 7) {
                    edtSetDaysOfWeek.setText(String.valueOf(mNumberPicker.getValue()));

                } else {
                    edtSetWorkoutDuration.setText(String.valueOf(mNumberPicker.getValue()));
                }

                dialog.dismiss();
            }
        });

        mNumberPickerDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        mNumberPickerDialog.show();
    }


    @Override
    public void onSetTimeChanged(String time) {
        edtSetTime.setText(time);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {


        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            super.onCreateOptionsMenu(menu, inflater);
            inflater.inflate(R.menu.save_workout_menu, menu);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            if (id == R.id.action_save_workout) {

                if (TextUtils.isEmpty(edtSetName.getText())) {
                    edtSetName.setError("Please, type workout name.");
                } else if (TextUtils.isEmpty(edtSetTime.getText())) {
                    edtSetTime.setError("Please, set workout time.");
                } else if (TextUtils.isEmpty(edtSetTime.getText())) {
                    edtSetDaysOfWeek.setError("Please, set week days.");
                } else if (TextUtils.isEmpty(edtSetTime.getText())) {
                    edtSetWorkoutDuration.setError("Please, set workout duration.");
                } else {
                    WorkoutItem workoutItem = new WorkoutItem(edtSetName.getText().toString(), Integer.parseInt(edtSetDaysOfWeek.getText().toString()), Integer.parseInt(edtSetWorkoutDuration.getText().toString()), edtSetTime.getText().toString(), 1);
                    QuickFitnessDAO.getInstance(getActivity().getApplicationContext()).insertWorkoutSet(workoutItem);

                    getActivity().setResult(RESULT_OK);
                    getActivity().finish();
                    return true;

                }
            }

            return false;
        }


        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            setHasOptionsMenu(true);

            View rootView = inflater.inflate(R.layout.fragment_add_workout, container, false);
            edtSetName = (EditText) rootView.findViewById(R.id.edt_set_workout_name);
            edtSetTime = (EditText) rootView.findViewById(R.id.edt_set_workout_time);
            edtSetDaysOfWeek = (EditText) rootView.findViewById(R.id.edt_set_workout_days);
            edtSetWorkoutDuration = (EditText) rootView.findViewById(R.id.edt_set_workout_duration);

            edtSetTime.setFocusable(false);
            edtSetDaysOfWeek.setFocusable(false);
            edtSetWorkoutDuration.setFocusable(false);

            return rootView;
        }


    }

}
