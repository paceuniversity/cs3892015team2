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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pace.cs389.team2.quickfitness.adapter.CustomWorkoutsListAdapter;
import pace.cs389.team2.quickfitness.adapter.ExercisesByWorkoutCustomAdapter;
import pace.cs389.team2.quickfitness.data.QuickFitnessDAO;
import pace.cs389.team2.quickfitness.dialog.DeleteExerciseDialog;
import pace.cs389.team2.quickfitness.model.ExercisesItem;
import pace.cs389.team2.quickfitness.model.WorkoutItem;

public class ActivityExercisesByWorkoutList extends ActionBarActivity implements AbsListView.MultiChoiceModeListener, DeleteExerciseDialog.OnDeleteExerciseListener {

    static WorkoutItem mWorkoutItem;
    List<Integer> selectedExercises;
    private ListView mListExercises;
    private TextView mTextListEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_exercises_by_workout);

        mListExercises = (ListView) findViewById(R.id.exercise_by_workout_list);
        mTextListEmpty = (TextView) findViewById(R.id.txt_list_empty);

        selectedExercises = new ArrayList<>();

        mWorkoutItem = (WorkoutItem) getIntent().getSerializableExtra(CustomWorkoutsListAdapter.EXERCISE_TAG);

        QuickFitnessDAO dao = QuickFitnessDAO.getInstance(getApplicationContext());

        setListExercisesAdapter(dao.listExercisesByWorkout(mWorkoutItem.getId()));

        mListExercises.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        mListExercises.setMultiChoiceModeListener(this);


    }


    private void setListExercisesAdapter(List<ExercisesItem> mListAdapter) {

        if (mListAdapter.isEmpty()) {
            mTextListEmpty.setVisibility(View.VISIBLE);
            mListExercises.setVisibility(View.GONE);
        } else {
            mTextListEmpty.setVisibility(View.GONE);
            mListExercises.setVisibility(View.VISIBLE);
            mListExercises.setAdapter(new ExercisesByWorkoutCustomAdapter(this, mListAdapter));

        }
    }


    @Override
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
        if (checked) {
            selectedExercises.add(position);
        } else {
            selectedExercises.remove((Integer) position);
        }
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
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
            DeleteExerciseDialog dialog = DeleteExerciseDialog.newInstance(mListExercises.getCheckedItemIds(), this);
            dialog.show(getFragmentManager(), "deleteDialog");
            return true;
        }

        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        unselectItems();
    }

    private void unselectItems() {
        for (int i = 0; i < mListExercises.getCount(); i++) {
            mListExercises.setItemChecked(i, false);
        }
    }

    @Override
    public void onDeleteExercise(long[] ids) {

        QuickFitnessDAO dao = QuickFitnessDAO.getInstance(getApplicationContext());

        for (long id : ids) {
            dao.deleteExercise(id);
        }
        unselectItems();
    }
}
