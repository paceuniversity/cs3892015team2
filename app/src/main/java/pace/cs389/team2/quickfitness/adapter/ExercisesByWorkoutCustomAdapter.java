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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import pace.cs389.team2.quickfitness.R;
import pace.cs389.team2.quickfitness.data.QuickFitnessDAO;
import pace.cs389.team2.quickfitness.model.CategoryItem;
import pace.cs389.team2.quickfitness.model.ExercisesItem;

public class ExercisesByWorkoutCustomAdapter extends BaseAdapter {

    Context mContext;
    private List<ExercisesItem> exercisesItemList;
    private LayoutInflater inflater;
    private HashMap<Integer, Boolean> mSelection = new HashMap<>();


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

    public void setNewSelection(int position, boolean value) {
        mSelection.put(position, value);
        notifyDataSetChanged();
    }

    public boolean isPositionChecked(int position) {
        Boolean result = mSelection.get(position);
        return result == null ? false : result;
    }

    public Set<Integer> getCurrentCheckedPosition() {
        return mSelection.keySet();
    }

    public void removeSelection(int position) {
        mSelection.remove(position);
        notifyDataSetChanged();
    }

    public void clearSelection() {
        mSelection = new HashMap<>();
        notifyDataSetChanged();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        AdapterViewHolder holder;

        if (view == null) {

            holder = new AdapterViewHolder();
            view = inflater.inflate(R.layout.exercise_by_workout_adapter, parent, false);

            holder.mImageExerciseIcon = (ImageView) view.findViewById(R.id.img_exercise_icon);
            holder.mTextExerciseName = (TextView) view
                    .findViewById(R.id.txt_exercise_name);

            holder.mTextExerciseCategory = (TextView) view.findViewById(R.id.txt_exercise_category);
            holder.mTextExerciseDuration = (TextView) view.findViewById(R.id.txt_exercise_duration);
            holder.mTextExerciseLevel = (TextView) view.findViewById(R.id.txt_exercise_level);

            view.setTag(holder);
        } else {
            holder = (AdapterViewHolder) view.getTag();
        }

        view.setBackgroundColor(Color.TRANSPARENT);

        ExercisesItem exercisesItem = exercisesItemList.get(position);

        Bitmap exerciseIcon = BitmapFactory.decodeResource(view.getResources(), exercisesItem.getIcon());

        Bitmap exerciseIconResized = Bitmap.createScaledBitmap(exerciseIcon,
                70, 70, false);


        if (exerciseIconResized != null) {
            holder.mImageExerciseIcon.setImageBitmap(exerciseIconResized);
        }


        CategoryItem categoryItem = QuickFitnessDAO.getInstance(view.getContext()).categoryById(exercisesItem.getCategoryKey());

        holder.mTextExerciseName.setText(exercisesItem.getName());
        holder.mTextExerciseCategory.setText(categoryItem.getName());
        holder.mTextExerciseDuration.setText(String.valueOf(exercisesItem.getDuration()) + " min");
        holder.mTextExerciseLevel.setText(exercisesItem.getLevel());

        if (mSelection.get(position) != null) {
            view.setBackgroundColor(Color.LTGRAY);
        }

        return view;
    }

    public class AdapterViewHolder {

        protected ImageView mImageExerciseIcon;
        protected TextView mTextExerciseName;
        protected TextView mTextExerciseCategory;
        protected TextView mTextExerciseDuration;
        protected TextView mTextExerciseLevel;
    }
}
