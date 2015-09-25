package com.yao.breakskyyo.tools;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import java.io.File;

/**
 * 项目名称：BreakSkyYo
 * 类描述：
 * 创建人：yoyo
 * 创建时间：2015/9/25 20:53
 * 修改人：yoyo
 * 修改时间：2015/9/25 20:53
 * 修改备注：
 */
public class DownloadReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        DownloadManager manager = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
        if(DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())){
            DownloadManager.Query query = new DownloadManager.Query();
            //在广播中取出下载任务的id
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
            query.setFilterById(id);
            Cursor c = manager.query(query);
            if(c.moveToFirst()) {
                //获取文件下载路径
                String filename = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
                //如果文件名不为空，说明已经存在了，拿到文件名想干嘛都好
                if(filename != null){
                    File _file =  new File(filename);
                    if(_file!=null&&_file.exists()){
                        Intent intentOpen = new Intent();
                        intentOpen.setAction(Intent.ACTION_VIEW);
                        intentOpen.setDataAndType(Uri.fromFile(_file), "application/vnd.android.package-archive");
                        intentOpen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//以新压入栈
                        intentOpen.addCategory(Intent.CATEGORY_DEFAULT);
                        context.startActivity(intentOpen);
                    }

                }
            }
        }/*else if(DownloadManager.ACTION_NOTIFICATION_CLICKED.equals(intent.getAction())){
            long[] ids = intent.getLongArrayExtra(DownloadManager.EXTRA_NOTIFICATION_CLICK_DOWNLOAD_IDS);
            //点击通知栏取消下载
            manager.remove(ids);
        }*/

    }

}
