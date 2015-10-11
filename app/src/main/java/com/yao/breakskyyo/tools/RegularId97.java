package com.yao.breakskyyo.tools;

import android.text.TextUtils;
import android.util.Log;

import com.yao.breakskyyo.dummy.DummyContent;
import com.yao.breakskyyo.dummy.InfoVideos;
import com.yao.breakskyyo.dummy.SelectHeadItem;

import org.kymjs.kjframe.utils.KJLoger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 项目名称：BreakSkyYo
 * 类描述：
 * 创建人：yoyo
 * 创建时间：2015/9/16 23:21
 * 修改人：yoyo
 * 修改时间：2015/9/16 23:21
 * 修改备注：
 */
public class RegularId97 {
    static final String id97Url = "http://www.id97.com/videos/play";
    static final String urlBaiduPan = "http://pan.baidu.com";
    static String regularTable = "【导演】([\\s\\S]*?)<div class=\"am-g\">";
    static String regularZaixianUrl = "videos/play([\\s\\S]*?)\"";
    static String regularBaidupanUrl = urlBaiduPan + "(.*?)\">.*?</a>";
    static String regularBaidupanName = urlBaiduPan + ".*?\">(.*?)</a>";
    static String regularBaidupanUrlMima = "密码：(.*?)</";
    //static String regularChili = "<a class=\"am-inline am-text-break\" href=\"(.*?)\">";
    static String regularChiliName = "<a class=\"am-inline am-text-break\" href=\"(.*?)\">(.*?)</a>";

    static String[] regular = {regularTable, regularZaixianUrl, regularBaidupanUrl, regularBaidupanName, regularBaidupanUrlMima, regularChiliName};

    static String year = "/videos/movie\\?year=([\\s\\S]*?)\"[\\s\\S]*?>([\\s\\S]*?)<";
    static String rating = "/videos/movie\\?rating=([\\s\\S]*?)\"[\\s\\S]*?>([\\s\\S]*?)<";
    static String country = "/videos/movie\\?country=([\\s\\S]*?)\"[\\s\\S]*?>([\\s\\S]*?)<";
    static String tags = "/videos/movie\\?tags=([\\s\\S]*?)\" class=\"movie-tags[\\s\\S]*?>([\\s\\S]*?)<";
    static String[] regularSelectHead = {year, rating, country, tags};


    public static InfoVideos getInfoVideos(String htmlStr) {
        InfoVideos mInfoVideos = new InfoVideos();
        List<String> itemList;
        for (int item = 0; item < regular.length; item++) {
            itemList = getObjByRegular(htmlStr, regular[item]);
            // KJLoger.debug("yoyo:--"+ regular[item]+"--" + itemStr);
            if (itemList != null) {
                KJLoger.debug("yoyo:--" + regular[item] + "--" + itemList.get(0));
                switch (item) {
                    case 0:
                        mInfoVideos.setMovie_jvqing("【导演】" + itemList.get(0).replace("<br/>", "\n").replace("<br />", "\n"));
                        break;
                    case 1:
                        mInfoVideos.setMovie_payZaixian(id97Url + itemList.get(0));
                        break;
                    case 2:
                        mInfoVideos.setBaiduPanUrl(urlBaiduPan + itemList.get(0));
                        break;
                    case 3:
                        mInfoVideos.setBaiduPanName(itemList.get(0));
                        break;
                    case 4:
                        mInfoVideos.setBaiduPanUrlMima(itemList.get(0));
                        break;
                    case 5:
                        List<Map<String, String>> regularChili = new ArrayList<>();
                        for (int i = 1; i < itemList.size(); i = i + 2) {
                            Map<String, String> map = new HashMap<>();
                            map.put("url", itemList.get(i));
                            map.put("name", itemList.get(i - 1));
                            regularChili.add(map);
                        }
                        mInfoVideos.setRegularChili(regularChili);
                        break;
                }
            }

        }
        return mInfoVideos;
    }

