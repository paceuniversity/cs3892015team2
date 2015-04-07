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
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.List;

import pace.cs389.team2.quickfitness.data.QuickFitnessDAO;
import pace.cs389.team2.quickfitness.model.WorkoutExercisesItem;
import pace.cs389.team2.quickfitness.model.WorkoutItem;

public class WorkoutsListDialog extends DialogFragment implements
        DialogInterface.OnClickListener {

    WorkoutItem workoutItem;
    List<WorkoutItem> workoutItemList;
    private int mExerciseId;
    ArrayAdapter<CharSequence> adapter;
    private int mWorkoutId;

    public int getmWorkoutId() {
        return mWorkoutId;
    }

    public void setmWorkoutId(int mWorkoutId) {
        this.mWorkoutId = mWorkoutId;
    }

    public int getmExerciseId() {
        return mExerciseId;
    }

    public void setmExerciseId(int mExerciseId) {
        this.mExerciseId = mExerciseId;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        workoutItemList = QuickFitnessDAO.getInstance(getActivity()).listWorkouts();

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.select_dialog_singlechoice);

        for (int i = 0; i < workoutItemList.size(); i++) {
            adapter.add(workoutItemList.get(i).getName());
        }

        ContextThemeWrapper context = new ContextThemeWrapper(getActivity(), android.R.style.Theme_DeviceDefault_Light_DarkActionBar);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Select workout: ");

        builder.setSingleChoiceItems(adapter, -1, this);
        builder.setPositiveButton("Choose", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,
                                int whichButton) {
                WorkoutExercisesItem mItem = new WorkoutExercisesItem(getmWorkoutId(), getmExerciseId());
                QuickFitnessDAO.getInstance(getActivity()).insertExerciseWorkout(mItem);
                Toast.makeText(getActivity(), "Exercise added to workout.", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        workoutItem = new WorkoutItem((String) adapter.getItem(which));
        int mWorkoutId = QuickFitnessDAO.getInstance(getActivity()).workoutId(workoutItem).getId();

        setmWorkoutId(mWorkoutId);
    }

}
