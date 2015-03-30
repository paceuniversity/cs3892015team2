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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Luiz on 29/03/2015.
 */
public class QuickFitnessDbHelper extends SQLiteOpenHelper {

    private static QuickFitnessDbHelper instance;
    public static final String DATABASE_NAME = "fitness.db";
    private static final int DB_VERSION = 10;

    private static final String SQL_DROP_USER_TABLE = "DROP TABLE IF EXISTS " + QuickFitnessContract.UserEntry.TABLE_NAME;
    private static final String SQL_DROP_BODY_TABLE = "DROP TABLE IF EXISTS " + QuickFitnessContract.BodyEntry.TABLE_NAME;
    private static final String SQL_DROP_CATEGORY_TABLE = "DROP TABLE IF EXISTS " + QuickFitnessContract.ExerciseCategoryEntry.TABLE_NAME;
    private static final String SQL_DROP_EXERCISE_TABLE = "DROP TABLE IF EXISTS " + QuickFitnessContract.ExerciseEntry.TABLE_NAME;
    private static final String SQL_DROP_STATUS_TABLE = "DROP TABLE IF EXISTS " + QuickFitnessContract.StatusEntry.TABLE_NAME;
    private static final String SQL_DROP_WORKOUT_SET_TABLE = "DROP TABLE IF EXISTS " + QuickFitnessContract.WorkoutSetEntry.TABLE_NAME;
    private static final String SQL_DROP_WORKOUT_EXERCISE_TABLE = "DROP TABLE IF EXISTS " + QuickFitnessContract.WorkoutExerciseEntry.TABLE_NAME;

    private static final String SQL_CREATE_USER_TABLE = "CREATE TABLE " + QuickFitnessContract.UserEntry.TABLE_NAME + "(" +
            QuickFitnessContract.UserEntry.COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            QuickFitnessContract.UserEntry.COLUMN_USER_NAME + " TEXT NOT NULL," +
            QuickFitnessContract.UserEntry.COLUMN_USER_SURNAME + " TEXT NOT NULL, " +
            QuickFitnessContract.UserEntry.COLUMN_USER_AGE + " INTEGER NOT NULL, " +
            QuickFitnessContract.UserEntry.COLUMN_USER_PICTURE + " TEXT NOT NULL);";

    private static final String SQL_CREATE_BODY_TABLE = "CREATE TABLE " + QuickFitnessContract.BodyEntry.TABLE_NAME + "(" +
            QuickFitnessContract.BodyEntry.COLUMN_BODY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            QuickFitnessContract.BodyEntry.COLUMN_BODY_HEIGHT + " INTEGER NOT NULL, " +
            QuickFitnessContract.BodyEntry.COLUMN_BODY_WEIGHT + " REAL NOT NULL, " +
            QuickFitnessContract.BodyEntry.COLUMN_BODY_BMI + " REAL NOT NULL, " +
            QuickFitnessContract.BodyEntry.COLUMN_BODY_BF + " REAL NOT NULL, " +
            QuickFitnessContract.BodyEntry.COLUMN_BODY_USER_ID_KEY + " INTEGER NOT NULL, " +
            " FOREIGN KEY (" + QuickFitnessContract.BodyEntry.COLUMN_BODY_USER_ID_KEY + ") REFERENCES " +
            QuickFitnessContract.UserEntry.TABLE_NAME + " (" + QuickFitnessContract.UserEntry.COLUMN_USER_ID + "));";


    private static final String SQL_CREATE_CATEGORY_TABLE = "CREATE TABLE " + QuickFitnessContract.ExerciseCategoryEntry.TABLE_NAME + "(" +
            QuickFitnessContract.ExerciseCategoryEntry.COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            QuickFitnessContract.ExerciseCategoryEntry.COLUMN_CATEGORY_ICON + " INTEGER, " +
            QuickFitnessContract.ExerciseCategoryEntry.COLUMN_CATEGORY_NAME + " TEXT NOT NULL);";


    private static final String SQL_CREATE_EXERCISE_TABLE = "CREATE TABLE " + QuickFitnessContract.ExerciseEntry.TABLE_NAME + "(" +
            QuickFitnessContract.ExerciseEntry.COLUMN_EXERCISE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            QuickFitnessContract.ExerciseEntry.COLUMN_EXERCISE_NAME + " TEXT NOT NULL, " +
            QuickFitnessContract.ExerciseEntry.COLUMN_EXERCISE_DESCRIPTION + " TEXT NOT NULL, " +
            QuickFitnessContract.ExerciseEntry.COLUMN_EXERCISE_SETS + " INTEGER NOT NULL, " +
            QuickFitnessContract.ExerciseEntry.COLUMN_EXERCISE_REPS + " INTEGER NOT NULL, " +
            QuickFitnessContract.ExerciseEntry.COLUMN_EXERCISE_CALORIES + " INTEGER NOT NULL, " +
            QuickFitnessContract.ExerciseEntry.COLUMN_EXERCISE_VIDEO + " TEXT NOT NULL, " +
            QuickFitnessContract.ExerciseEntry.COLUMN_EXERCISE_CATEGORY_KEY + " INTEGER NOT NULL, " +

