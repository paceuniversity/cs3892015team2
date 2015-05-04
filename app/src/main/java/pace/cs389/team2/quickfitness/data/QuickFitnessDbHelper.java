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
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import pace.cs389.team2.quickfitness.MainActivity;
import pace.cs389.team2.quickfitness.R;
import pace.cs389.team2.quickfitness.model.CategoryItem;
import pace.cs389.team2.quickfitness.model.ExercisesItem;
import pace.cs389.team2.quickfitness.model.WorkoutStatusItem;

public class QuickFitnessDbHelper extends SQLiteOpenHelper {

    //Database name
    public static final String DATABASE_NAME = "fitness.db";

    //Database version. If database has been changed, increment version in 1 to recreate database.
    private static final int DB_VERSION = 95;

    // SQL script to drop the entire database
    //private static final String SQL_DROP_DATABASE = "DROP DATABASE IF EXISTS " + DATABASE_NAME;

    /*SQL scripts to delete database tables*/
    private static final String SQL_DROP_USER_TABLE = "DROP TABLE IF EXISTS " + QuickFitnessContract.UserEntry.TABLE_NAME;
    private static final String SQL_DROP_BODY_TABLE = "DROP TABLE IF EXISTS " + QuickFitnessContract.BodyEntry.TABLE_NAME;
    private static final String SQL_DROP_CATEGORY_TABLE = "DROP TABLE IF EXISTS " + QuickFitnessContract.ExerciseCategoryEntry.TABLE_NAME;
    private static final String SQL_DROP_EXERCISE_TABLE = "DROP TABLE IF EXISTS " + QuickFitnessContract.ExerciseEntry.TABLE_NAME;
    private static final String SQL_DROP_STATUS_TABLE = "DROP TABLE IF EXISTS " + QuickFitnessContract.StatusEntry.TABLE_NAME;
    private static final String SQL_DROP_WORKOUT_SET_TABLE = "DROP TABLE IF EXISTS " + QuickFitnessContract.WorkoutSetEntry.TABLE_NAME;
    private static final String SQL_DROP_WORKOUT_EXERCISE_TABLE = "DROP TABLE IF EXISTS " + QuickFitnessContract.WorkoutExerciseEntry.TABLE_NAME;

    /*SQL script to create table 'user' on the database*/
    private static final String SQL_CREATE_USER_TABLE = "CREATE TABLE " + QuickFitnessContract.UserEntry.TABLE_NAME + "(" +
            QuickFitnessContract.UserEntry.COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            QuickFitnessContract.UserEntry.COLUMN_USER_NAME + " TEXT NOT NULL," +
            QuickFitnessContract.UserEntry.COLUMN_USER_EMAIL + " TEXT NOT NULL, " +
            QuickFitnessContract.UserEntry.COLUMN_USER_AGE + " INTEGER NOT NULL, " +
            QuickFitnessContract.UserEntry.COLUMN_USER_PASSWORD + " TEXT NOT NULL, " +
            QuickFitnessContract.UserEntry.COLUMN_USER_GENRE + " INTEGER NOT NULL, " +
            QuickFitnessContract.UserEntry.COLUMN_USER_PICTURE + " TEXT);";

    /*SQL script to create table 'body' on the database. This table contains a foreign key linked to table 'user' */
    private static final String SQL_CREATE_BODY_TABLE = "CREATE TABLE " + QuickFitnessContract.BodyEntry.TABLE_NAME + "(" +
            QuickFitnessContract.BodyEntry.COLUMN_BODY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            QuickFitnessContract.BodyEntry.COLUMN_BODY_HEIGHT + " INTEGER NOT NULL, " +
            QuickFitnessContract.BodyEntry.COLUMN_BODY_WEIGHT + " REAL NOT NULL, " +
            QuickFitnessContract.BodyEntry.COLUMN_BODY_BMI + " REAL NOT NULL, " +
            QuickFitnessContract.BodyEntry.COLUMN_BODY_BF + " REAL NOT NULL, " +
            QuickFitnessContract.BodyEntry.COLUMN_BODY_USER_ID_KEY + " INTEGER NOT NULL, " +
            QuickFitnessContract.BodyEntry.COLUMN_BODY_WORKOUT_ID_KEY + " INTEGER NOT NULL, " +

