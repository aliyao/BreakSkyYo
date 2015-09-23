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
    int type;//1百度   2在线  3磁力

    public DownloadInfoItem(){

    }
    public DownloadInfoItem(String name,String url, String  mima,int type){
        this.name=name;
        this.url=url;
        this.mima=mima;
        this.type=type;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
