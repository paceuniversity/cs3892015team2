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
 * Created by Luiz on 25/03/2015.
 */
public class DrawerItem implements Serializable {

    private String mNameItem;
    private String mTitleItem;
    private int mIconRes;


    public DrawerItem(String mNameItem, int mIconRes) {
        super();
        this.mNameItem = mNameItem;
        this.mIconRes = mIconRes;
    }

    public DrawerItem(String mTitleItem) {
        this(null, 0);
        this.mTitleItem = mTitleItem;
    }


    public String getmNameItem() {
        return mNameItem;
    }

    public void setmNameItem(String mNameItem) {
        this.mNameItem = mNameItem;
    }

    public String getmTitleItem() {
        return mTitleItem;
    }

    public void setmTitleItem(String mTitleItem) {
        this.mTitleItem = mTitleItem;
    }

    public int getmIconRes() {
        return mIconRes;
    }

    public void setmIconRes(int mIconRes) {
        this.mIconRes = mIconRes;
    }
}
