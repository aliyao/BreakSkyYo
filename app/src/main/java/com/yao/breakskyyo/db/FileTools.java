package com.yao.breakskyyo.db;

import android.os.Environment;
import android.util.Log;

import com.sina.cloudstorage.auth.AWSCredentials;
import com.sina.cloudstorage.auth.BasicAWSCredentials;
import com.sina.cloudstorage.services.scs.SCS;
import com.sina.cloudstorage.services.scs.SCSClient;
import com.sina.cloudstorage.services.scs.model.S3Object;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Created by nideyoyo on 2015/10/12.
 */
public class FileTools {
    String accessKey = "yga1pjhy4pfvhhPuLtZq";
    String accessSecret = "88b2ec4e44dab198e83467741913815e9ef73711";
    AWSCredentials credentials = new BasicAWSCredentials(accessKey,accessSecret);
    SCS conn = new SCSClient(credentials);
    public final static String objectFilePathSinaJsonMain = "/json/jsonMain";
    public final static  String objectFilePathSinaJsonInfo = "/json/jsonInfo";

   /* public void main(Context mContext){
        check(0);
        int versionCode= (int)AppInfoUtil.getVersionCode(mContext);
        String basePath =getExternalStorageBasePath();
        if(TextUtils.isEmpty(basePath)){
            return;
        }
        String versionCodeSina= ReadTxtFile(basePath + "/" + objectFilePathSinaJsonMain);
        if(versionCode!=0&& !TextUtils.isEmpty(versionCodeSina)&&versionCodeSina.equals(versionCode+"")){
            check(1);
            Init.InitSinaInfoHttpUrlJson();
        }
    }*/

    public int check(int type){
        int bool=0;
        //下载Object
        String objectFilePath = "/json/jsonMain";
        switch (type){
            case 1:
                objectFilePath =objectFilePathSinaJsonInfo;
               break;
        }
        File destFile = null;
        String basePath =getExternalStorageBasePath();
        if(basePath!=null){
            destFile = new File(basePath+"/"+objectFilePath);
            if(destFile.exists()){
                destFile.delete();
            }
            S3Object s3Obj = conn.getObject("breakskyyo", objectFilePath);
            InputStream in = s3Obj.getObjectContent();
            byte[] buf = new byte[1024];
            OutputStream out = null;
            try {
                out = new FileOutputStream(destFile);
                int count;
                while( (count = in.read(buf)) != -1)
                {
                    if( Thread.interrupted() )
                    {
                        throw new InterruptedException();
                    }
                    out.write(buf, 0, count);
                }


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally{
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else{
            bool=2;
        }
        return bool;
    }

    public static String ReadTxtFile(String strFilePath)
    {
        String path = strFilePath;
        String content = ""; //文件内容字符串
        //打开文件
        File file = new File(path);
        //如果path是传递过来的参数，可以做一个非目录的判断
        if (file.isDirectory())
        {
            Log.d("TestFile", "The File doesn't not exist.");
        }
        else
        {
            try {
                InputStream instream = new FileInputStream(file);
                if (instream != null)
                {
                    InputStreamReader inputreader = new InputStreamReader(instream);
                    BufferedReader buffreader = new BufferedReader(inputreader);
                    String line;
                    //分行读取
                    while (( line = buffreader.readLine()) != null) {
                        content += line + "\n";
                    }
                    instream.close();
                }
            }
            catch (java.io.FileNotFoundException e)
            {
                Log.d("TestFile", "The File doesn't not exist.");
            }
            catch (IOException e)
            {
                Log.d("TestFile", e.getMessage());
            }
        }
        return content;
    }

    /**
     * 获取存储文件的根路径
     * @return
     */
    public static   String getExternalStorageBasePath(){
        if(isExternalStorageWriteable()){
            File file = new File(Environment.getExternalStorageDirectory()+"/breakskyyo/");
            file.mkdirs();
            return file.getAbsolutePath();
        }
        return null;
    }

    /**
     * 判断sd卡是否可写
     * @return
     */
    private static boolean isExternalStorageWriteable(){
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)){
            return true;
        }

        return false;
    }
}
