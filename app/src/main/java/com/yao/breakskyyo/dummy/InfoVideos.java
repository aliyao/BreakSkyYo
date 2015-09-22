package com.yao.breakskyyo.dummy;

import java.util.List;
import java.util.Map;

/**
 * 项目名称：BreakSkyYo
 * 类描述：
 * 创建人：yoyo
 * 创建时间：2015/9/16 23:24
 * 修改人：yoyo
 * 修改时间：2015/9/16 23:24
 * 修改备注：
 */
public class InfoVideos {
    String movie_title;
    String img_thumbnail;
    //String movie_payUrl;
    String movie_jvqing;
    String movie_payZaixian;
    String baiduPanUrl;
    String baiduPanName;
    String baiduPanUrlMima;
    List<Map<String,String>> regularChili;

    public String getMovie_title() {
        return movie_title;
    }

    public void setMovie_title(String movie_title) {
        this.movie_title = movie_title;
    }


    public String getImg_thumbnail() {
        return img_thumbnail;
    }

    public void setImg_thumbnail(String img_thumbnail) {
        this.img_thumbnail = img_thumbnail;
    }

   /* public String getMovie_payUrl() {
        return movie_payUrl;
    }

    public void setMovie_payUrl(String movie_payUrl) {
        this.movie_payUrl = movie_payUrl;
    }*/

    public String getMovie_jvqing() {
        return movie_jvqing;
    }

    public void setMovie_jvqing(String movie_jvqing) {
        this.movie_jvqing = movie_jvqing;
    }

    public String getMovie_payZaixian() {
        return movie_payZaixian;
    }

    public void setMovie_payZaixian(String movie_payZaixian) {
        this.movie_payZaixian = movie_payZaixian;
    }

    public String getBaiduPanUrl() {
        return baiduPanUrl;
    }

    public void setBaiduPanUrl(String baiduPanUrl) {
        this.baiduPanUrl = baiduPanUrl;
    }

    public String getBaiduPanUrlMima() {
        return baiduPanUrlMima;
    }

    public void setBaiduPanUrlMima(String baiduPanUrlMima) {
        this.baiduPanUrlMima = baiduPanUrlMima;
    }

    public String getBaiduPanName() {
        return baiduPanName;
    }

    public void setBaiduPanName(String baiduPanName) {
        this.baiduPanName = baiduPanName;
    }

    public List<Map<String, String>> getRegularChili() {
        return regularChili;
    }

    public void setRegularChili(List<Map<String, String>> regularChili) {
        this.regularChili = regularChili;
    }
}
