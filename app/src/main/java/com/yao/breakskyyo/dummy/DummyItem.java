package com.yao.breakskyyo.dummy;

/**
 * Created by nideyoyo on 2015/9/7.
 */
public class DummyItem {
    public String id;
    public String content;
    public String url;
    public String imgUrl;
    public String tag;
    public String type;
    public String score;
    public long saveDate;

    public DummyItem(String id, String content, String url, String imgUrl, String tag, String type, String score) {
        this.id = id;
        this.content = content;
        this.url = url;
        this.imgUrl = imgUrl;
        this.tag = tag;
        this.type =type;
        this.score = score;
    }
    /*public DummyItem(String id, String content, String url, String imgUrl, String tag, List<String> type, String score,Date saveDate) {
        this.id = id;
        this.content = content;
        this.url = url;
        this.imgUrl = imgUrl;
        this.tag = tag;
        this.type = (List<String>) type;
        this.score = score;
        this.saveDate=saveDate;
    }*/
    public DummyItem(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public long getSaveDate() {
        return saveDate;
    }

    public void setSaveDate(long saveDate) {
        this.saveDate = saveDate;
    }

    @Override
    public String toString() {
       /* String typeStr = "";
        for (String tp : type
                ) {
            typeStr = typeStr + "--" + tp;
        }*/
        return id + "--" + content + "--" + url + "--" + imgUrl + "--" + tag + "--" + score + "--" ;
    }
}
