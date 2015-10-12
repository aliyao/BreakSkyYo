package com.yao.breakskyyo;

import android.app.Application;

/**
 * Created by nideyoyo on 2015/10/12.
 */
public class MyApplication extends Application
{
    private static MyApplication instance;
    public MyApplication() {
    }

    // 单例模式获取唯一的MyApplication实例
    public static MyApplication getInstance() {
        if (null == instance) {
            instance = MyApplication.getInstance();
        }
        return instance;
    }
    /*
     * android应用程序真正入口。
     * 此方法在所有activity，servie，receiver组件之前调用
     * */
    @Override
    public void onCreate()
    {
        super.onCreate();//必须调用父类方法\
        instance = this;
    }
    /**
     * 此方法方便在那些没有context对象的类中使用
     * @return MyApp实例
     */
    public static MyApplication getApplicationInstance()
    {
        return instance;
    }
}
