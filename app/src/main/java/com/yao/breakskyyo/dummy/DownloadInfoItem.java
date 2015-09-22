package com.yao.breakskyyo.dummy;

/**
 * 项目名称：BreakSkyYo
 * 类描述：
 * 创建人：yoyo
 * 创建时间：2015/9/22 22:20
 * 修改人：yoyo
 * 修改时间：2015/9/22 22:20
 * 修改备注：
 */
public class DownloadInfoItem {
    String name;
    String url;
    String  mima;

    public DownloadInfoItem(){

    }
    public DownloadInfoItem(String name,String url, String  mima){
        this.name=name;
        this.url=url;
        this.mima=mima;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMima() {
        return mima;
    }

    public void setMima(String mima) {
        this.mima = mima;
    }
}
