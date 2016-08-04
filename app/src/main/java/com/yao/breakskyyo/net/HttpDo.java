package com.yao.breakskyyo.net;

import android.content.Context;
import android.os.Handler;

import com.alibaba.fastjson.JSON;
import com.yao.breakskyyo.entity.UpdateApp;
import com.yao.breakskyyo.tools.ACacheUtil;
import com.yao.breakskyyo.tools.AppInfoUtil;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * 项目名称：BreakSkyYo
 * 类描述：
 * 创建人：yoyo
 * 创建时间：2015/9/26 13:10
 * 修改人：yoyo
 * 修改时间：2015/9/26 13:10
 * 修改备注：
 */
public class HttpDo {

    public static void updateApp(final Context context, final Handler handle) {
        int versionCode = AppInfoUtil.getVersionCode(context);
        if (versionCode > 0) {
            BmobQuery<UpdateApp> query = new BmobQuery<>();
            //条件：版本号大于versionCode
            query.addWhereGreaterThan("versionCode", versionCode);
            query.order("-versionCode");
           /* // 根据score字段升序显示数据
            query.order("score");
            // 根据score字段降序显示数据
            query.order("-score");
            // 多个排序字段可以用（，）号分隔
            query.order("-score,createdAt");*/
            //返回50条数据，如果不加上这条语句，默认返回10条数据
            query.setLimit(1);
            //执行查询方法
            query.findObjects(context, new FindListener<UpdateApp>() {
                @Override
                public void onSuccess(List<UpdateApp> object) {
                    if (object.size() > 0) {
                        UpdateApp mUpdateApp = object.get(object.size() - 1);
                        ACacheUtil.put(context, ACacheUtil.UpdateAppJson, JSON.toJSONString(mUpdateApp));
                    }else{
                        ACacheUtil.put(context, ACacheUtil.UpdateAppJson, null);
                    }
                    if (handle != null) {
                        handle.sendEmptyMessage(1);
                    }
                }

                @Override
                public void onError(int code, String msg) {
                    if (handle != null) handle.sendEmptyMessage(0);
                }
            });
        }
    }
}
