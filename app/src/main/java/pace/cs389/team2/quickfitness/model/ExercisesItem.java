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

import java.io.Serializable;

public class ExercisesItem implements Serializable {

    private int id;
    private String name;
    private String description;
    private int icon;
    private int sets;
    private int reps;
    private int calories;
    private String videoAnimation;
    private int categoryKey;


    public ExercisesItem(String name, String description, int icon, int sets, int reps, int calories, String videoAnimation, int category) {
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.sets = sets;
        this.reps = reps;
        this.calories = calories;
        this.videoAnimation = videoAnimation;
        this.categoryKey = category;
    }

    public ExercisesItem(int id, String name, String description, int icon, int sets, int reps, int calories, String videoAnimation, int category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.sets = sets;
        this.reps = reps;
        this.calories = calories;
        this.videoAnimation = videoAnimation;
        this.categoryKey = category;
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


    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getSets() {
        return sets;
    }


    public int getReps() {
        return reps;
    }


    public int getCalories() {
        return calories;
    }


    public String getVideoAnimation() {
        return videoAnimation;
    }


    public int getCategoryKey() {
        return categoryKey;
    }

}
