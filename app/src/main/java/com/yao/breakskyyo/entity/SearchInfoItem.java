package com.yao.breakskyyo.entity;

/**
 * 项目名称：BreakSkyYo
 * 类描述：
 * 创建人：yoyo
 * 创建时间：2016/3/23 20:49
 * 修改人：yoyo
 * 修改时间：2016/3/23 20:49
 * 修改备注：
 */
public class SearchInfoItem {
    String id;
    String hrefStr;
    String title;
    String imgUrl;
    String releaseTime;
    String tag;
    String score;
    String downloadNum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHrefStr() {
        return hrefStr;
    }

    public void setHrefStr(String hrefStr) {
        this.hrefStr = hrefStr;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getDownloadNum() {
        return downloadNum;
    }

    public void setDownloadNum(String downloadNum) {
        this.downloadNum = downloadNum;
    }
}