            " FOREIGN KEY (" + QuickFitnessContract.BodyEntry.COLUMN_BODY_USER_ID_KEY + ") REFERENCES " +
            QuickFitnessContract.UserEntry.TABLE_NAME + " (" + QuickFitnessContract.UserEntry.COLUMN_USER_ID +
            "), FOREIGN KEY (" + QuickFitnessContract.BodyEntry.COLUMN_BODY_WORKOUT_ID_KEY + ") REFERENCES " +
            QuickFitnessContract.WorkoutSetEntry.TABLE_NAME + " (" + QuickFitnessContract.WorkoutSetEntry.COLUMN_WORKOUT_SET_ID +
            "));";


    /*SQL script to create table 'exercise_category' on the database*/
    private static final String SQL_CREATE_CATEGORY_TABLE = "CREATE TABLE " + QuickFitnessContract.ExerciseCategoryEntry.TABLE_NAME + "(" +
            QuickFitnessContract.ExerciseCategoryEntry.COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            QuickFitnessContract.ExerciseCategoryEntry.COLUMN_CATEGORY_ICON + " INTEGER, " +
            QuickFitnessContract.ExerciseCategoryEntry.COLUMN_CATEGORY_NAME + " TEXT NOT NULL);";

    /*SQL script to create table 'exercise' on the database. This table contains a foreign key linked to 'exercise_category' table*/
    private static final String SQL_CREATE_EXERCISE_TABLE = "CREATE TABLE " + QuickFitnessContract.ExerciseEntry.TABLE_NAME + "(" +
            QuickFitnessContract.ExerciseEntry.COLUMN_EXERCISE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            QuickFitnessContract.ExerciseEntry.COLUMN_EXERCISE_NAME + " TEXT NOT NULL, " +
            QuickFitnessContract.ExerciseEntry.COLUMN_EXERCISE_DESCRIPTION + " TEXT NOT NULL, " +
            QuickFitnessContract.ExerciseEntry.COLUMN_EXERCISE_ICON + " INTEGER NOT NULL, " +
            QuickFitnessContract.ExerciseEntry.COLUMN_EXERCISE_LEVEL + " TEXT NOT NULL, " +
            QuickFitnessContract.ExerciseEntry.COLUMN_EXERCISE_DURATION + " INTEGER NOT NULL, " +
            QuickFitnessContract.ExerciseEntry.COLUMN_EXERCISE_CALORIES + " INTEGER NOT NULL, " +
            QuickFitnessContract.ExerciseEntry.COLUMN_EXERCISE_VIDEO + " TEXT NOT NULL, " +
            QuickFitnessContract.ExerciseEntry.COLUMN_EXERCISE_CATEGORY_KEY + " INTEGER NOT NULL, " +

            " FOREIGN KEY (" + QuickFitnessContract.ExerciseEntry.COLUMN_EXERCISE_CATEGORY_KEY + ") REFERENCES " +
            QuickFitnessContract.ExerciseCategoryEntry.TABLE_NAME + " (" + QuickFitnessContract.ExerciseCategoryEntry.COLUMN_CATEGORY_ID + "));";

    /*SQL script to create table 'status' on the database*/
    private static final String SQL_CREATE_STATUS_TABLE = "CREATE TABLE " + QuickFitnessContract.StatusEntry.TABLE_NAME + "(" +
            QuickFitnessContract.StatusEntry.COLUMN_STATUS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            QuickFitnessContract.StatusEntry.COLUMN_STATUS_NAME + " TEXT NOT NULL);";

