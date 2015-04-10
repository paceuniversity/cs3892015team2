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
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

import java.util.Set;

public class DeleteExerciseDialog extends DialogFragment implements OnClickListener {

    // private long[] ids;
    private OnDeleteExerciseListener listener;
    private Set<Integer> selectedItems;

    public static DeleteExerciseDialog newInstance(Set<Integer> selectedItems, OnDeleteExerciseListener listener) {
        DeleteExerciseDialog dialog = new DeleteExerciseDialog();
        dialog.selectedItems = selectedItems;
        dialog.listener = listener;
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Delete Exercise");

        if (selectedItems.size() == 1) {
            builder.setMessage("Delete selected exercise?");
        } else {
            builder.setMessage("Delete " + selectedItems.size() + " selected exercises?");
        }

        builder.setNegativeButton("No", this);
        builder.setPositiveButton("Yes", this);

        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE && listener != null) {
            listener.onDeleteExercise(selectedItems);
        }
    }

    public interface OnDeleteExerciseListener {
        void onDeleteExercise(Set<Integer> selectedItems);
    }
}
