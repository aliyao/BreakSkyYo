package com.yao.breakskyyo.dummy;

import org.kymjs.kjframe.utils.KJLoger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    public static void setData(List<Map<String, Object>> listData) {

        /*addItem(new DummyItem("1", listData));
        addItem(new DummyItem("2", "Item 2"));
        addItem(new DummyItem("3", "Item 3"));*/
        for (int itemNum = 0; itemNum < listData.size(); itemNum++) {
            addItem(new DummyItem((String) listData.get(itemNum).get("id"), listData.get(itemNum).get("title"), listData.get(itemNum).get("url"), listData.get(itemNum).get("imgurl"),
                    listData.get(itemNum).get("tag"), listData.get(itemNum).get("type"), listData.get(itemNum).get("score")));

        }
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);

        KJLoger.debug("DummyItem:" + item.toString());

    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public String id;
        public Object content;
        public Object url;
        public Object imgUrl;
        public Object tag;
        public List<String> type;
        public Object score;


        public DummyItem(String id, Object content, Object url, Object imgUrl, Object tag, Object type, Object score) {
            this.id = id;
            this.content = content;
            this.url = url;
            this.imgUrl = imgUrl;
            this.tag = tag;
            this.type = (List<String>) type;
            this.score = score;
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
}
