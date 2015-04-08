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
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import pace.cs389.team2.quickfitness.R;
import pace.cs389.team2.quickfitness.model.ExercisesItem;

public class ExercisesByWorkoutCustomAdapter extends BaseAdapter {

    Context mContext;
    private List<ExercisesItem> exercisesItemList;
    private LayoutInflater inflater;

    public ExercisesByWorkoutCustomAdapter(Context mContext, List<ExercisesItem> exercisesItemList) {
        this.mContext = mContext;
        this.exercisesItemList = exercisesItemList;
        inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return exercisesItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return exercisesItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return exercisesItemList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        AdapterViewHolder holder;

        if (view == null) {

            holder = new AdapterViewHolder();
            view = inflater.inflate(R.layout.exercise_by_workout_adapter, parent, false);
            holder.mTextExerciseName = (TextView) view
                    .findViewById(R.id.txt_exercise_name);

            view.setTag(holder);
        } else {
            holder = (AdapterViewHolder) view.getTag();
        }

        ExercisesItem exercisesItem = exercisesItemList.get(position);

        holder.mTextExerciseName.setText(exercisesItem.getName());

        return view;
    }


    public class AdapterViewHolder {

        protected TextView mTextExerciseName;
    }
}
