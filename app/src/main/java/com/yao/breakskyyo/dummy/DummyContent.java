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
        ITEM_MAP.clear();
        for (int itemNum = 0; itemNum < listData.size(); itemNum++) {
            addItem(new DummyItem((String) listData.get(itemNum).get("id"), (String) listData.get(itemNum).get("title"),(String)  listData.get(itemNum).get("url"), (String) listData.get(itemNum).get("imgurl"),
                    (String) listData.get(itemNum).get("tag"), (String)listData.get(itemNum).get("type"), (String) listData.get(itemNum).get("score")));

        }
    }

    public static void addData(List<Map<String, Object>> listData) {

        /*addItem(new DummyItem("1", listData));
        addItem(new DummyItem("2", "Item 2"));
        addItem(new DummyItem("3", "Item 3"));*/
        for (int itemNum = 0; itemNum < listData.size(); itemNum++) {
            addItem(new DummyItem((String) listData.get(itemNum).get("id"), (String)listData.get(itemNum).get("title"), (String)listData.get(itemNum).get("url"),(String) listData.get(itemNum).get("imgurl"),
                    (String)listData.get(itemNum).get("tag"),(String)listData.get(itemNum).get("type"),(String) listData.get(itemNum).get("score")));

        }
    }
    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
        KJLoger.debug("DummyItem:" + item.toString());

    }

}
