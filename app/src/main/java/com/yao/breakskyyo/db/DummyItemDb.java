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
    public static boolean save(DummyItem dummyItem,Context context){
        try{
            KJDB db = DbMain.getDb(context);
            db.save(dummyItem);
        }catch (Exception e){
            return false;
        }
        return true;
    }
    public static boolean saveList(List<DummyItem> listDummyItem,Context context){
        try{
            KJDB db = DbMain.getDb(context);
            db.save(listDummyItem);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public static List<DummyItem>  findList(Context context){
        List<DummyItem> list;
        try{
            KJDB db = DbMain.getDb(context);
            list=db.findAll(DummyItem.class,"saveDate desc ");
        }catch (Exception e){
            return null;
        }
        return list;

    }

    public static boolean delete(String id,Context context){
        try{
            KJDB db = DbMain.getDb(context);
            db.deleteById(DummyItem.class, id);
        }catch (Exception e){
            return false;
        }
        return true;
    }
}
