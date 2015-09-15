package com.yao.breakskyyo.tools;

import android.app.Activity;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 项目名称：BreakSky
 * 类描述：
 * 创建人：yoyo
 * 创建时间：2015/8/25 16:55
 * 修改人：yoyo
 * 修改时间：2015/8/25 16:55
 * 修改备注：
 */
public class BSClick {
    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    public static void exitBy2Click(Activity mActivity) {
        Timer tExit;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(mActivity, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 1500); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            mActivity.finish();
            System.exit(0);
        }
    }
}