    /*SQL script to create table 'workout_set' on the database. This table has a foreign key linked to 'status' table*/
    private static final String SQL_CREATE_WORKOUT_SET_TABLE = "CREATE TABLE " + QuickFitnessContract.WorkoutSetEntry.TABLE_NAME + "(" +
            QuickFitnessContract.WorkoutSetEntry.COLUMN_WORKOUT_SET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            QuickFitnessContract.WorkoutSetEntry.COLUMN_WORKOUT_SET_NAME + " TEXT NOT NULL, " +
            QuickFitnessContract.WorkoutSetEntry.COLUMN_WORKOUT_DAYS_OF_WEEK + " INTEGER NOT NULL, " +
            QuickFitnessContract.WorkoutSetEntry.COLUMN_WORKOUT_DURATION + " INTEGER NOT NULL, " +
            QuickFitnessContract.WorkoutSetEntry.COLUMN_WORKOUT_SET_TIME + " TEXT NOT NULL, " +
            QuickFitnessContract.WorkoutSetEntry.COLUMN_WORKOUT_SET_STATUS_KEY + " INTEGER NOT NULL, " +
            " FOREIGN KEY (" + QuickFitnessContract.WorkoutSetEntry.COLUMN_WORKOUT_SET_STATUS_KEY + ") REFERENCES " +
            QuickFitnessContract.StatusEntry.TABLE_NAME + " (" + QuickFitnessContract.StatusEntry.COLUMN_STATUS_ID + "));";

    /*SQL script to create table 'workout_exercise' on the database. This table has two foreign keys, one linked to the 'exercise_id' on the 'exercise table',
    * and the other linked to the 'workout_set_id' on the 'workout_set' table*/
    private static final String SQL_CREATE_WORKOUT_EXERCISE_TABLE = "CREATE TABLE " + QuickFitnessContract.WorkoutExerciseEntry.TABLE_NAME + "(" +
            QuickFitnessContract.WorkoutExerciseEntry.COLUMN_WORKOUT_EXERCISE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            QuickFitnessContract.WorkoutExerciseEntry.COLUMN_EXERCISE_KEY + " INTEGER NOT NULL, " +
            QuickFitnessContract.WorkoutExerciseEntry.COLUMN_WORKOUT_KEY + " INTEGER NOT NULL, " +
            " FOREIGN KEY (" + QuickFitnessContract.WorkoutExerciseEntry.COLUMN_EXERCISE_KEY + ") REFERENCES " +
            QuickFitnessContract.ExerciseEntry.TABLE_NAME + " (" + QuickFitnessContract.ExerciseEntry.COLUMN_EXERCISE_ID +
            "), FOREIGN KEY (" + QuickFitnessContract.WorkoutExerciseEntry.COLUMN_WORKOUT_KEY + ") REFERENCES " +
            QuickFitnessContract.WorkoutSetEntry.TABLE_NAME + " (" + QuickFitnessContract.WorkoutSetEntry.COLUMN_WORKOUT_SET_ID +
            "));";

    static Context mContext;
    private static QuickFitnessDbHelper instance;

    private QuickFitnessDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    //Singleton method to create an instance of the QuickFitnessDbHelper class. If this instance had been already created any time by the app, the instance is returned
    //Only one instance is shared throughout the app
    public static QuickFitnessDbHelper getInstance(Context context) {
        mContext = context;
        if (instance == null) {
            instance = new QuickFitnessDbHelper(context.getApplicationContext());
        }

        return instance;
    }

