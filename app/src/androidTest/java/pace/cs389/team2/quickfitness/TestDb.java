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

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import pace.cs389.team2.quickfitness.data.QuickFitnessDbHelper;

public class TestDb extends AndroidTestCase {

    public static final String LOG_TAG = TestDb.class.getSimpleName();
    static final String TEST_LOCATION = "99705";
    static final String TEST_DATE = "20141205";

    public void testCreateDb() throws Throwable {
        mContext.deleteDatabase(QuickFitnessDbHelper.DATABASE_NAME);
        SQLiteDatabase db = QuickFitnessDbHelper.getInstance(mContext.getApplicationContext()).getWritableDatabase();
        assertEquals(true, db.isOpen());
        db.close();
    }
}
