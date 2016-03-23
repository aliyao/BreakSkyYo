package com.yao.breakskyyo.entity;

import java.util.List;

/**
 * 项目名称：BreakSkyYo
 * 类描述：
 * 创建人：yoyo
 * 创建时间：2016/3/23 20:48
 * 修改人：yoyo
 * 修改时间：2016/3/23 20:48
 * 修改备注：
 */
public class SearchInfo {
    List<SearchInfoItem>  searchInfo;

    public List<SearchInfoItem> getSearchInfo() {
        return searchInfo;
    }

    public void setSearchInfo(List<SearchInfoItem> searchInfo) {
        this.searchInfo = searchInfo;
    }
}
