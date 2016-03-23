package com.yao.breakskyyo.entity;

import java.util.List;

/**
 * 项目名称：BreakSkyYo
 * 类描述：
 * 创建人：yoyo
 * 创建时间：2016/3/23 17:53
 * 修改人：yoyo
 * 修改时间：2016/3/23 17:53
 * 修改备注：
 */
public class VideoInfo {
    String title;
    String year;
    String tag;
    String imgUrl;
    String content;
    String keyWord;
    String filmUrlTitle;
    String filmUrl;
    String downloadTitle;
    List<DownloadItem> childDownload;
    String childRecommend;
    String childNew;
    String childSelect;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getFilmUrlTitle() {
        return filmUrlTitle;
    }

    public void setFilmUrlTitle(String filmUrlTitle) {
        this.filmUrlTitle = filmUrlTitle;
    }

    public String getFilmUrl() {
        return filmUrl;
    }

    public void setFilmUrl(String filmUrl) {
        this.filmUrl = filmUrl;
    }

    public String getDownloadTitle() {
        return downloadTitle;
    }

    public void setDownloadTitle(String downloadTitle) {
        this.downloadTitle = downloadTitle;
    }

    public String getChildRecommend() {
        return childRecommend;
    }

    public void setChildRecommend(String childRecommend) {
        this.childRecommend = childRecommend;
    }

    public String getChildNew() {
        return childNew;
    }

    public void setChildNew(String childNew) {
        this.childNew = childNew;
    }

    public String getChildSelect() {
        return childSelect;
    }

    public void setChildSelect(String childSelect) {
        this.childSelect = childSelect;
    }

    public List<DownloadItem> getChildDownload() {
        return childDownload;
    }

    public void setChildDownload(List<DownloadItem> childDownload) {
        this.childDownload = childDownload;
    }
}
