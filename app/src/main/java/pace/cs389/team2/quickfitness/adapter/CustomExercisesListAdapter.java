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

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import pace.cs389.team2.quickfitness.R;
import pace.cs389.team2.quickfitness.data.QuickFitnessDAO;
import pace.cs389.team2.quickfitness.model.CategoryItem;
import pace.cs389.team2.quickfitness.model.ExercisesItem;

public class CustomExercisesListAdapter extends RecyclerView.Adapter<CustomExercisesListAdapter.ExercisesViewHolder> implements View.OnClickListener {

    private List<ExercisesItem> mExercisesList;
    private View itemView;
    ExercisesItem exercisesItem;

    public CustomExercisesListAdapter(List<ExercisesItem> exercisesList) {
        this.mExercisesList = exercisesList;
    }

    @Override
    public int getItemCount() {
        return mExercisesList.size();
    }

    @Override
    public ExercisesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.set_goal_exercises_adapter, viewGroup, false);


        return new ExercisesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ExercisesViewHolder exercisesViewHolder, int i) {
        exercisesItem = mExercisesList.get(i);

        if (exercisesItem.getCategoryKey() == 2) {
            exercisesViewHolder.mCardBottomLayout.setBackgroundResource(R.color.material_green);
        } else if (exercisesItem.getCategoryKey() == 3) {
            exercisesViewHolder.mCardBottomLayout.setBackgroundResource(R.color.material_orange);
        } else if (exercisesItem.getCategoryKey() == 4) {
            exercisesViewHolder.mCardBottomLayout.setBackgroundResource(R.color.material_purple);
        } else if (exercisesItem.getCategoryKey() == 5) {
            exercisesViewHolder.mCardBottomLayout.setBackgroundResource(R.color.material_blue_grey);

        } else {
            exercisesViewHolder.mCardBottomLayout.setBackgroundResource(R.color.background_list_item_gray);
        }

        exercisesViewHolder.mExerciseTitle.setText(exercisesItem.getName());

        CategoryItem category = QuickFitnessDAO.getInstance(itemView.getContext()).categoryById(exercisesItem.getCategoryKey());
        exercisesViewHolder.mExerciseCategory.setText(category.getName());

        ImageLoader task = new ImageLoader();
        task.execute(exercisesItem.getIcon());

    }

    @Override
    public void onClick(View v) {
        if (exercisesItem != null) {
            //Intent i = Activity.getIntent(v.getContext(), exercisesItem);
            Toast.makeText(v.getContext(), exercisesItem.getName(), Toast.LENGTH_LONG).show();
        }
    }

    public static class ExercisesViewHolder extends RecyclerView.ViewHolder {
        protected TextView mExerciseTitle;
        protected TextView mExerciseCategory;
        protected ImageView mImageExerciseTop;
        protected LinearLayout mCardBottomLayout;

        public ExercisesViewHolder(View v) {
            super(v);
            mExerciseTitle = (TextView) v.findViewById(R.id.txt_exercise_title);
            mExerciseCategory = (TextView) v.findViewById(R.id.txt_exercise_category);
            mImageExerciseTop = (ImageView) v.findViewById(R.id.img_exercise_picture);
            mCardBottomLayout = (LinearLayout) v.findViewById(R.id.card_bottom_layout);
        }
    }

    private class ImageLoader extends AsyncTask<Integer, Void, Integer> {

        ExercisesViewHolder exercisesViewHolder = new ExercisesViewHolder(itemView);

        @Override
        protected Integer doInBackground(Integer... params) {
            return params[0];
        }

        @Override
        protected void onPostExecute(Integer imageResourceId) {
            exercisesViewHolder.mImageExerciseTop.setImageResource(imageResourceId);
        }
    }


}