    //SQLite script to insert all categories into category table on database
    //This script will run only on the first time the app runs. If the user runs the app for the second time, for example, this script is skipped, once the table is already filled.
    private void categoryBulkInsert(SQLiteDatabase db) {

        CategoryItem allCategoriesItem = new CategoryItem(mContext.getResources().getString(R.string.category_all));
        CategoryItem cardioItem = new CategoryItem(mContext.getResources().getString(R.string.category_cardio), R.drawable.circle_tag_spinner_green);
        CategoryItem enduranceItem = new CategoryItem(mContext.getResources().getString(R.string.category_endurance), R.drawable.circle_tag_spinner_orange);
        CategoryItem strengthItem = new CategoryItem(mContext.getResources().getString(R.string.category_strength), R.drawable.circle_tag_spinner_purple);
        CategoryItem weightLossItem = new CategoryItem(mContext.getResources().getString(R.string.category_weight_loss), R.drawable.circle_tag_spinner_grey_blue);


        List<CategoryItem> exerciseCategories = new ArrayList<>();

        exerciseCategories.add(allCategoriesItem);
        exerciseCategories.add(cardioItem);
        exerciseCategories.add(enduranceItem);
        exerciseCategories.add(strengthItem);
        exerciseCategories.add(weightLossItem);

        for (int i = 0; i < exerciseCategories.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(QuickFitnessContract.ExerciseCategoryEntry.COLUMN_CATEGORY_NAME, exerciseCategories.get(i).getName());
            values.put(QuickFitnessContract.ExerciseCategoryEntry.COLUMN_CATEGORY_ICON, exerciseCategories.get(i).getIcon());
            db.insert(QuickFitnessContract.ExerciseCategoryEntry.TABLE_NAME, null, values);
        }
    }

    private void workoutStatusBulkInsert(SQLiteDatabase db) {

        WorkoutStatusItem statusStart = new WorkoutStatusItem(mContext.getResources().getString(R.string.workout_status_start));
        WorkoutStatusItem statusDoing = new WorkoutStatusItem(mContext.getResources().getString(R.string.workout_status_doing));
        WorkoutStatusItem statusStopped = new WorkoutStatusItem(mContext.getResources().getString(R.string.workout_status_stopped));
        WorkoutStatusItem statusFinished = new WorkoutStatusItem(mContext.getResources().getString(R.string.workout_status_finished));


        List<WorkoutStatusItem> workoutStatuses = new ArrayList<>();

        workoutStatuses.add(statusStart);
        workoutStatuses.add(statusDoing);
        workoutStatuses.add(statusStopped);
        workoutStatuses.add(statusFinished);


        for (int i = 0; i < workoutStatuses.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(QuickFitnessContract.StatusEntry.COLUMN_STATUS_NAME, workoutStatuses.get(i).getStatus());
            db.insert(QuickFitnessContract.StatusEntry.TABLE_NAME, null, values);
        }
    }

