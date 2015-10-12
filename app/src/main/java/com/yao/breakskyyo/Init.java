package com.yao.breakskyyo;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.yao.breakskyyo.db.FileTools;
import com.yao.breakskyyo.dummy.HttpUrlJson;

/**
 * Created by nideyoyo on 2015/10/12.
 */
public class Init {
    private static HttpUrlJson httpInfo;
    public static void InitSinaInfoHttpUrlJson(){
        String basePath = FileTools.getExternalStorageBasePath();
        if(!TextUtils.isEmpty(basePath)){
            String jsonMain=FileTools.ReadTxtFile(basePath + "/" + FileTools.objectFilePathSinaJsonMain);
            if(!TextUtils.isEmpty(jsonMain)){
                try {
                    HttpUrlJson mHttp= JSON.parseObject(jsonMain, HttpUrlJson.class);
                    if(mHttp!=null){
                        httpInfo= mHttp;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }
        httpInfo=new HttpUrlJson();
    }
    public static HttpUrlJson getHttpInfo(){
        if(httpInfo==null){
            InitSinaInfoHttpUrlJson();
        }
        return httpInfo;
    }
}
