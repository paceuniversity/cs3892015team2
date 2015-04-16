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

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pace.cs389.team2.quickfitness.R;
import pace.cs389.team2.quickfitness.model.CategoryItem;

/**
 * Created by Luiz on 28/03/2015.
 */

public class CustomSpinnerAdapter extends BaseAdapter {

    private ImageView imgIcon;
    private TextView txtTitle;
    private List<CategoryItem> spinnerNavItem;
    private Context context;

    public CustomSpinnerAdapter(Context context,
                                List<CategoryItem> spinnerNavItem) {
        this.spinnerNavItem = spinnerNavItem;
        this.context = context;
    }

    @Override
    public int getCount() {
        return spinnerNavItem.size();
    }

    @Override
    public Object getItem(int index) {
        return spinnerNavItem.get(index);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.spinner_categories_adapter, null);
        }

        imgIcon = (ImageView) convertView.findViewById(R.id.img_spinner_adapter);
        txtTitle = (TextView) convertView.findViewById(R.id.txt_spinner_adapter);

        txtTitle.setText(spinnerNavItem.get(position).getName());
        imgIcon.setImageResource(spinnerNavItem.get(position).getIcon());


        return convertView;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.spinner_categories_adapter, null);
        }


        imgIcon = (ImageView) convertView.findViewById(R.id.img_spinner_adapter);
        txtTitle = (TextView) convertView.findViewById(R.id.txt_spinner_adapter);

        if (imgIcon == null) {
            txtTitle.setText(spinnerNavItem.get(position).getName());
        } else {
            imgIcon.setImageResource(spinnerNavItem.get(position).getIcon());
            txtTitle.setText(spinnerNavItem.get(position).getName());
        }

        return convertView;
    }

}
