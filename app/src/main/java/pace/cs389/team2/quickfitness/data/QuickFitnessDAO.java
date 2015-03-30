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

import java.util.ArrayList;
import java.util.List;

import pace.cs389.team2.quickfitness.R;
import pace.cs389.team2.quickfitness.model.CategoryItem;
import pace.cs389.team2.quickfitness.model.ItemDrawer;

/**
 * Created by Luiz on 29/03/2015.
 */
public class QuickFitnessDAO {

    private static QuickFitnessDAO instance;
    private SQLiteDatabase sqLiteDatabase;
    QuickFitnessDbHelper helper;

    private static final String[] PROJECTION = {QuickFitnessContract.ExerciseCategoryEntry.COLUMN_CATEGORY_ID,
            QuickFitnessContract.ExerciseCategoryEntry.COLUMN_CATEGORY_NAME,
            QuickFitnessContract.ExerciseCategoryEntry.COLUMN_CATEGORY_ICON
    };

    public static QuickFitnessDAO getInstance(Context mContext) {

        if (instance == null) {
            instance = new QuickFitnessDAO(mContext);
        }

        return instance;

    }

    private QuickFitnessDAO(Context context) {
        helper = QuickFitnessDbHelper.getInstance(context);
        sqLiteDatabase = helper.getWritableDatabase();
    }

    public void categoryBulkInsert() {

        CategoryItem allCategoriesItem = new CategoryItem("All Categories");
        CategoryItem cardioItem = new CategoryItem("Cardio", R.drawable.circle_tag_spinner_green);
        CategoryItem enduranceItem = new CategoryItem("Endurance", R.drawable.circle_tag_spinner_orange);
        CategoryItem strengthItem = new CategoryItem("Strength", R.drawable.circle_tag_spinner_purple);
        CategoryItem weightLossItem = new CategoryItem("Weight Loss", R.drawable.circle_tag_spinner_grey_blue);


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
            sqLiteDatabase.insert(QuickFitnessContract.ExerciseCategoryEntry.TABLE_NAME, null, values);
        }


    }

    public List<CategoryItem> listExercisesCategories() {
        List<CategoryItem> categories = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.query(QuickFitnessContract.ExerciseCategoryEntry.TABLE_NAME, PROJECTION,
                null, null, null, null, QuickFitnessContract.ExerciseCategoryEntry.COLUMN_CATEGORY_NAME);

        CategoryItem itemCategory = null;
        if (cursor.moveToFirst()) {
            do {
                itemCategory = new CategoryItem(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
                categories.add(itemCategory);
            } while (cursor.moveToNext());
        }

        return categories;
    }

}
