package com.yao.breakskyyo.dummy;

/**
 * 项目名称：BreakSkyYo
 * 类描述：
 * 创建人：yoyo
 * 创建时间：2015/10/16 21:25
 * 修改人：yoyo
 * 修改时间：2015/10/16 21:25
 * 修改备注：
 */
public class SearchItem {
    String id;
    String imgUrl;
    String hdtag;
    String content;

    public SearchItem( String content,String id,String imgUrl,String hdtag){
        this.id=id;
        this.imgUrl=imgUrl;
        this.hdtag=hdtag;
        this.content=content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getHdtag() {
        return hdtag;
    }

    public void setHdtag(String hdtag) {
        this.hdtag = hdtag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
