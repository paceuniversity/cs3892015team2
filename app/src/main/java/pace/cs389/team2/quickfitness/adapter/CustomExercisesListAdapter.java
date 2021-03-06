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
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import pace.cs389.team2.quickfitness.ActivityExerciseDetails;
import pace.cs389.team2.quickfitness.R;
import pace.cs389.team2.quickfitness.data.QuickFitnessDAO;
import pace.cs389.team2.quickfitness.model.CategoryItem;
import pace.cs389.team2.quickfitness.model.ExercisesItem;

public class CustomExercisesListAdapter extends RecyclerView.Adapter<CustomExercisesListAdapter.ExercisesViewHolder> implements View.OnClickListener {

    public static final String EXERCISE_TAG = "exercise_ref";
    static Bitmap exerciseIcon;
    static Bitmap exerciseIconResized;
    ExercisesItem exercisesItem;
    RecyclerView mRecyclerView;
    private List<ExercisesItem> mExercisesList;
    private View itemView;
    private int lastPosition = -1;
    private Activity mActivity;

    public CustomExercisesListAdapter(Activity mActivity, List<ExercisesItem> exercisesList, RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
        this.mExercisesList = exercisesList;
        this.mActivity = mActivity;
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
        itemView.setOnClickListener(this);

        return new ExercisesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ExercisesViewHolder exercisesViewHolder, int i) {
        exercisesItem = mExercisesList.get(i);

        if (exercisesItem.getCategoryKey() == 2) {
            exercisesViewHolder.mCardTopLayout.setBackgroundResource(R.color.material_green);
        } else if (exercisesItem.getCategoryKey() == 3) {
            exercisesViewHolder.mCardTopLayout.setBackgroundResource(R.color.material_orange);
        } else if (exercisesItem.getCategoryKey() == 4) {
            exercisesViewHolder.mCardTopLayout.setBackgroundResource(R.color.material_purple);
        } else if (exercisesItem.getCategoryKey() == 5) {
            exercisesViewHolder.mCardTopLayout.setBackgroundResource(R.color.material_blue_grey);

        } else {
            exercisesViewHolder.mCardTopLayout.setBackgroundResource(R.color.background_list_item_gray);
        }

        exerciseIcon = BitmapFactory.decodeResource(itemView.getResources(), exercisesItem.getIcon());

        exerciseIconResized = Bitmap.createScaledBitmap(exerciseIcon,
                100, 100, false);

        if (exerciseIconResized != null) {
            exercisesViewHolder.mImageExerciseTop.setImageBitmap(exerciseIconResized);
        }


        exercisesViewHolder.mExerciseTitle.setText(exercisesItem.getName());

        CategoryItem category = QuickFitnessDAO.getInstance(itemView.getContext()).categoryById(exercisesItem.getCategoryKey());
        exercisesViewHolder.mExerciseCategory.setText(category.getName());

        // ImageLoader task = new ImageLoader();
        // task.execute(exercisesItem.getIcon());

        //setAnimation(exercisesViewHolder.mCardItemLayout, i);

    }

    @Override
    public void onClick(final View view) {
        int itemPosition = mRecyclerView.getChildPosition(view);
        ExercisesItem mItem = mExercisesList.get(itemPosition);
        Intent intent = new Intent(itemView.getContext(), ActivityExerciseDetails.class);
        intent.putExtra(EXERCISE_TAG, mItem);
        itemView.getContext().startActivity(intent);

    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(itemView.getContext(), android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public static class ExercisesViewHolder extends RecyclerView.ViewHolder {
        protected TextView mExerciseTitle;
        protected TextView mExerciseCategory;
        protected ImageView mImageExerciseTop;
        protected LinearLayout mCardTopLayout;
        protected CardView mCardItemLayout;

        public ExercisesViewHolder(View v) {
            super(v);
            mExerciseTitle = (TextView) v.findViewById(R.id.txt_exercise_title);
            mExerciseCategory = (TextView) v.findViewById(R.id.txt_exercise_category);
            mImageExerciseTop = (ImageView) v.findViewById(R.id.img_exercise_picture);
            mCardTopLayout = (LinearLayout) v.findViewById(R.id.card_top_layout);
            mCardItemLayout = (CardView) v.findViewById(R.id.card_view1);

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