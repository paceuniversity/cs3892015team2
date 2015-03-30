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

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import pace.cs389.team2.quickfitness.R;
import pace.cs389.team2.quickfitness.model.ExercisesItem;

/**
 * Created by Luiz on 28/03/2015.
 */
public class CustomExercisesListAdapter extends RecyclerView.Adapter<CustomExercisesListAdapter.ExercisesViewHolder> {

    private List<ExercisesItem> mExercisesList;
    private View itemView;

    public CustomExercisesListAdapter(List<ExercisesItem> contactList) {
        this.mExercisesList = contactList;
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
        ExercisesItem exercisesItem = mExercisesList.get(i);

        if (exercisesItem.getName().equals("Treadmill")) {
            exercisesViewHolder.mCardBottomLayout.setBackgroundResource(R.color.material_green);
        } else if (exercisesItem.getName().equals("Leg Press")) {
            exercisesViewHolder.mCardBottomLayout.setBackgroundResource(R.color.material_orange);
        } else {
            exercisesViewHolder.mCardBottomLayout.setBackgroundResource(R.color.material_blue_grey);

        }

        exercisesViewHolder.mExerciseTitle.setText(exercisesItem.getName());
        exercisesViewHolder.mExerciseDescription.setText(exercisesItem.getDescription());
        exercisesViewHolder.mImageExerciseTop.setImageResource(exercisesItem.getIcon());

        //Bitmap icon = BitmapFactory.decodeResource(itemView.getResources(),
        //        exercisesItem.getIcon());

        //Bitmap mIconResized = Bitmap.createScaledBitmap(icon, 350, 160, true);

        //BitmapResizeUtils.getResizedBitmap(icon, 350, 160);


        //exercisesViewHolder.mImageExerciseTop.setImageBitmap(BitmapUtils.decodeSampledBitmapFromResource(itemView.getResources(),
        //       exercisesItem.getIcon(), 350, 160));
    }

    public static class ExercisesViewHolder extends RecyclerView.ViewHolder {
        protected TextView mExerciseTitle;
        protected TextView mExerciseDescription;
        protected ImageView mImageExerciseTop;
        protected LinearLayout mCardBottomLayout;

        public ExercisesViewHolder(View v) {
            super(v);
            mExerciseTitle = (TextView) v.findViewById(R.id.txt_exercise_title);
            mExerciseDescription = (TextView) v.findViewById(R.id.txt_exercise_description);
            mImageExerciseTop = (ImageView) v.findViewById(R.id.img_exercise_picture);
            mCardBottomLayout = (LinearLayout) v.findViewById(R.id.card_bottom_layout);
        }
    }
}