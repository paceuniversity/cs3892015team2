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

package pace.cs389.team2.quickfitness.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimeDialogFragment extends DialogFragment implements
        OnTimeSetListener {

    private OnTimeChanged listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof OnTimeChanged)) {
            throw new IllegalArgumentException(
                    "Activity must implement the interface");
        }

        listener = (OnTimeChanged) activity;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Calendar calendar = Calendar.getInstance();

        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hourOfDay, minute,
                false);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        String time;

        if (hourOfDay < 10 && minute < 10) {

            time = "0" + hourOfDay + ":0" + minute;

        } else {
            time = hourOfDay + ":" + minute;

        }

        listener.onSetTimeChanged(time);

    }

    public interface OnTimeChanged {

        void onSetTimeChanged(String time);
    }

}
