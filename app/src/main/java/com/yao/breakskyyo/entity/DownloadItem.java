package com.yao.breakskyyo.entity;

/**
 * 项目名称：BreakSkyYo
 * 类描述：
 * 创建人：yoyo
 * 创建时间：2016/3/23 18:05
 * 修改人：yoyo
 * 修改时间：2016/3/23 18:05
 * 修改备注：
 */
public class DownloadItem {
    String title;
    String url;
    int type;
    String baiduPsw;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBaiduPsw() {
        return baiduPsw;
    }

    public void setBaiduPsw(String baiduPsw) {
        this.baiduPsw = baiduPsw;
    }
}
