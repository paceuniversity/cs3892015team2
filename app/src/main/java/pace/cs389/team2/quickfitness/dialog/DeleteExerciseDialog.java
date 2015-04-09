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

public class DeleteExerciseDialog extends DialogFragment implements OnClickListener {

    private long[] ids;
    private OnDeleteExerciseListener listener;

    public static DeleteExerciseDialog newInstance(long[] ids, OnDeleteExerciseListener listener) {
        DeleteExerciseDialog dialog = new DeleteExerciseDialog();
        dialog.ids = ids;
        dialog.listener = listener;
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Delete Exercise");

        if (ids.length == 1) {
            builder.setMessage("Delete selected exercise?");
        } else {
            builder.setMessage("Delete " + ids.length + " selected exercises?");
        }

        builder.setNegativeButton("No", this);
        builder.setPositiveButton("Yes", this);

        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE && listener != null) {
            listener.onDeleteExercise(ids);
        }
    }

    public interface OnDeleteExerciseListener {
        void onDeleteExercise(long[] ids);
    }
}
