package com.yao.breakskyyo.tools;

import org.kymjs.kjframe.KJBitmap;

/**
 * 项目名称：BreakSky
 * 类描述：
 * 创建人：yoyo
 * 创建时间：2015/9/12 17:20
 * 修改人：yoyo
 * 修改时间：2015/9/12 17:20
 * 修改备注：
 */
public class YOBitmap {
    static KJBitmap mKJBitmap;

    public static KJBitmap getmKJBitmap() {
        if (mKJBitmap==null){
            mKJBitmap =  new KJBitmap();
        }
        return mKJBitmap;
    }

    public static void setmKJBitmap(KJBitmap mKJBitmap) {
        YOBitmap.mKJBitmap = mKJBitmap;
    }
}
