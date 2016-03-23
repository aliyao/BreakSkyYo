package com.yao.breakskyyo.entity;

import java.util.List;

/**
 * 项目名称：BreakSkyYo
 * 类描述：
 * 创建人：yoyo
 * 创建时间：2016/3/23 20:05
 * 修改人：yoyo
 * 修改时间：2016/3/23 20:05
 * 修改备注：
 */
public class RecommendInfo {
    List<RecommendInfoItem> bannerList;
    List<RecommendInfoItem> hotList;
    List<RecommendInfoItem> newList;

    public List<RecommendInfoItem> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<RecommendInfoItem> bannerList) {
        this.bannerList = bannerList;
    }

    public List<RecommendInfoItem> getHotList() {
        return hotList;
    }

    public void setHotList(List<RecommendInfoItem> hotList) {
        this.hotList = hotList;
    }

    public List<RecommendInfoItem> getNewList() {
        return newList;
    }

    public void setNewList(List<RecommendInfoItem> newList) {
        this.newList = newList;
    }
}
