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

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pace.cs389.team2.quickfitness.adapter.CustomDrawerAdapter;
import pace.cs389.team2.quickfitness.data.QuickFitnessDAO;
import pace.cs389.team2.quickfitness.model.DrawerItem;
import pace.cs389.team2.quickfitness.model.UserItem;
import pace.cs389.team2.quickfitness.preferences.UserLoggedPreference;
import pace.cs389.team2.quickfitness.utils.BitmapUtils;

/**
 * MainActivity will launch the app's home screen
 */

public class MainActivity extends ActionBarActivity {

    //App tag used for debug
    //The tag uses the class name to filter log messages for this class
    public static final String APP_TAG = MainActivity.class.getSimpleName();
    private static final int RESULT_LOAD_IMG = 100;
    TextView txtUserLoggedIn;
    TextView txtUserLoggedInEmail;
    ImageView mUserPicture;
    String imgPathDecode;
    FrameLayout mDrawerTop;
    // DrawerLayout object to display app's left menu options
    private DrawerLayout mDrawerLayout;
    // ListView to show menu options on DrawerLayout
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    //Variable to show the title on drawer
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private LinearLayout mGroupView;
    private List<DrawerItem> mDataList;
    private UserItem userItem;
    private UserLoggedPreference prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Copies references to local variables
        mDataList = new ArrayList<>();
        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.container);
        mDrawerList = (ListView) findViewById(R.id.list_left_drawer);
        mGroupView = (LinearLayout) findViewById(R.id.group_layout);
        txtUserLoggedIn = (TextView) findViewById(R.id.txt_user_logged);
        txtUserLoggedInEmail = (TextView) findViewById(R.id.txt_user_logged_email);
        mUserPicture = (ImageView) findViewById(R.id.img_main_user_pic);
        mDrawerTop = (FrameLayout) findViewById(R.id.drawer_top_place_holder);

        prefs = new UserLoggedPreference(getApplicationContext());

        if (!prefs.isFirstTime()) {
            showDrawerTop();
        }

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);

        //Adds items to the left menu drawer
        mDataList.add(new DrawerItem(getResources().getString(R.string.list_drawer_item_home), R.mipmap.ic_home_grey600_24dp));
        mDataList.add(new DrawerItem(getResources().getString(R.string.list_drawer_item_set_goal), R.mipmap.ic_goal24dp));
        mDataList.add(new DrawerItem(getResources().getString(R.string.list_drawer_item_workouts), R.mipmap.ic_workouts24dp));
        mDataList.add(new DrawerItem(getResources().getString(R.string.list_drawer_item_profile), R.mipmap.ic_person_grey600_24dp));
        mDataList.add(new DrawerItem(getResources().getString(R.string.list_drawer_item_statistics), R.mipmap.ic_trending_up_grey600_24dp));
        mDataList.add(new DrawerItem(getResources().getString(R.string.list_drawer_item_settings), R.mipmap.ic_settings_grey600_24dp));
        mDataList.add(new DrawerItem(getResources().getString(R.string.list_drawer_item_help), R.mipmap.ic_help_grey600_24dp));

        //Makes a reference to the menu drawer adapter, which will show them items with an icon on the left.
        //CustomDrawerAdapter receives the context, the custom xml layout adapter and the list of items to be displayed.
        CustomDrawerAdapter adapter = new CustomDrawerAdapter(this, R.layout.custom_drawer_item,
                mDataList);

        //Set adapter to ListView
        mDrawerList.setAdapter(adapter);

        //Check if the action bar object isn't null and then enables the up navigator and home buttons
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open,
                R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to
                // onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to
                // onPrepareOptionsMenu()
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);


        if (savedInstanceState == null) {
            selectItem(0);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    public void showDrawerTop() {

        userItem = QuickFitnessDAO.getInstance(this).loadLoggedUser(prefs.getName());

        mDrawerTop.setVisibility(View.VISIBLE);
        mUserPicture.setVisibility(View.VISIBLE);

        if (userItem != null) {
            if (!(userItem.getPicture().equals(""))) {
                Bitmap mIcon = BitmapFactory
                        .decodeFile(userItem.getPicture());
                Bitmap updatedIcon = BitmapUtils.getRoundedCroppedBitmap(mIcon, 500);

                mUserPicture.setImageBitmap(updatedIcon);
            }
        }

        txtUserLoggedIn.setText(prefs.getName());
        txtUserLoggedInEmail.setText(prefs.getEmail());
    }

    public void userLogout(View view) {
        UserLoggedPreference prefs = new UserLoggedPreference(this);
        prefs.logOut();
        Toast.makeText(this, "User logged out.", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_log_out) {

            final UserLoggedPreference prefs = new UserLoggedPreference(this);

            new AlertDialog.Builder(this)
                    .setTitle("Exit application")
                    .setMessage("Are you sure you want to logout?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            prefs.logOut();
                            startActivity(new Intent(MainActivity.this, ActivityIntro.class));
                            MainActivity.this.finish();
                            Toast.makeText(MainActivity.this, "User logged out.", Toast.LENGTH_LONG).show();
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            return true;
        }

        return mDrawerToggle.onOptionsItemSelected(item);
    }


    public void selectItem(int position) {

        Fragment fragment;
        // Bundle args = new Bundle();
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        switch (position) {

            case 0:
                fragment = new FragmentMainContent();
                break;
            case 1:
                fragment = new ActivitySetGoal.FragmentSetGoal();
                break;
            case 2:
                UserLoggedPreference prefs = new UserLoggedPreference(this);
                if (prefs.isFirstTime()) {
                    startActivity(new Intent(this, ActivityIntro.class));
                    fragment = new FragmentMainContent();
                    finish();
                } else {
                    fragment = new ActivityWorkoutsList.FragmentWorkouts();
                }
                break;
            case 3:
                fragment = new MainFragment();
                break;
            case 4:
                fragment = new ActivityStatistics.StatisticsFragment();
                break;
            case 5:
                fragment = new MainFragment();
                break;
            case 6:
                fragment = new MainFragment();
                break;
            default:
                fragment = new MainFragment();
                break;
        }


        ft.replace(R.id.content_place_holder, fragment)
                .commit();

        mDrawerList.setItemChecked(position, true);
        setTitle(mDataList.get(position).getmNameItem());
        mDrawerLayout.closeDrawer(mGroupView);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(mTitle);
        }

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public void setUserPicture(View view) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {

                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);

                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgPathDecode = cursor.getString(columnIndex);
                cursor.close();

                // Set the Image in ImageView after decoding the String

                userItem.setPicture(imgPathDecode);
                QuickFitnessDAO.getInstance(this).updateUserPicture(userItem);

                if (!(userItem.getPicture().equals(""))) {
                    Bitmap mIcon = BitmapFactory
                            .decodeFile(userItem.getPicture());
                    Bitmap updatedIcon = BitmapUtils.getRoundedCroppedBitmap(mIcon, 500);

                    mUserPicture.setImageBitmap(updatedIcon);
                    Toast.makeText(this, "Picture changed.", Toast.LENGTH_LONG).show();
                }


            } else {
                Toast.makeText(this, "You haven't picked an image.",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong.", Toast.LENGTH_LONG)
                    .show();
        }

    }

    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            if (mDataList.get(position).getmTitleItem() == null) {
                selectItem(position);
            }

        }

    }
}