    public static List< List<SelectHeadItem>> getSelectHeadItem(String htmlStr) {
        List<SelectHeadItem> listSelectHeadItemYear = new ArrayList<>();
        List<SelectHeadItem> listSelectHeadItemRating = new  ArrayList<>();
        List<SelectHeadItem> listSelectHeadItemCountry = new  ArrayList<>();
        List<SelectHeadItem> listSelectHeadItemTags = new  ArrayList<>();
        List<String> itemList;
       // System.out.println(htmlStr);
        for (int item = 0; item < regularSelectHead.length; item++) {
            itemList = getObjByRegular(htmlStr, regularSelectHead[item]);
            // KJLoger.debug("yoyo:--"+ regularSelectHead[item]+"--" + itemStr);
            if (itemList != null) {
                for (int num = 1; num < itemList.size();num=num+2){
                    //KJLoger.debug("yoyo:--" + regularSelectHead[item] + "--" + UrlSelectHead + itemList.get(0) + "---" + UrlSelectHead + itemList.get(1));
                    switch (item){
                        case 0:
                            SelectHeadItem mSelectHeadItemYear=new SelectHeadItem();
                            mSelectHeadItemYear.setUrl( itemList.get(num - 1));
                            mSelectHeadItemYear.setText(itemList.get(num));
                            listSelectHeadItemYear.add(mSelectHeadItemYear);
                            break;
                        case 1:
                            SelectHeadItem mSelectHeadItemRating=new SelectHeadItem();
                            mSelectHeadItemRating.setUrl(itemList.get(num - 1));
                            mSelectHeadItemRating.setText(itemList.get(num));
                            listSelectHeadItemRating.add(mSelectHeadItemRating);
                            break;
                        case 2:
                            SelectHeadItem mSelectHeadItemCountry=new SelectHeadItem();
                            mSelectHeadItemCountry.setUrl(itemList.get(num-1));
                            mSelectHeadItemCountry.setText(itemList.get(num));
                            listSelectHeadItemCountry.add(mSelectHeadItemCountry);
                            break;
                        case 3:
                            SelectHeadItem mSelectHeadItemTags=new SelectHeadItem();
                            mSelectHeadItemTags.setUrl(itemList.get(num-1));
                            mSelectHeadItemTags.setText(itemList.get(num));
                            listSelectHeadItemTags.add(mSelectHeadItemTags);
                            break;
                    }
                }
            }
        }
        listSelectHeadItemYear.add(0,new SelectHeadItem("全部"));
        listSelectHeadItemRating.add(0,new SelectHeadItem("全部"));
        listSelectHeadItemCountry.add(0,new SelectHeadItem("全部"));
        listSelectHeadItemTags.add(0,new SelectHeadItem("全部"));
        List< List<SelectHeadItem>> listListSelectHeadItem=new ArrayList<>();
        listListSelectHeadItem.add(listSelectHeadItemYear);
        listListSelectHeadItem.add(listSelectHeadItemRating);
        listListSelectHeadItem.add(listSelectHeadItemCountry);
        listListSelectHeadItem.add(listSelectHeadItemTags);

        return listListSelectHeadItem;
    }

    private static List<String> getObjByRegular(String htmlStr, String regularStr) {
        List<String> listStr = new ArrayList<>();
        Pattern p = Pattern.compile(regularStr);
        Matcher m = p.matcher(htmlStr);
        while (m.find()) { // 循环查找匹配字串
            MatchResult mr = m.toMatchResult();
            for (int groupItem = 1; groupItem <= mr.groupCount(); groupItem++) {
                if (mr != null && mr.group(groupItem) != null) {
                     System.out.println(mr.group(groupItem));
                    listStr.add(mr.group(groupItem));
                }

            }

        }
        if (listStr != null && listStr.size() == 0) {
            listStr = null;
        }

        return listStr;
    }
}

