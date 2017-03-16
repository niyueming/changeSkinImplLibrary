/*
 * Copyright (c) 2017  Ni YueMing<niyueming@163.com>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 *
 */

package net.nym.changeskinimpllibrary.impl;

import android.content.Context;

import com.zhy.changeskin.SkinManager;

import net.nym.changeskinlibrary.operation.NOnSkinChangeListener;
import net.nym.changeskinlibrary.operation.NSkinManager;

/**
 * @author niyueming
 * @date 2017-03-15
 * @time 15:29
 */

public class SkinManagerImpl implements NSkinManager<SkinManager> {
    private static NSkinManager<SkinManager> my;
    private SkinManager mManager;

    private SkinManagerImpl(Context context){
        init();
    }

    private void init() {
        if (mManager == null){
            mManager = SkinManager.getInstance();
        }
    }

    public static NSkinManager<SkinManager> getInstance(Context context){
        if (my == null){
            synchronized (SkinManager.class){
                if (my == null){
                    my = new SkinManagerImpl(context);
                }
            }
        }
        return my;
    }

    @Override
    public SkinManager getManager() {
        init();
        return mManager;
    }

    @Override
    public void switchSkinMode(NOnSkinChangeListener listener) {

    }

    @Override
    public void refreshSkin(NOnSkinChangeListener listener) {

    }

    @Override
    public void register(Context context) {

    }

    @Override
    public void unregister(Context context) {

    }
}
