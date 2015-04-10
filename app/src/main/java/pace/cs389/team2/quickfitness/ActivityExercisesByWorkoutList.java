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

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import pace.cs389.team2.quickfitness.adapter.CustomWorkoutsListAdapter;
import pace.cs389.team2.quickfitness.adapter.ExercisesByWorkoutCustomAdapter;
import pace.cs389.team2.quickfitness.data.QuickFitnessDAO;
import pace.cs389.team2.quickfitness.dialog.DeleteExerciseDialog;
import pace.cs389.team2.quickfitness.model.ExercisesItem;
import pace.cs389.team2.quickfitness.model.WorkoutExercisesItem;
import pace.cs389.team2.quickfitness.model.WorkoutItem;

public class ActivityExercisesByWorkoutList extends ActionBarActivity implements AbsListView.MultiChoiceModeListener, DeleteExerciseDialog.OnDeleteExerciseListener {

    static WorkoutItem mWorkoutItem;
    List<ExercisesItem> selectedExercises;
    private ListView mListExercises;
    private TextView mTextListEmpty;
    private ExercisesByWorkoutCustomAdapter adapter;
    private int count = 0;
    private QuickFitnessDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_exercises_by_workout);

        mListExercises = (ListView) findViewById(R.id.exercise_by_workout_list);
        mTextListEmpty = (TextView) findViewById(R.id.txt_list_empty);

        selectedExercises = new ArrayList<>();


        mWorkoutItem = (WorkoutItem) getIntent().getSerializableExtra(CustomWorkoutsListAdapter.EXERCISE_TAG);

        dao = QuickFitnessDAO.getInstance(getApplicationContext());

        setListExercisesAdapter(dao.listExercisesByWorkout(mWorkoutItem.getId()));

        mListExercises.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        mListExercises.setMultiChoiceModeListener(this);

        mListExercises.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int position, long arg3) {
                mListExercises.setItemChecked(position, !adapter.isPositionChecked(position));
                return false;
            }
        });


    }


    private void setListExercisesAdapter(List<ExercisesItem> mListAdapter) {

        adapter = new ExercisesByWorkoutCustomAdapter(this, mListAdapter);

        if (mListAdapter.isEmpty()) {
            mTextListEmpty.setVisibility(View.VISIBLE);
            mListExercises.setVisibility(View.GONE);
        } else {
            mTextListEmpty.setVisibility(View.GONE);
            mListExercises.setVisibility(View.VISIBLE);
            mListExercises.setAdapter(adapter);
        }
    }


    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        count = 0;
        getMenuInflater().inflate(R.menu.actionmode, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {
            DeleteExerciseDialog dialog = DeleteExerciseDialog.newInstance(adapter.getCurrentCheckedPosition(), this);
            dialog.show(getFragmentManager(), "deleteDialog");
            // Toast.makeText(this, "ids: " + adapter.getCurrentCheckedPosition(), Toast.LENGTH_LONG).show();
            mode.finish();
            return true;
        }

        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        adapter.clearSelection();
    }

    @Override
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

        if (checked) {
            count++;
            adapter.setNewSelection(position, true);
        } else {
            count--;
            adapter.removeSelection(position);
        }
        mode.setTitle(count + " selected");

    }


    @Override
    public void onDeleteExercise(Set<Integer> selectedItems) {

        QuickFitnessDAO dao = QuickFitnessDAO.getInstance(getApplicationContext());

        String msg = "";

        for (Integer id : selectedItems) {
            ExercisesItem itemId = (ExercisesItem) adapter.getItem(id);
            WorkoutExercisesItem workoutExercisesItem = dao.workoutExerciseById(itemId.getId());
            //dao.deleteExercise(workoutExercisesItem.getWorkoutExerciseId(), mWorkoutItem.getId());
            dao.deleteExercise(workoutExercisesItem.getWorkoutExerciseId());

            if (count == 1) {
                msg = "Item deleted.";

            } else {
                msg = count + " items deleted.";
            }

        }

        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();


        setListExercisesAdapter(dao.listExercisesByWorkout(mWorkoutItem.getId()));
        adapter.clearSelection();

        count = 0;
    }
}
