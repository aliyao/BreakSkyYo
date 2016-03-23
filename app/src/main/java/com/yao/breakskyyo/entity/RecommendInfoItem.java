package com.yao.breakskyyo.entity;

/**
 * 项目名称：BreakSkyYo
 * 类描述：
 * 创建人：yoyo
 * 创建时间：2016/3/23 20:09
 * 修改人：yoyo
 * 修改时间：2016/3/23 20:09
 * 修改备注：
 */
public class RecommendInfoItem {
    String id;
    String hrefStr;
    String title;
    String imgUrl;
    String tag;
    String score;
    String type;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
