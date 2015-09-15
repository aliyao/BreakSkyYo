package com.yao.breakskyyo.tools;

/**
 * 项目名称：BreakSkyYo
 * 类描述：
 * 创建人：yoyo
 * 创建时间：2015/9/15 19:48
 * 修改人：yoyo
 * 修改时间：2015/9/15 19:48
 * 修改备注：
 */
public class StringDo {
    public static String removeNull(Object mObject){
        if(mObject==null){
            return "";
        }
        return (String)mObject;
    }
}
