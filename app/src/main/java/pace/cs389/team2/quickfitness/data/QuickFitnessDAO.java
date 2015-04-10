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

package pace.cs389.team2.quickfitness.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import pace.cs389.team2.quickfitness.MainActivity;
import pace.cs389.team2.quickfitness.model.CategoryItem;
import pace.cs389.team2.quickfitness.model.ExercisesItem;
import pace.cs389.team2.quickfitness.model.WorkoutExercisesItem;
import pace.cs389.team2.quickfitness.model.WorkoutItem;
import pace.cs389.team2.quickfitness.model.WorkoutStatusItem;

public class QuickFitnessDAO {

    private static final String[] PROJECTION = {QuickFitnessContract.ExerciseCategoryEntry.COLUMN_CATEGORY_ID,
            QuickFitnessContract.ExerciseCategoryEntry.COLUMN_CATEGORY_NAME,
            QuickFitnessContract.ExerciseCategoryEntry.COLUMN_CATEGORY_ICON
    };
    private static final String[] PROJECTION_TABLE_STATUS = {QuickFitnessContract.StatusEntry.COLUMN_STATUS_ID,
            QuickFitnessContract.StatusEntry.COLUMN_STATUS_NAME
    };
    private static final String[] PROJECTION_TABLE_WORKOUT_X_EXERCISES = {
            QuickFitnessContract.WorkoutExerciseEntry.COLUMN_WORKOUT_EXERCISE_ID,
            QuickFitnessContract.WorkoutExerciseEntry.COLUMN_WORKOUT_KEY,
            QuickFitnessContract.WorkoutExerciseEntry.COLUMN_EXERCISE_KEY
    };
    private static final String[] PROJECTION_TABLE_WORKOUT = {QuickFitnessContract.WorkoutSetEntry.COLUMN_WORKOUT_SET_ID,
            QuickFitnessContract.WorkoutSetEntry.COLUMN_WORKOUT_SET_NAME,
            QuickFitnessContract.WorkoutSetEntry.COLUMN_WORKOUT_DAYS_OF_WEEK,
            QuickFitnessContract.WorkoutSetEntry.COLUMN_WORKOUT_DURATION,
            QuickFitnessContract.WorkoutSetEntry.COLUMN_WORKOUT_SET_TIME,
            QuickFitnessContract.WorkoutSetEntry.COLUMN_WORKOUT_SET_STATUS_KEY
    };
    private static final String[] PROJECTION_TABLE_EXERCISE = {QuickFitnessContract.ExerciseEntry.COLUMN_EXERCISE_ID,
            QuickFitnessContract.ExerciseEntry.COLUMN_EXERCISE_NAME,
            QuickFitnessContract.ExerciseEntry.COLUMN_EXERCISE_DESCRIPTION,
            QuickFitnessContract.ExerciseEntry.COLUMN_EXERCISE_ICON,
            QuickFitnessContract.ExerciseEntry.COLUMN_EXERCISE_LEVEL,
            QuickFitnessContract.ExerciseEntry.COLUMN_EXERCISE_DURATION,
            QuickFitnessContract.ExerciseEntry.COLUMN_EXERCISE_CALORIES,
            QuickFitnessContract.ExerciseEntry.COLUMN_EXERCISE_VIDEO,
            QuickFitnessContract.ExerciseEntry.COLUMN_EXERCISE_CATEGORY_KEY
    };
    private static QuickFitnessDAO instance;
    private static SQLiteDatabase sqLiteDatabase;
    QuickFitnessDbHelper helper;

    private QuickFitnessDAO(Context context) {
        helper = QuickFitnessDbHelper.getInstance(context);
        sqLiteDatabase = helper.getWritableDatabase();
    }

    public static QuickFitnessDAO getInstance(Context mContext) {

        if (instance == null) {
            instance = new QuickFitnessDAO(mContext);
        }

        return instance;

    }

    public void deleteExercise(long exerciseId) {

        // String sqlDelete = "DELETE FROM " + QuickFitnessContract.WorkoutExerciseEntry.TABLE_NAME + " WHERE " + QuickFitnessContract.WorkoutExerciseEntry.COLUMN_WORKOUT_EXERCISE_ID + " = " + exerciseId + " AND " + QuickFitnessContract.WorkoutExerciseEntry.COLUMN_WORKOUT_KEY + " = " + workoutId;

        // sqLiteDatabase.rawQuery(sqlDelete, null);
        sqLiteDatabase.delete(QuickFitnessContract.WorkoutExerciseEntry.TABLE_NAME, QuickFitnessContract.WorkoutExerciseEntry.COLUMN_WORKOUT_EXERCISE_ID + " = ?", new String[]{String.valueOf(exerciseId)});
    }

