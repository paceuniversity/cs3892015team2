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

import android.provider.BaseColumns;

/**
 * Created by Luiz on 29/03/2015.
 */
public class QuickFitnessContract {

    /* Inner class that defines the table contents of the user table */
    public static final class UserEntry implements BaseColumns {

        //Table name
        public static final String TABLE_NAME = "user";

        //User id is stored as an integer value
        public static final String COLUMN_USER_ID = "user_id";

        //User name, stored as string
        public static final String COLUMN_USER_NAME = "user_name";

        // User surname, stored as string
        public static final String COLUMN_USER_SURNAME = "user_surname";

        //User age column is stored as integer
        public static final String COLUMN_USER_AGE = "user_age";

        //User picture is stored as string
        public static final String COLUMN_USER_PICTURE = "user_picture";

    }

    /* Inner class that defines the table contents of the body table */
    public static final class BodyEntry implements BaseColumns {

        //Table name
        public static final String TABLE_NAME = "body";

        //Body id column is stored as an integer value
        public static final String COLUMN_BODY_ID = "body_id";

        //User height column stores the user height as integer
        public static final String COLUMN_BODY_HEIGHT = "body_height";

        //User weight column stores the user weight as integer
        public static final String COLUMN_BODY_WEIGHT = "body_weight";

        //Body Mass Index will be calculated base on user height and weight and will be stored as a float value
        public static final String COLUMN_BODY_BMI = "body_bmi";

        //Body Fat will be calculated based on user height, weight, and age and stored as a float value
        public static final String COLUMN_BODY_BF = "body_bf";

        // Column with the foreign key into the user table
        public static final String COLUMN_BODY_USER_ID_KEY = "body_user_id";
    }

    /* Inner class that defines the table contents of the exercise table */
    public static final class ExerciseEntry implements BaseColumns {

        //Table name
        public static final String TABLE_NAME = "exercise";

        //Exercise primary key, stored as an integer
        public static final String COLUMN_EXERCISE_ID = "exercise_id";

        //Exercise name, stored as string
        public static final String COLUMN_EXERCISE_NAME = "exercise_name";

        //Exercise description column will store a brief description of the exercise as a string
        public static final String COLUMN_EXERCISE_DESCRIPTION = "exercise_description";

        //Sets will represent how many times each exercise will be done. Stored as integer
        public static final String COLUMN_EXERCISE_SETS = "exercise_sets";

        //Reps will represent how many times an exercise will be repeated for each set. Stored as integer
        public static final String COLUMN_EXERCISE_REPS = "exercise_reps";

        //Calories column will store how many calories each exercise burns. Stored as interger
        public static final String COLUMN_EXERCISE_CALORIES = "exercise_calories";

        // Exercise Video, video path will be stored here as string
        public static final String COLUMN_EXERCISE_VIDEO = "exercise_video";

        // Column with the foreign key into the user table
        public static final String COLUMN_EXERCISE_CATEGORY_KEY = "exercise_category";
    }

    /* Inner class that defines the table contents of the category table */
    public static final class ExerciseCategoryEntry implements BaseColumns {

        //Table name
        public static final String TABLE_NAME = "category";

        //Category primary key, stored as integer
        public static final String COLUMN_CATEGORY_ID = "category_id";

        //Category name, stored as String
        public static final String COLUMN_CATEGORY_NAME = "category_name";
    }

    /* Inner class that defines the table contents of the workout_set table */
    public static final class WorkoutSetEntry implements BaseColumns {

        //Table name
        public static final String TABLE_NAME = "workout_set";

        //Workout set primary key, stored as integer
        public static final String COLUMN_WORKOUT_SET_ID = "workout_set_id";

        //Workout set name, stored as string
        public static final String COLUMN_WORKOUT_SET_NAME = "workout_set_name";

        //Workout preferred time, stored as string in "hh:mm" format
        public static final String COLUMN_WORKOUT_SET_TIME = "workout_set_time";

        // Column with the foreign key into the user table
        public static final String COLUMN_WORKOUT_SET_STATUS_KEY = "workout_set_status";
    }

    /* Inner class that defines the table contents of the status table */
    public static final class StatusEntry implements BaseColumns {

        //Table name
        public static final String TABLE_NAME = "status";

        //Status primary key stored as an integer
        public static final String COLUMN_STATUS_ID = "status_id";

        //Status name, stored as string
        public static final String COLUMN_STATUS_NAME = "status_name";
    }

    /* Inner class that defines the table contents of the workout_set_exercise table */
    public static final class WorkoutExerciseEntry implements BaseColumns {

        //Table name
        public static final String TABLE_NAME = "workout_set_exercise";

        // Column with the foreign key into the exercise table
        public static final String COLUMN_EXERCISE_KEY = "workout_set_exercise_id";

        // Column with the foreign key into the workout table
        public static final String COLUMN_WORKOUT_KEY = "workout_set_workout_id";
    }
}