            " FOREIGN KEY (" + QuickFitnessContract.ExerciseEntry.COLUMN_EXERCISE_CATEGORY_KEY + ") REFERENCES " +
            QuickFitnessContract.ExerciseCategoryEntry.TABLE_NAME + " (" + QuickFitnessContract.ExerciseCategoryEntry.COLUMN_CATEGORY_ID + "));";

    private static final String SQL_CREATE_STATUS_TABLE = "CREATE TABLE " + QuickFitnessContract.StatusEntry.TABLE_NAME + "(" +
            QuickFitnessContract.StatusEntry.COLUMN_STATUS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            QuickFitnessContract.StatusEntry.COLUMN_STATUS_NAME + " TEXT NOT NULL);";

    private static final String SQL_CREATE_WORKOUT_SET_TABLE = "CREATE TABLE " + QuickFitnessContract.WorkoutSetEntry.TABLE_NAME + "(" +
            QuickFitnessContract.WorkoutSetEntry.COLUMN_WORKOUT_SET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            QuickFitnessContract.WorkoutSetEntry.COLUMN_WORKOUT_SET_NAME + " TEXT NOT NULL, " +
            QuickFitnessContract.WorkoutSetEntry.COLUMN_WORKOUT_SET_TIME + " TEXT NOT NULL, " +
            QuickFitnessContract.WorkoutSetEntry.COLUMN_WORKOUT_SET_STATUS_KEY + " INTEGER NOT NULL, " +

            " FOREIGN KEY (" + QuickFitnessContract.WorkoutSetEntry.COLUMN_WORKOUT_SET_STATUS_KEY + ") REFERENCES " +
            QuickFitnessContract.StatusEntry.TABLE_NAME + " (" + QuickFitnessContract.StatusEntry.COLUMN_STATUS_ID + "));";

    private static final String SQL_CREATE_WORKOUT_EXERCISE_TABLE = "CREATE TABLE " + QuickFitnessContract.WorkoutExerciseEntry.TABLE_NAME + "(" +
            QuickFitnessContract.WorkoutExerciseEntry.COLUMN_EXERCISE_KEY + " INTEGER NOT NULL, " +
            QuickFitnessContract.WorkoutExerciseEntry.COLUMN_WORKOUT_KEY + " INTEGER NOT NULL, " +
            " FOREIGN KEY (" + QuickFitnessContract.WorkoutExerciseEntry.COLUMN_EXERCISE_KEY + ") REFERENCES " +
            QuickFitnessContract.WorkoutExerciseEntry.TABLE_NAME + " (" + QuickFitnessContract.ExerciseEntry.COLUMN_EXERCISE_ID +
            "), FOREIGN KEY (" + QuickFitnessContract.WorkoutExerciseEntry.COLUMN_WORKOUT_KEY + ") REFERENCES " +
            QuickFitnessContract.WorkoutExerciseEntry.TABLE_NAME + " (" + QuickFitnessContract.WorkoutSetEntry.COLUMN_WORKOUT_SET_ID +
            "));";


    public static QuickFitnessDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new QuickFitnessDbHelper(context.getApplicationContext());
        }

        return instance;
    }

    private QuickFitnessDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(SQL_CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_BODY_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_CATEGORY_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_EXERCISE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_STATUS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_WORKOUT_SET_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_WORKOUT_EXERCISE_TABLE);
        Log.i("DATABASE", "Database created.");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        //SQLite query to delete tables if they exist
        sqLiteDatabase.execSQL(SQL_DROP_USER_TABLE);
        sqLiteDatabase.execSQL(SQL_DROP_CATEGORY_TABLE);
        sqLiteDatabase.execSQL(SQL_DROP_EXERCISE_TABLE);
        sqLiteDatabase.execSQL(SQL_DROP_BODY_TABLE);
        sqLiteDatabase.execSQL(SQL_DROP_STATUS_TABLE);
        sqLiteDatabase.execSQL(SQL_DROP_WORKOUT_SET_TABLE);
        sqLiteDatabase.execSQL(SQL_DROP_WORKOUT_EXERCISE_TABLE);
        Log.i("DATABASE", "Database deleted.");

        //Calls the onCreate() method to create the database again
        onCreate(sqLiteDatabase);
    }
}
