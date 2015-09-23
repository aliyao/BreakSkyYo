package com.yao.breakskyyo.dummy;

import java.util.Date;
import java.util.List;

/**
 * Created by nideyoyo on 2015/9/7.
 */
public class DummyItem {
    public String id;
    public Object content;
    public Object url;
    public Object imgUrl;
    public Object tag;
    public List<String> type;
    public Object score;
    public Date keepDate;

    public DummyItem(String id, Object content, Object url, Object imgUrl, Object tag, Object type, Object score) {
        this.id = id;
        this.content = content;
        this.url = url;
        this.imgUrl = imgUrl;
        this.tag = tag;
        this.type = (List<String>) type;
        this.score = score;
    }
    public DummyItem(String id, Object content, Object url, Object imgUrl, Object tag, Object type, Object score,Date keepDate) {
        this.id = id;
        this.content = content;
        this.url = url;
        this.imgUrl = imgUrl;
        this.tag = tag;
        this.type = (List<String>) type;
        this.score = score;
        this.keepDate=keepDate;
    }
    public DummyItem(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public Object getUrl() {
        return url;
    }

    public void setUrl(Object url) {
        this.url = url;
    }

    public Object getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(Object imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public Object getScore() {
        return score;
    }

    public void setScore(Object score) {
        this.score = score;
    }


    @Override
    public String toString() {
        String typeStr = "";
        for (String tp : type
                ) {
            typeStr = typeStr + "--" + tp;
        }
        return id + "--" + content + "--" + url + "--" + imgUrl + "--" + tag + "--" + score + "--" + typeStr;
    }
}