    public void insertWorkoutSet(WorkoutItem mItemWorkout) {
        ContentValues values = new ContentValues();

        values.put(QuickFitnessContract.WorkoutSetEntry.COLUMN_WORKOUT_SET_NAME, mItemWorkout.getName());
        values.put(QuickFitnessContract.WorkoutSetEntry.COLUMN_WORKOUT_DAYS_OF_WEEK, mItemWorkout.getWorkoutDays());
        values.put(QuickFitnessContract.WorkoutSetEntry.COLUMN_WORKOUT_DURATION, mItemWorkout.getWorkoutDuration());
        values.put(QuickFitnessContract.WorkoutSetEntry.COLUMN_WORKOUT_SET_TIME, mItemWorkout.getTime());
        values.put(QuickFitnessContract.WorkoutSetEntry.COLUMN_WORKOUT_SET_STATUS_KEY, mItemWorkout.getStatusKey());

        sqLiteDatabase.insert(QuickFitnessContract.WorkoutSetEntry.TABLE_NAME, null, values);
    }

    public List<CategoryItem> listExercisesCategories() {
        List<CategoryItem> categories = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.query(QuickFitnessContract.ExerciseCategoryEntry.TABLE_NAME, PROJECTION,
                null, null, null, null, null);

        CategoryItem itemCategory;
        try {
            if (cursor.moveToFirst()) {
                do {
                    itemCategory = new CategoryItem(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
                    categories.add(itemCategory);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteDatabaseLockedException exception) {
            Log.e(MainActivity.APP_TAG, exception.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }


        return categories;
    }

    public CategoryItem categoryById(int id) {

        Cursor cursor = sqLiteDatabase.query(QuickFitnessContract.ExerciseCategoryEntry.TABLE_NAME, PROJECTION,
                QuickFitnessContract.ExerciseCategoryEntry.COLUMN_CATEGORY_ID + " = ?", new String[]{String.valueOf(id)}, null, null, QuickFitnessContract.ExerciseCategoryEntry.COLUMN_CATEGORY_NAME);

        CategoryItem itemCategory = null;

        try {
            if (cursor.moveToFirst()) {
                do {
                    itemCategory = new CategoryItem(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
                } while (cursor.moveToNext());
            }
        } catch (SQLiteDatabaseLockedException exception) {
            Log.e(MainActivity.APP_TAG, exception.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }


        return itemCategory;
    }

    public WorkoutExercisesItem workoutExerciseById(int id) {

        Cursor cursor = sqLiteDatabase.query(QuickFitnessContract.WorkoutExerciseEntry.TABLE_NAME, PROJECTION_TABLE_WORKOUT_X_EXERCISES,
                QuickFitnessContract.WorkoutExerciseEntry.COLUMN_EXERCISE_KEY + " = ?", new String[]{String.valueOf(id)}, null, null, null);

        WorkoutExercisesItem workoutExercisesItem = null;

        try {
            if (cursor.moveToFirst()) {
                do {
                    workoutExercisesItem = new WorkoutExercisesItem(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2));
                } while (cursor.moveToNext());
            }
        } catch (SQLiteDatabaseLockedException exception) {
            Log.e(MainActivity.APP_TAG, exception.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }


        return workoutExercisesItem;
    }

    public List<ExercisesItem> listExercisesById(int id) {
        List<ExercisesItem> exercises = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.query(QuickFitnessContract.ExerciseEntry.TABLE_NAME, PROJECTION_TABLE_EXERCISE,
                QuickFitnessContract.ExerciseEntry.COLUMN_EXERCISE_CATEGORY_KEY + " = ?", new String[]{String.valueOf(id)}, null, null, QuickFitnessContract.ExerciseEntry.COLUMN_EXERCISE_NAME);

        ExercisesItem itemExercise;
        try {
            if (cursor.moveToFirst()) {
                do {
                    itemExercise = new ExercisesItem(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getString(4), cursor.getInt(5), cursor.getInt(6), cursor.getString(7), cursor.getInt(8));
                    exercises.add(itemExercise);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteDatabaseLockedException exception) {
            Log.e(MainActivity.APP_TAG, exception.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return exercises;
    }

    public List<ExercisesItem> listExercises() {
        List<ExercisesItem> exercises = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.query(QuickFitnessContract.ExerciseEntry.TABLE_NAME, PROJECTION_TABLE_EXERCISE,
                null, null, null, null, QuickFitnessContract.ExerciseEntry.COLUMN_EXERCISE_NAME);
        ExercisesItem itemExercise;

        try {
            if (cursor.moveToFirst()) {
                do {
                    itemExercise = new ExercisesItem(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getString(4), cursor.getInt(5), cursor.getInt(6), cursor.getString(7), cursor.getInt(8));
                    exercises.add(itemExercise);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteDatabaseLockedException exception) {
            Log.e(MainActivity.APP_TAG, exception.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return exercises;
    }

    public List<ExercisesItem> listExercisesByWorkout(int workoutId) {
        List<ExercisesItem> exercises = new ArrayList<>();

        String sqlQuery = "SELECT * FROM " + QuickFitnessContract.ExerciseEntry.TABLE_NAME + " , " + QuickFitnessContract.WorkoutExerciseEntry.TABLE_NAME + " WHERE " + QuickFitnessContract.ExerciseEntry.COLUMN_EXERCISE_ID + " = " + QuickFitnessContract.WorkoutExerciseEntry.COLUMN_EXERCISE_KEY + " AND " + QuickFitnessContract.WorkoutExerciseEntry.COLUMN_WORKOUT_KEY + " = " + workoutId;

        Cursor cursor = sqLiteDatabase.rawQuery(sqlQuery, null);

        ExercisesItem itemExercise;

        try {
            if (cursor.moveToFirst()) {
                do {
                    itemExercise = new ExercisesItem(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getString(4), cursor.getInt(5), cursor.getInt(6), cursor.getString(7), cursor.getInt(8));
                    exercises.add(itemExercise);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteDatabaseLockedException exception) {
            Log.e(MainActivity.APP_TAG, exception.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return exercises;
    }

    public WorkoutItem workoutId(WorkoutItem workoutItem) {

        Cursor cursor = sqLiteDatabase.query(QuickFitnessContract.WorkoutSetEntry.TABLE_NAME, PROJECTION_TABLE_WORKOUT,
                QuickFitnessContract.WorkoutSetEntry.COLUMN_WORKOUT_SET_NAME + " = ?", new String[]{workoutItem.getName()}, null, null, QuickFitnessContract.WorkoutSetEntry.COLUMN_WORKOUT_SET_NAME);

        WorkoutItem itemWorkout = null;

        try {
            if (cursor.moveToFirst()) {
                do {
                    itemWorkout = new WorkoutItem(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getString(4), cursor.getInt(5));
                } while (cursor.moveToNext());
            }
        } catch (SQLiteDatabaseLockedException exception) {
            Log.e(MainActivity.APP_TAG, exception.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return itemWorkout;
    }

    public List<WorkoutItem> listWorkouts() {
        List<WorkoutItem> workouts = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.query(QuickFitnessContract.WorkoutSetEntry.TABLE_NAME, PROJECTION_TABLE_WORKOUT,
                null, null, null, null, QuickFitnessContract.WorkoutSetEntry.COLUMN_WORKOUT_SET_NAME);
        WorkoutItem itemWorkout;

        try {
            if (cursor.moveToFirst()) {
                do {
                    itemWorkout = new WorkoutItem(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getString(4), cursor.getInt(5));
                    workouts.add(itemWorkout);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteDatabaseLockedException exception) {
            Log.e(MainActivity.APP_TAG, exception.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return workouts;
    }

    public WorkoutStatusItem statusById(int id) {

        Cursor cursor = sqLiteDatabase.query(QuickFitnessContract.StatusEntry.TABLE_NAME, PROJECTION_TABLE_STATUS,
                QuickFitnessContract.StatusEntry.COLUMN_STATUS_ID + " = ?", new String[]{String.valueOf(id)}, null, null, QuickFitnessContract.StatusEntry.COLUMN_STATUS_NAME);

        WorkoutStatusItem itemStatus = null;

        try {
            if (cursor.moveToFirst()) {
                do {
                    itemStatus = new WorkoutStatusItem(cursor.getInt(0), cursor.getString(1));
                } while (cursor.moveToNext());
            }
        } catch (SQLiteDatabaseLockedException exception) {
            Log.e(MainActivity.APP_TAG, exception.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return itemStatus;
    }

    public void insertExerciseWorkout(WorkoutExercisesItem workoutExercisesItem) {
        ContentValues values = new ContentValues();

        values.put(QuickFitnessContract.WorkoutExerciseEntry.COLUMN_WORKOUT_KEY, workoutExercisesItem.getWorkoutId());
        values.put(QuickFitnessContract.WorkoutExerciseEntry.COLUMN_EXERCISE_KEY, workoutExercisesItem.getExerciseId());

        sqLiteDatabase.insert(QuickFitnessContract.WorkoutExerciseEntry.TABLE_NAME, null, values);
    }


    public List<WorkoutExercisesItem> listExercisesByWorkout() {
        List<WorkoutExercisesItem> workouts = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.query(QuickFitnessContract.WorkoutExerciseEntry.TABLE_NAME, PROJECTION_TABLE_WORKOUT_X_EXERCISES,
                null, null, null, null, null);
        WorkoutExercisesItem workoutExercisesItem;

        try {
            if (cursor.moveToFirst()) {
                do {
                    workoutExercisesItem = new WorkoutExercisesItem(cursor.getInt(0), cursor.getInt(1));
                    workouts.add(workoutExercisesItem);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteDatabaseLockedException exception) {
            Log.e(MainActivity.APP_TAG, exception.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return workouts;
    }

}
