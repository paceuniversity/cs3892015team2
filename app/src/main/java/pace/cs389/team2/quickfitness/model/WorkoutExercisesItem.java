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

public class WorkoutExercisesItem {

    private int workoutExerciseId;
    private int workoutId;
    private int exerciseId;


    public WorkoutExercisesItem(int workoutId, int exerciseId) {
        this.workoutId = workoutId;
        this.exerciseId = exerciseId;
    }

    public WorkoutExercisesItem(int workoutExerciseId, int workoutId, int exerciseId) {
        this.workoutExerciseId = workoutExerciseId;
        this.workoutId = workoutId;
        this.exerciseId = exerciseId;
    }

    public int getWorkoutExerciseId() {
        return workoutExerciseId;
    }

    public int getWorkoutId() {
        return workoutId;
    }


    public int getExerciseId() {
        return exerciseId;
    }

}
