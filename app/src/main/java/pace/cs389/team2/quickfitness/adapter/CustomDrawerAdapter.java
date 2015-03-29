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

package pace.cs389.team2.quickfitness.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import pace.cs389.team2.quickfitness.R;
import pace.cs389.team2.quickfitness.model.ItemDrawer;

/**
 * Created by Luiz on 25/03/2015.
 * <p/>
 * This class creates an adapter of type ItemDrawer to display in a ListView
 */
public class CustomDrawerAdapter extends ArrayAdapter<ItemDrawer> {

    Context mContext;
    private int mIconRes;
    private List<ItemDrawer> mListDrawer;
    private LayoutInflater mInflater;

    // Default class constructor with the application context, item icon, and the items list
    // Makes a copy of these attributes to local variables and initialize the LayoutInflater
    public CustomDrawerAdapter(Context context, int mIconRes, List<ItemDrawer> mListItems) {
        super(context, mIconRes, mListItems);
        this.mContext = context;
        this.mIconRes = mIconRes;
        this.mListDrawer = mListItems;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DrawerViewHolder mDrawerHolder;
        View mView = convertView;

        if (mView == null) {

            mDrawerHolder = new DrawerViewHolder();

            mView = mInflater.inflate(mIconRes, parent, false);
            mDrawerHolder.mItemText = (TextView) mView.findViewById(R.id.drawer_item_name);
            mDrawerHolder.mItemIcon = (ImageView) mView.findViewById(R.id.drawer_item_icon);

           /* mDrawerHolder.mListSeparator = mView.findViewById(R.id.list_divider2);

            if (position == 4) {
                mDrawerHolder.mListSeparator.setVisibility(View.VISIBLE);
            }*/


            mDrawerHolder.mItemLayout = (LinearLayout) mView
                    .findViewById(R.id.item_layout);


            mView.setTag(mDrawerHolder);

        } else {
            mDrawerHolder = (DrawerViewHolder) mView.getTag();

        }

        ItemDrawer mItemDrawer = (ItemDrawer) this.mListDrawer.get(position);
        mDrawerHolder.mItemLayout.setVisibility(LinearLayout.VISIBLE);
        mDrawerHolder.mItemIcon.setImageDrawable(mView.getResources().getDrawable(
                mItemDrawer.getmIconRes()));
        mDrawerHolder.mItemText.setText(mItemDrawer.getmNameItem());


        return mView;
    }

    public static class DrawerViewHolder {
        TextView mItemText, mItemTitle;
        View mListSeparator;
        ImageView mItemIcon;
        LinearLayout mItemLayout;
    }
}