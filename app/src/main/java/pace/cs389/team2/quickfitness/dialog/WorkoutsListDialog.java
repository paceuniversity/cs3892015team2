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
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pace.cs389.team2.quickfitness.data.QuickFitnessDAO;
import pace.cs389.team2.quickfitness.model.WorkoutItem;

public class WorkoutsListDialog extends DialogFragment implements
        DialogInterface.OnClickListener {

    private WorkoutItem workoutItem;
    private List<WorkoutItem> workoutItemList;
    List<String> listAdapter;
    private int mExerciseId;

    public int getmExerciseId() {
        return mExerciseId;
    }

    public void setmExerciseId(int mExerciseId) {
        this.mExerciseId = mExerciseId;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        workoutItemList = QuickFitnessDAO.getInstance(getActivity()).listWorkouts();
        listAdapter = new ArrayList<>();

        for (int i = 0; i < workoutItemList.size(); i++) {
            listAdapter.add(workoutItemList.get(i).getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, listAdapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select workout: ");

        builder.setSingleChoiceItems(adapter, -1, this);

        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        String workout = listAdapter.get(which);

        Toast.makeText(getActivity(), "Workout id: " + workout + " - Exercise id: " + getmExerciseId(), Toast.LENGTH_LONG).show();

        dialog.dismiss();
    }

}
