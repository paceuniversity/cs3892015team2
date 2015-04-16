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

package pace.cs389.team2.quickfitness.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * @since 04/16/2015
 * <p/>
 * This class provide methods to store in a SharedPreferences file whether the user has been already logged into the app.
 * Also, if the user is logged in, the app will not show the login screen again, once the user has done this process before.
 * This class also has a method that allows the user to logout of the application. If the user chooses to logout of the app, the SharedPreferences file is cleaned up (its content).
 */


public class UserLoggedPreference {

    /* Variables declaration */
    private SharedPreferences prefs;

    private Editor editor;

    final int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "UserLogin";

    private static final String IS_FIRST_TIME = "IsFirstTime";

    private static final String USER_NAME = "name";
    private static final String USER_EMAIL = "email";

    Context mContext;

    //Class constructor receives the Context as parameter
    public UserLoggedPreference(Context context) {
        //Makes a copy of the context to a local variable
        this.mContext = context;
        //Initialize the SharedPreferences object
        prefs = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        //Sets the SharedPreferences object to edit mode. This must be done to be able to change the file's content.
        editor = prefs.edit();
    }

    //This method checks if the user is not signed into the application. If is the first time he is running it, the app will ask him to log in, proving him the option to skip this
    // process for now.
    public boolean isFirstTime() {
        //Sets true to attribute 'IS_FIST_TIME' meaning the user is not logged in.
        return prefs.getBoolean(IS_FIRST_TIME, true);
    }

    //This method checks whether the user has been already logged into the application once. If so, then the app does not take him again to the login screen.
    public void setOld(boolean b) {
        if (b) {
            //Sets false to attribute 'IS_FIRST_TIME' meaning that the user is already logged in.
            editor.putBoolean(IS_FIRST_TIME, false);
            //Commit changes
            editor.commit();
        }
    }

    //This method cleans the SharedPreferences file used to store the logged in user info when he clicks to 'log out' on the application
    public void logOut() {
        //Clear SharedPreferences file
        editor.clear();
        //Commit changes
        editor.commit();
    }

    //This method returns the user name which has been previously stored on SharedPreferences when the user logged in
    public String getName() {
        //Returns the user name
        return prefs.getString(USER_NAME, "");
    }

    //This method returns the user email which has been previously stored on SharedPreferences when the user logged in
    public String getEmail() {
        //Returns the user email
        return prefs.getString(USER_EMAIL, "");
    }

    //This method stores the user name when the user logs into the application for the first time. When he comes back to it (without logging in again), the
    // application will not ask for login again unless the user logs out manually.
    public void setName(String name) {
        //Store the user name
        editor.putString(USER_NAME, name);
        //Commit changes
        editor.commit();
    }

    //This method stores the user email when the user logs into the application for the first time. When he comes back to it (without logging in again), the
    // application will not ask for login again unless the user logs out manually.
    public void setEmail(String email) {
        //Store the user email
        editor.putString(USER_EMAIL, email);
        //Commit changes
        editor.commit();
    }

}
