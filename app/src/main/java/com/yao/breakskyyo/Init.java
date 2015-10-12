package com.yao.breakskyyo;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.yao.breakskyyo.db.FileTools;
import com.yao.breakskyyo.dummy.JsonInfo;
import com.yao.breakskyyo.tools.ACacheUtil;

/**
 * Created by nideyoyo on 2015/10/12.
 */
public class Init {
    private static JsonInfo httpInfo;
    public static void InitSinaInfoHttpUrlJson(){
        String basePath = FileTools.getExternalStorageBasePath();
        if(!TextUtils.isEmpty(basePath)){
            String jsonMain=FileTools.ReadTxtFile(basePath + "/" + FileTools.objectFilePathSinaJsonMain);
            if(!TextUtils.isEmpty(jsonMain)){
                try {
                    JsonInfo mHttp= JSON.parseObject(jsonMain, JsonInfo.class);
                    if(mHttp!=null){
                        httpInfo= mHttp;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }
        httpInfo=new JsonInfo();
    }
    public static JsonInfo getHttpInfo(){
        if(httpInfo==null){
            //InitSinaInfoHttpUrlJson();
            InitBmobHttpUrlJson();
        }
        return httpInfo;
    }


    public static void InitBmobHttpUrlJson(){
            String httpUrlJsonStr= (String)ACacheUtil.getAsObject(MyApplication.getInstance(),ACacheUtil.HttpUrlJson);
            if(!TextUtils.isEmpty(httpUrlJsonStr)){
                try {
                    JsonInfo mHttp= JSON.parseObject(httpUrlJsonStr, JsonInfo.class);
                    if(mHttp!=null){
                        httpInfo= mHttp;
                        return;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

        httpInfo=new JsonInfo();
    }
}
