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

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import pace.cs389.team2.quickfitness.ActivityAddWorkout;
import pace.cs389.team2.quickfitness.R;

public class NumberPickerDialog {

    private Context mContext;
    private ActivityAddWorkout activityAddWorkout;

    public NumberPickerDialog(Context mContext) {
        this.mContext = mContext;
    }

    public void buildNumberPickerDialog(String dialogTitle, String dialogDesc, int minValue, int maxValue) {

        final AlertDialog.Builder mNumberPickerDialog = new AlertDialog.Builder(mContext);
        activityAddWorkout = new ActivityAddWorkout();

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
                //activityAddWorkout.setmValue(mNumberPicker.getValue());
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


}
