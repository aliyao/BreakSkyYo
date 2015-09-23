package com.yao.breakskyyo.db;

import android.content.Context;

import com.yao.breakskyyo.dummy.DummyItem;

import org.kymjs.kjframe.KJDB;

import java.util.List;

/**
 * 项目名称：BreakSkyYo
 * 类描述：
 * 创建人：yoyo
 * 创建时间：2015/9/23 14:58
 * 修改人：yoyo
 * 修改时间：2015/9/23 14:58
 * 修改备注：
 */
public class DummyItemDb {
    public static void saveList(List<DummyItem> listDummyItem,Context context){
        KJDB db = DbMain.getDb(context);
        db.save(listDummyItem);
    }

    public static List<DummyItem>  findList(Context context){
        KJDB db = DbMain.getDb(context);
       return db.findAll(DummyItem.class);
    }
}
