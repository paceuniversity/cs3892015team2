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

package pace.cs389.team2.quickfitness.model;

/**
 * Created by Luiz on 28/03/2015.
 */
public class ExercisesItem {

    private int id;
    private String name;
    private String description;
    private int icon;
    private int sets;
    private int reps;
    private int calories;
    private String videoAnimation;
    private CategoryItem category;


    public ExercisesItem(String name, String description, int icon) {
        this.name = name;
        this.description = description;
        this.icon = icon;
    }

    public ExercisesItem(String name, String description, int icon, int sets, int reps, int calories, String videoAnimation, CategoryItem category) {
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.sets = sets;
        this.reps = reps;
        this.calories = calories;
        this.videoAnimation = videoAnimation;
        this.category = category;
    }

    public ExercisesItem(int id, String name, String description, int icon, int sets, int reps, int calories, String videoAnimation, CategoryItem category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.sets = sets;
        this.reps = reps;
        this.calories = calories;
        this.videoAnimation = videoAnimation;
        this.category = category;
    }


    public ExercisesItem(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getVideoAnimation() {
        return videoAnimation;
    }

    public void setVideoAnimation(String videoAnimation) {
        this.videoAnimation = videoAnimation;
    }

    public CategoryItem getCategory() {
        return category;
    }

    public void setCategory(CategoryItem category) {
        this.category = category;
    }
}
