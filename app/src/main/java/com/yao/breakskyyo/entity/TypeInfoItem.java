package com.yao.breakskyyo.entity;

import com.yao.breakskyyo.dummy.SelectHeadItem;

import java.util.List;

/**
 * 项目名称：BreakSkyYo
 * 类描述：
 * 创建人：yoyo
 * 创建时间：2016/3/23 16:36
 * 修改人：yoyo
 * 修改时间：2016/3/23 16:36
 * 修改备注：
 */
public class TypeInfoItem {
    List<SelectHeadItem> typeList;
    String typeName;

    public List<SelectHeadItem> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<SelectHeadItem> typeList) {
        this.typeList = typeList;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
