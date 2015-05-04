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
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import pace.cs389.team2.quickfitness.R;
import pace.cs389.team2.quickfitness.data.QuickFitnessDAO;
import pace.cs389.team2.quickfitness.model.CategoryItem;
import pace.cs389.team2.quickfitness.model.ExercisesItem;

public class WorkoutCategoriesListAdapter extends RecyclerView.Adapter<WorkoutCategoriesListAdapter.ExercisesViewHolder> implements View.OnClickListener {

    CategoryItem categoryItem;
    RecyclerView mRecyclerView;
    Activity mActivity;
    private List<CategoryItem> mCategoriesList;
    private View itemView;

    public WorkoutCategoriesListAdapter(Activity mActivity, List<CategoryItem> mCategoriesList, RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
        this.mCategoriesList = mCategoriesList;
        this.mActivity = mActivity;
    }

    @Override
    public int getItemCount() {
        return mCategoriesList.size();
    }

    @Override
    public ExercisesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.workout_categories_list_adapter, viewGroup, false);
        itemView.setOnClickListener(this);

        return new ExercisesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ExercisesViewHolder exercisesViewHolder, int i) {
        categoryItem = mCategoriesList.get(i);

        List<ExercisesItem> exercisesItems = QuickFitnessDAO.getInstance(itemView.getContext()).listExercisesByCategory(categoryItem.get_id());

        exercisesViewHolder.mImageExerciseTop.setImageResource(exercisesItems.get(1).getIcon());


        exercisesViewHolder.mExerciseCategory.setText(categoryItem.getName());

    }

    @Override
    public void onClick(final View view) {
        //int itemPosition = mRecyclerView.getChildPosition(view);
        // categoryItem mItem = mExercisesList.get(itemPosition);
        //Intent intent = new Intent(itemView.getContext(), ActivityExerciseDetails.class);
        //intent.putExtra(EXERCISE_TAG, mItem);
        // itemView.getContext().startActivity(intent);

    }

    public static class ExercisesViewHolder extends RecyclerView.ViewHolder {
        protected TextView mExerciseCategory;
        protected ImageView mImageExerciseTop;
        protected LinearLayout mCardTopLayout;
        protected CardView mCardItemLayout;

        public ExercisesViewHolder(View v) {
            super(v);
            mExerciseCategory = (TextView) v.findViewById(R.id.txt_exercise_category);
            mImageExerciseTop = (ImageView) v.findViewById(R.id.img_exercise_picture);
            mCardTopLayout = (LinearLayout) v.findViewById(R.id.card_top_layout);
            mCardItemLayout = (CardView) v.findViewById(R.id.card_view1);

        }
    }


}