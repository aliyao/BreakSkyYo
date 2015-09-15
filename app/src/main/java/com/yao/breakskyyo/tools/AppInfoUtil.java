package com.yao.breakskyyo.tools;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by yoyo on 2015/5/19.
 */
public class AppInfoUtil {
    public static String getVersionName(Context mContext) {
        PackageManager manager;

        PackageInfo info = null;

        manager = mContext.getPackageManager();

        try {

            info = manager.getPackageInfo(mContext.getPackageName(), 0);

        } catch (PackageManager.NameNotFoundException e) {

// TODO Auto-generated catch block

            e.printStackTrace();
            return null;

        }
        return info.versionName;
       /* info.versionCode;

        info.versionName;

        info.packageName;

        info.signatures;*/
    }

    public static Object getVersionCode(Context mContext) {
        PackageManager manager;

        PackageInfo info = null;

        manager = mContext.getPackageManager();

        try {

            info = manager.getPackageInfo(mContext.getPackageName(), 0);

        } catch (PackageManager.NameNotFoundException e) {

// TODO Auto-generated catch block

            e.printStackTrace();
            return 0;

        }
        return info.versionCode;
       /* info.versionCode;

        info.versionName;

        info.packageName;

        info.signatures;*/
    }
}
