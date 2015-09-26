package com.yao.breakskyyo.net;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yao.breakskyyo.dummy.InfoUpdateApk;
import com.yao.breakskyyo.dummy.RootJsonInfo;
import com.yao.breakskyyo.tools.ACacheUtil;

import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.utils.KJLoger;

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
        KJHttp kjh = new KJHttp();
        kjh.get(HttpUrl.UpdateApp, new HttpCallBack() {
            @Override
            public void onPreStart() {
                super.onPreStart();
                KJLoger.debug("在请求开始之前调用");
            }

            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                //ViewInject.longToast("请求成功");
                if (!TextUtils.isEmpty(t)) {
                    try {
                        RootJsonInfo<InfoUpdateApk> netRootJsonInfo = JSON.parseObject(t, new TypeReference<RootJsonInfo<InfoUpdateApk>>() {
                        });
                        if (netRootJsonInfo.isHttpSuccess()) {
                            ACacheUtil.put(context, ACacheUtil.UpdateJson, JSON.toJSONString(netRootJsonInfo.getInfo()));
                        }
                    } catch (Exception e) {

                    }
                }
                if (handle != null) {
                    handle.sendEmptyMessage(1);
                }

            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
                KJLoger.debug("exception:" + strMsg);
                if (handle != null) handle.sendEmptyMessage(0);
            }


            @Override
            public void onFinish() {
                super.onFinish();
                KJLoger.debug("请求完成，不管成功或者失败都会调用");
            }
        });

    }
}
