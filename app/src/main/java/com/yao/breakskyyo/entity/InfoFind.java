package com.yao.breakskyyo.entity;

import java.util.List;

/**
 * 项目名称：BreakSkyYo
 * 类描述：
 * 创建人：yoyo
 * 创建时间：2016/3/23 16:26
 * 修改人：yoyo
 * 修改时间：2016/3/23 16:26
 * 修改备注：
 */
public class InfoFind {
    List<FindItem> findList;
    TypeInfoItem countryInfo;
    TypeInfoItem filmTypeInfo;
    TypeInfoItem scoreInfo;
    TypeInfoItem yearInfo;

    public List<FindItem> getFindList() {
        return findList;
    }

    public void setFindList(List<FindItem> findList) {
        this.findList = findList;
    }

    public TypeInfoItem getCountryInfo() {
        return countryInfo;
    }

    public void setCountryInfo(TypeInfoItem countryInfo) {
        this.countryInfo = countryInfo;
    }

    public TypeInfoItem getFilmTypeInfo() {
        return filmTypeInfo;
    }

    public void setFilmTypeInfo(TypeInfoItem filmTypeInfo) {
        this.filmTypeInfo = filmTypeInfo;
    }

    public TypeInfoItem getScoreInfo() {
        return scoreInfo;
    }

    public void setScoreInfo(TypeInfoItem scoreInfo) {
        this.scoreInfo = scoreInfo;
    }

    public TypeInfoItem getYearInfo() {
        return yearInfo;
    }

    public void setYearInfo(TypeInfoItem yearInfo) {
        this.yearInfo = yearInfo;
    }
}
