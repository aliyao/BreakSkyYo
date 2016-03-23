package com.yao.breakskyyo.dummy;

import android.text.TextUtils;

import com.yao.breakskyyo.net.DefaultHttpUrl;

import java.util.Date;

/**
 * Created by nideyoyo on 2015/10/12.
 */
public class JsonInfo {
    int newVersionCode;
    int versionCode;
    Date updateAt;
    String mainList;
    String updateApp;
    String updateAppWeb;
    String hellpUrl;
    String searchInfo;
    String year;
    String rating;
    String country;
    String tags;
    String page;
    String id97Url;
    String urlBaiduPan;
    String searchBaiduUrl;
    String videosResourceIdUrl;
    String ucUrl;

    public String getMainList() {
        return TextUtils.isEmpty(mainList)? DefaultHttpUrl.MainList:mainList;
    }

    public void setMainList(String mainList) {
        this.mainList = mainList;
    }

    public String getUpdateApp() {
        return TextUtils.isEmpty(updateApp)? DefaultHttpUrl.UpdateApp:updateApp;
    }

    public void setUpdateApp(String updateApp) {
        this.updateApp = updateApp;
    }

    public String getUpdateAppWeb() {
        return TextUtils.isEmpty(updateAppWeb)? DefaultHttpUrl.UpdateAppWeb:updateAppWeb;
    }

    public void setUpdateAppWeb(String updateAppWeb) {
        this.updateAppWeb = updateAppWeb;
    }

    public String getHellpUrl() {
        return TextUtils.isEmpty(hellpUrl)? DefaultHttpUrl.HellpUrl:hellpUrl;
    }

    public void setHellpUrl(String hellpUrl) {
        this.hellpUrl = hellpUrl;
    }

    public String getSearchInfo() {
        return TextUtils.isEmpty(searchInfo)? DefaultHttpUrl.SearchInfo:searchInfo;
    }

    public void setSearchInfo(String searchInfo) {
        this.searchInfo = searchInfo;
    }

    public String getYear() {
        return TextUtils.isEmpty(year)? DefaultHttpUrl.year:year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRating() {
        return TextUtils.isEmpty(rating)? DefaultHttpUrl.rating:rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getCountry() {
        return TextUtils.isEmpty(country)? DefaultHttpUrl.country:country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTags() {
        return TextUtils.isEmpty(tags)? DefaultHttpUrl.tags:tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getPage() {
        return TextUtils.isEmpty(page)? DefaultHttpUrl.page:page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getId97Url() {
        return TextUtils.isEmpty(id97Url)? DefaultHttpUrl.id97Url:id97Url;
    }

    public void setId97Url(String id97Url) {
        this.id97Url = id97Url;
    }

    public String getUrlBaiduPan() {
        return TextUtils.isEmpty(urlBaiduPan)? DefaultHttpUrl.urlBaiduPan:urlBaiduPan;
    }

    public void setUrlBaiduPan(String urlBaiduPan) {
        this.urlBaiduPan = urlBaiduPan;
    }

    public String getSearchBaiduUrl() {
        return TextUtils.isEmpty(searchBaiduUrl)? DefaultHttpUrl.SearchBaiduUrl:searchBaiduUrl;
    }

    public void setSearchBaiduUrl(String searchBaiduUrl) {
        this.searchBaiduUrl = searchBaiduUrl;
    }

    public String getVideosResourceIdUrl() {
          return TextUtils.isEmpty(videosResourceIdUrl)? DefaultHttpUrl.videosResourceIdUrl:videosResourceIdUrl;
    }

    public void setVideosResourceIdUrl(String videosResourceIdUrl) {
        this.videosResourceIdUrl = videosResourceIdUrl;
    }

    public String getUcUrl() {
        return TextUtils.isEmpty(ucUrl)? DefaultHttpUrl.UCUrl:ucUrl;
    }

    public void setUcUrl(String ucUrl) {
        this.ucUrl = ucUrl;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public int getNewVersionCode() {
        return newVersionCode;
    }

    public void setNewVersionCode(int newVersionCode) {
        this.newVersionCode = newVersionCode;
    }


}
