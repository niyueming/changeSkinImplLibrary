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

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.zhy.changeskin.SkinManager;
import com.zhy.changeskin.callback.ISkinChangingCallback;
import com.zhy.changeskin.utils.PrefUtils;

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
    private PrefUtils prefUtils;
    private boolean mIsDefaultMode = true;

    private SkinManagerImpl(Context context){
        manager();
        prefUtils = new PrefUtils(context.getApplicationContext());
    }

    private SkinManager manager() {
        if (mManager == null){
            mManager = SkinManager.getInstance();
        }
        return mManager;
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
        return manager();
    }

    @Override
    public void switchSkinMode(NOnSkinChangeListener listener) {
        mIsDefaultMode = !mIsDefaultMode;
        refreshSkin(listener);
    }

    @Override
    public void refreshSkin(final NOnSkinChangeListener listener) {
        if (mIsDefaultMode) {
            //恢复到默认皮肤
            restoreDefault(listener);
        } else if (prefUtils != null){
            if (!TextUtils.isEmpty(prefUtils.getPluginPath())){
                changeSkin(listener);
            }
        }
    }

    private void changeSkin(final NOnSkinChangeListener listener) {
        manager().changeSkin(prefUtils.getPluginPath(), prefUtils.getPluginPkgName(),prefUtils.getSuffix(), new ISkinChangingCallback() {
            @Override
            public void onStart() {
                System.out.println("换肤开始");
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                if (listener != null){
                    listener.onSkinChanged();
                }
            }
        });
    }

    @Override
    public void changeSkin(String skinPath, String skinPkgName, String suffix, NOnSkinChangeListener listener) {
        if (prefUtils != null){
            mIsDefaultMode = false;
            prefUtils.putPluginPath(skinPath);
            prefUtils.putPluginPkg(skinPkgName);
            prefUtils.putPluginSuffix(suffix);
            changeSkin(listener);
        }
    }

    @Override
    public void changeSkin(String suffix, NOnSkinChangeListener listener) {
        if (prefUtils != null){
            prefUtils.putPluginSuffix(suffix);
            refreshSkin(listener);
        }
    }

    @Override
    public void register(Activity activity) {
        manager().register(activity);
    }

    @Override
    public void unregister(Activity activity) {
        manager().unregister(activity);
    }

    @Override
    public void injectSkin(View view) {
        manager().injectSkin(view);
    }

    @Override
    public void restoreDefault(NOnSkinChangeListener listener) {
        if (prefUtils != null){
            manager().changeSkin(prefUtils.getSuffix());
            listener.onSkinChanged();
        }
    }

    @Override
    public void clear() {
        if (prefUtils != null){
            prefUtils.clear();
        }
        mIsDefaultMode = true;
        manager().removeAnySkin();
    }
}