    private void exercisesBulkInsert(SQLiteDatabase db) {

        String ellipticalVideoStringPath = "android.resource://" + mContext.getPackageName() + "/" + R.raw.video_elliptical;
        ExercisesItem weightLossElliptical = new ExercisesItem(mContext.getResources().getString(R.string.weight_loss_exercise_title_elliptical), mContext.getResources().getString(R.string.weight_loss_exercise_description_elliptical),
                R.drawable.elliptical, mContext.getResources().getString(R.string.weight_loss_exercise_level_elliptical), mContext.getResources().getInteger(R.integer.weight_loss_exercise_duration_elliptical), mContext.getResources().getInteger(R.integer.weight_loss_exercise_calories_elliptical), ellipticalVideoStringPath, 5);

        String treadmillVideoStringPath = "android.resource://" + mContext.getPackageName() + "/" + R.raw.video_treadmill;
        ExercisesItem weightLossTreadmill = new ExercisesItem(mContext.getResources().getString(R.string.weight_loss_exercise_title_treadmill), mContext.getResources().getString(R.string.weight_loss_exercise_description_treadmill),
                R.drawable.treadmill, mContext.getResources().getString(R.string.weight_loss_exercise_level_treadmill), mContext.getResources().getInteger(R.integer.weight_loss_exercise_duration_treadmill), mContext.getResources().getInteger(R.integer.weight_loss_exercise_calories_treadmill), treadmillVideoStringPath, 5);


        String pullDownVideoStringPath = "android.resource://" + mContext.getPackageName() + "/" + R.raw.video_pull_down;
        ExercisesItem strengthPullDown = new ExercisesItem(mContext.getResources().getString(R.string.strength_exercise_title_pull_down), mContext.getResources().getString(R.string.strength_exercise_description_pull_down),
                R.drawable.pull_down, mContext.getResources().getString(R.string.strength_exercise_level_pull_down), mContext.getResources().getInteger(R.integer.strength_exercise_duration_pull_down), mContext.getResources().getInteger(R.integer.strength_exercise_calories_pull_down), pullDownVideoStringPath, 4);

        String pushUpVideoStringPath = "android.resource://" + mContext.getPackageName() + "/" + R.raw.video_push_up;
        ExercisesItem strengthPushUp = new ExercisesItem(mContext.getResources().getString(R.string.strength_exercise_title_push_up), mContext.getResources().getString(R.string.strength_exercise_description_push_up),
                R.drawable.push_up, mContext.getResources().getString(R.string.strength_exercise_level_push_up), mContext.getResources().getInteger(R.integer.strength_exercise_duration_push_up), mContext.getResources().getInteger(R.integer.strength_exercise_calories_push_up), pushUpVideoStringPath, 4);


        String treadmillIntervalVideoStringPath = "android.resource://" + mContext.getPackageName() + "/" + R.raw.video_treadmill;
        ExercisesItem cardioTreadmillInterval = new ExercisesItem(mContext.getResources().getString(R.string.cardio_exercise_title_treadmill_intervals), mContext.getResources().getString(R.string.cardio_exercise_description_treadmill_intervals),
                R.drawable.treadmill, mContext.getResources().getString(R.string.cardio_exercise_level_treadmill_intervals), mContext.getResources().getInteger(R.integer.cardio_exercise_duration_treadmill_intervals), mContext.getResources().getInteger(R.integer.cardio_exercise_calories_treadmill_intervals), treadmillIntervalVideoStringPath, 2);

        String jumpingJackVideoStringPath = "android.resource://" + mContext.getPackageName() + "/" + R.raw.video_jumping_jack;
        ExercisesItem cardioJumping = new ExercisesItem(mContext.getResources().getString(R.string.cardio_exercise_title_jumping_jack), mContext.getResources().getString(R.string.cardio_exercise_description_jumping_jack),
                R.drawable.jumping_jack, mContext.getResources().getString(R.string.cardio_exercise_level_jumping_jack), mContext.getResources().getInteger(R.integer.cardio_exercise_duration_jumping_jack), mContext.getResources().getInteger(R.integer.cardio_exercise_calories_jumping_jack), jumpingJackVideoStringPath, 2);


        String stationaryBikeVideoStringPath = "android.resource://" + mContext.getPackageName() + "/" + R.raw.video_stationary_bike;
        ExercisesItem enduranceStanioraryBike = new ExercisesItem(mContext.getResources().getString(R.string.endurance_exercise_title_stationary_bike), mContext.getResources().getString(R.string.endurance_exercise_description_stationary_bike),
                R.drawable.stationary_bike, mContext.getResources().getString(R.string.endurance_exercise_level_stationary_bike), mContext.getResources().getInteger(R.integer.endurance_exercise_duration_stationary_bike), mContext.getResources().getInteger(R.integer.endurance_exercise_calories_stationary_bike), stationaryBikeVideoStringPath, 3);


        String crawlVideoStringPath = "android.resource://" + mContext.getPackageName() + "/" + R.raw.video_crawl;
        ExercisesItem enduranceCrawl = new ExercisesItem(mContext.getResources().getString(R.string.endurance_exercise_title_crawl), mContext.getResources().getString(R.string.endurance_exercise_description_crawl),
                R.drawable.crawl, mContext.getResources().getString(R.string.endurance_exercise_level_crawl), mContext.getResources().getInteger(R.integer.endurance_exercise_duration_crawl), mContext.getResources().getInteger(R.integer.endurance_exercise_calories_crawl), crawlVideoStringPath, 3);


        List<ExercisesItem> exercises = new ArrayList<>();

        exercises.add(weightLossElliptical);
        exercises.add(weightLossTreadmill);

        exercises.add(strengthPullDown);
        exercises.add(strengthPushUp);

        exercises.add(cardioTreadmillInterval);
        exercises.add(cardioJumping);

        exercises.add(enduranceStanioraryBike);
        exercises.add(enduranceCrawl);

        for (int i = 0; i < exercises.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(QuickFitnessContract.ExerciseEntry.COLUMN_EXERCISE_NAME, exercises.get(i).getName());
            values.put(QuickFitnessContract.ExerciseEntry.COLUMN_EXERCISE_DESCRIPTION, exercises.get(i).getDescription());
            values.put(QuickFitnessContract.ExerciseEntry.COLUMN_EXERCISE_ICON, exercises.get(i).getIcon());
            values.put(QuickFitnessContract.ExerciseEntry.COLUMN_EXERCISE_LEVEL, exercises.get(i).getLevel());
            values.put(QuickFitnessContract.ExerciseEntry.COLUMN_EXERCISE_DURATION, exercises.get(i).getDuration());
            values.put(QuickFitnessContract.ExerciseEntry.COLUMN_EXERCISE_CALORIES, exercises.get(i).getCalories());
            values.put(QuickFitnessContract.ExerciseEntry.COLUMN_EXERCISE_VIDEO, exercises.get(i).getVideoAnimation());
            values.put(QuickFitnessContract.ExerciseEntry.COLUMN_EXERCISE_CATEGORY_KEY, exercises.get(i).getCategoryKey());
            db.insert(QuickFitnessContract.ExerciseEntry.TABLE_NAME, null, values);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //sqLiteDatabase.execSQL(SQL_DROP_DATABASE);

        //These calls create the tables on the database

        /*Creates user table*/
        sqLiteDatabase.execSQL(SQL_CREATE_USER_TABLE);

        /*Creates body table*/
        sqLiteDatabase.execSQL(SQL_CREATE_BODY_TABLE);

        /*Creates category table*/
        sqLiteDatabase.execSQL(SQL_CREATE_CATEGORY_TABLE);

        /*Creates exercise table*/
        sqLiteDatabase.execSQL(SQL_CREATE_EXERCISE_TABLE);

        /*Creates status table*/
        sqLiteDatabase.execSQL(SQL_CREATE_STATUS_TABLE);

        /*Creates workout set table*/
        sqLiteDatabase.execSQL(SQL_CREATE_WORKOUT_SET_TABLE);

        /*Creates workout exercises table*/
        sqLiteDatabase.execSQL(SQL_CREATE_WORKOUT_EXERCISE_TABLE);
        Log.i(MainActivity.APP_TAG, "Database created.");

        //Insert all the categories on the category table in the first time the app runs
        categoryBulkInsert(sqLiteDatabase);
        Log.i(MainActivity.APP_TAG, "Categories Inserted");

        //Insert all the exercises on the exercise table in the first time the app runs
        exercisesBulkInsert(sqLiteDatabase);
        Log.i(MainActivity.APP_TAG, "Exercises Inserted");

        //Insert all the workout statuses on the status table in the first time the app runs
        workoutStatusBulkInsert(sqLiteDatabase);
        Log.i(MainActivity.APP_TAG, "Workout Statuses Inserted");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        //SQLite query to delete tables if they exist

        /*Deletes user table*/
        sqLiteDatabase.execSQL(SQL_DROP_USER_TABLE);

        /*Deletes category table*/
        sqLiteDatabase.execSQL(SQL_DROP_CATEGORY_TABLE);

        /*Deletes exercise table*/
        sqLiteDatabase.execSQL(SQL_DROP_EXERCISE_TABLE);

        /*Deletes body table*/
        sqLiteDatabase.execSQL(SQL_DROP_BODY_TABLE);

        /*Deletes status table*/
        sqLiteDatabase.execSQL(SQL_DROP_STATUS_TABLE);

        /*Deletes workout set table*/
        sqLiteDatabase.execSQL(SQL_DROP_WORKOUT_SET_TABLE);

        /*Deletes workout exercises table*/
        sqLiteDatabase.execSQL(SQL_DROP_WORKOUT_EXERCISE_TABLE);
        Log.i("DATABASE", "Database deleted.");

        //Calls the onCreate() method to create the database again
        onCreate(sqLiteDatabase);
    }
}
