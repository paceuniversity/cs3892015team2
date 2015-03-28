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

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import pace.cs389.team2.quickfitness.adapter.CustomDrawerAdapter;
import pace.cs389.team2.quickfitness.model.ItemDrawer;

/**
 * MainActivity will launch the app's home screen
 */

public class MainActivity extends Activity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private LinearLayout mGroupView;
    private List<ItemDrawer> mDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Copies references to local variables
        mDataList = new ArrayList<ItemDrawer>();
        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.container);
        mDrawerList = (ListView) findViewById(R.id.list_left_drawer);
        mGroupView = (LinearLayout) findViewById(R.id.group_layout);

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);

        //Adds items to the left menu drawer
        mDataList.add(new ItemDrawer(getResources().getString(R.string.list_drawer_item_home), R.mipmap.ic_home_grey600_24dp));
        mDataList.add(new ItemDrawer(getResources().getString(R.string.list_drawer_item_set_goal), R.mipmap.ic_goal24dp));
        mDataList.add(new ItemDrawer(getResources().getString(R.string.list_drawer_item_workouts), R.mipmap.ic_workouts24dp));
        mDataList.add(new ItemDrawer(getResources().getString(R.string.list_drawer_item_profile), R.mipmap.ic_person_grey600_24dp));
        mDataList.add(new ItemDrawer(getResources().getString(R.string.list_drawer_item_statistics), R.mipmap.ic_trending_up_grey600_24dp));
        mDataList.add(new ItemDrawer(getResources().getString(R.string.list_drawer_item_settings), R.mipmap.ic_settings_grey600_24dp));
        mDataList.add(new ItemDrawer(getResources().getString(R.string.list_drawer_item_help), R.mipmap.ic_help_grey600_24dp));

        CustomDrawerAdapter adapter = new CustomDrawerAdapter(this, R.layout.custom_drawer_item,
                mDataList);

        mDrawerList.setAdapter(adapter);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open,
                R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to
                // onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (mDrawerToggle.onOptionsItemSelected(item))
            return true;
        return false;
    }


    public void selectItem(int position) {

        Fragment fragment = null;
        Bundle args = new Bundle();


        switch (position) {


            case 0:
                fragment = new FragmentMainContent();
                break;
            case 1:
                fragment = new FragmentSetGoal();
                break;
            case 2:
                fragment = new FragmentMainContent();
                args.putString(FragmentMainContent.ITEM_NAME, mDataList.get(position)
                        .getmNameItem());
                args.putInt(FragmentMainContent.IMAGE_RESOURCE_ID, mDataList
                        .get(position).getmIconRes());
                break;
            case 3:
                fragment = new FragmentMainContent();
                args.putString(FragmentMainContent.ITEM_NAME, mDataList.get(position)
                        .getmNameItem());
                args.putInt(FragmentMainContent.IMAGE_RESOURCE_ID, mDataList
                        .get(position).getmIconRes());
                break;
            case 4:

                fragment = new FragmentMainContent();
                args.putString(FragmentMainContent.ITEM_NAME, mDataList.get(position)
                        .getmNameItem());
                args.putInt(FragmentMainContent.IMAGE_RESOURCE_ID, mDataList
                        .get(position).getmIconRes());
                break;
            case 5:
                fragment = new FragmentMainContent();
                args.putString(FragmentMainContent.ITEM_NAME, mDataList.get(position)
                        .getmNameItem());
                args.putInt(FragmentMainContent.IMAGE_RESOURCE_ID, mDataList
                        .get(position).getmIconRes());
                break;
            case 6:
                fragment = new FragmentMainContent();
                args.putString(FragmentMainContent.ITEM_NAME, mDataList.get(position)
                        .getmNameItem());
                args.putInt(FragmentMainContent.IMAGE_RESOURCE_ID, mDataList
                        .get(position).getmIconRes());
                break;
            default:
                break;
        }
        FragmentManager frgManager = getFragmentManager();
        frgManager.beginTransaction().replace(R.id.content_place_holder, fragment)
                .commit();

        mDrawerList.setItemChecked(position, true);
        setTitle(mDataList.get(position).getmNameItem());
        mDrawerLayout.closeDrawer(mGroupView);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;

        getActionBar().setTitle(mTitle);
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

    public void setUserPicture(View view) {
        Toast.makeText(this, "User picture.", Toast.LENGTH_LONG).show();
    }
}
