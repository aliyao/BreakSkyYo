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
    String findList;
    String updateApp;
    String updateAppWeb;
    String hellpUrl;
    String searchInfo;
    String year;
    String rating;
    String country;
    String tags;
    String page;
    String zhengZeItem;
    String zhengZeId;
    String zhengZeType;
    String zhengZeTag;
    String id97Url;
    String urlBaiduPan;
    String regularTable;
    String regularZaixianUrl;
    String regularBaidupanUrl;
    String regularBaidupanName;
    String regularBaidupanUrlMima;
    String regularChiliName;
    String yearRegular;
    String ratingRegular;
    String countryRegular;
    String tagsRegular;
    String zhengZeBanerItem;
    String zhengZeHotNewItem;
    String zhengZeHotHtml;
    String zhengZeNewHtml;
    String zhengZeSearch;
    String searchBaiduUrl;
    String videosResourceIdUrl;
    String ucUrl;

    public String getMainList() {
        return TextUtils.isEmpty(mainList)? DefaultHttpUrl.MainList:mainList;
    }

    public void setMainList(String mainList) {
        this.mainList = mainList;
    }

    public String getFindList() {
        return TextUtils.isEmpty(findList)? DefaultHttpUrl.FindList:findList;
    }

    public void setFindList(String findList) {
        this.findList = findList;
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

    public String getZhengZeItem() {
        return TextUtils.isEmpty(zhengZeItem)? DefaultHttpUrl.zhengZeItem:zhengZeItem;
    }

    public void setZhengZeItem(String zhengZeItem) {
        this.zhengZeItem = zhengZeItem;
    }

    public String getZhengZeId() {
        return TextUtils.isEmpty(zhengZeId)? DefaultHttpUrl.zhengZeId:zhengZeId;
    }

    public void setZhengZeId(String zhengZeId) {
        this.zhengZeId = zhengZeId;
    }

    public String getZhengZeType() {
        return TextUtils.isEmpty(zhengZeType)? DefaultHttpUrl.zhengZeType:zhengZeType;
    }

    public void setZhengZeType(String zhengZeType) {
        this.zhengZeType = zhengZeType;
    }

    public String getZhengZeTag() {
        return TextUtils.isEmpty(zhengZeTag)? DefaultHttpUrl.zhengZeTag:zhengZeTag;
    }

    public void setZhengZeTag(String zhengZeTag) {
        this.zhengZeTag = zhengZeTag;
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

    public String getRegularTable() {
        return TextUtils.isEmpty(regularTable)? DefaultHttpUrl.regularTable:regularTable;
    }

    public void setRegularTable(String regularTable) {
        this.regularTable = regularTable;
    }

    public String getRegularZaixianUrl() {
        return TextUtils.isEmpty(regularZaixianUrl)? DefaultHttpUrl.regularZaixianUrl:regularZaixianUrl;
    }

    public void setRegularZaixianUrl(String regularZaixianUrl) {
        this.regularZaixianUrl = regularZaixianUrl;
    }

    public String getRegularBaidupanUrl() {
          return TextUtils.isEmpty(regularBaidupanUrl)? DefaultHttpUrl.regularBaidupanUrl:regularBaidupanUrl;
    }

    public void setRegularBaidupanUrl(String regularBaidupanUrl) {
        this.regularBaidupanUrl = regularBaidupanUrl;
    }

    public String getRegularBaidupanName() {
        return TextUtils.isEmpty(regularBaidupanName)? DefaultHttpUrl.regularBaidupanName:regularBaidupanName;
    }

    public void setRegularBaidupanName(String regularBaidupanName) {
        this.regularBaidupanName = regularBaidupanName;
    }

    public String getRegularBaidupanUrlMima() {
        return TextUtils.isEmpty(regularBaidupanUrlMima)? DefaultHttpUrl.regularBaidupanUrlMima:regularBaidupanUrlMima;
    }

    public void setRegularBaidupanUrlMima(String regularBaidupanUrlMima) {
        this.regularBaidupanUrlMima = regularBaidupanUrlMima;
    }

    public String getRegularChiliName() {
        return TextUtils.isEmpty(regularChiliName)? DefaultHttpUrl.regularChiliName:regularChiliName;
    }

    public void setRegularChiliName(String regularChiliName) {
        this.regularChiliName = regularChiliName;
    }

    public String getYearRegular() {
        return TextUtils.isEmpty(yearRegular)? DefaultHttpUrl.yearRegular:yearRegular;
    }

    public void setYearRegular(String yearRegular) {
        this.yearRegular = yearRegular;
    }

    public String getRatingRegular() {
        return TextUtils.isEmpty(ratingRegular)? DefaultHttpUrl.ratingRegular:ratingRegular;
    }

    public void setRatingRegular(String ratingRegular) {
        this.ratingRegular = ratingRegular;
    }

    public String getCountryRegular() {
        return TextUtils.isEmpty(countryRegular)? DefaultHttpUrl.countryRegular:countryRegular;
    }

    public void setCountryRegular(String countryRegular) {
        this.countryRegular = countryRegular;
    }

    public String getTagsRegular() {
        return TextUtils.isEmpty(tagsRegular)? DefaultHttpUrl.tagsRegular:tagsRegular;
    }

    public void setTagsRegular(String tagsRegular) {
        this.tagsRegular = tagsRegular;
    }

    public String getZhengZeBanerItem() {
        return TextUtils.isEmpty(zhengZeBanerItem)? DefaultHttpUrl.zhengZeBanerItem:zhengZeBanerItem;
    }

    public void setZhengZeBanerItem(String zhengZeBanerItem) {
        this.zhengZeBanerItem = zhengZeBanerItem;
    }

    public String getZhengZeHotNewItem() {
        return TextUtils.isEmpty(zhengZeHotNewItem)? DefaultHttpUrl.zhengZeHotNewItem:zhengZeHotNewItem;
    }

    public void setZhengZeHotNewItem(String zhengZeHotNewItem) {
        this.zhengZeHotNewItem = zhengZeHotNewItem;
    }

    public String getZhengZeHotHtml() {
        return TextUtils.isEmpty(zhengZeHotHtml)? DefaultHttpUrl.zhengZeHotHtml:zhengZeHotHtml;
    }

    public void setZhengZeHotHtml(String zhengZeHotHtml) {
        this.zhengZeHotHtml = zhengZeHotHtml;
    }

    public String getZhengZeNewHtml() {
        return TextUtils.isEmpty(zhengZeNewHtml)? DefaultHttpUrl.zhengZeNewHtml:zhengZeNewHtml;
    }

    public void setZhengZeNewHtml(String zhengZeNewHtml) {
        this.zhengZeNewHtml = zhengZeNewHtml;
    }

    public String getZhengZeSearch() {
        return TextUtils.isEmpty(zhengZeSearch)? DefaultHttpUrl.zhengZeSearch:zhengZeSearch;
    }

    public void setZhengZeSearch(String zhengZeSearch) {
        this.zhengZeSearch = zhengZeSearch;
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