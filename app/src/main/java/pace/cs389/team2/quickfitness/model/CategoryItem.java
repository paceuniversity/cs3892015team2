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

/**
 * Created by Luiz on 30/03/2015.
 */
public class CategoryItem implements Serializable {

    private int _id;
    private int icon;
    private String name;

    public CategoryItem(int id, String name, int icon) {
        this._id = id;
        this.name = name;
        this.icon = icon;
    }

    public CategoryItem(int id) {
        this._id = id;
    }

    public CategoryItem(String name) {
        this.name = name;
    }

    public CategoryItem(String name, int icon) {
        this.name = name;
        this.icon = icon;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
