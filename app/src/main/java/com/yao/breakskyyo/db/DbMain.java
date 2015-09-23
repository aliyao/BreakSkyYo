package com.yao.breakskyyo.db;

import android.content.Context;

import org.kymjs.kjframe.KJDB;

import java.security.PublicKey;

/**
 * 项目名称：BreakSkyYo
 * 类描述：
 * 创建人：yoyo
 * 创建时间：2015/9/23 15:04
 * 修改人：yoyo
 * 修改时间：2015/9/23 15:04
 * 修改备注：
 */
public class DbMain {
    static KJDB db;
    public static KJDB getDb(Context context){
        if (db==null){
            db = KJDB.create(context);
        }
        return db;
    }
}
