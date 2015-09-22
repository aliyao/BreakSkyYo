package com.yao.breakskyyo.tools;

import android.text.TextUtils;

import com.yao.breakskyyo.dummy.DummyContent;
import com.yao.breakskyyo.dummy.InfoVideos;

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
    static final String id97Url = "http://www.id97.com";
    static final String urlBaiduPan = "http://pan.baidu.com";
    static String regularTable = "</div>\n" +
            "\n([\\s\\S]*?)\n" +
            "\n" +
            "<div class=\"am-g\">";
    static String regularZaixianUrl ="<li class=\"list-group-item\"><a target=\"_blank\" href=\""+id97Url+"(.*?)\\\">";
    static String regularBaidupanUrl = urlBaiduPan + "(.*?)\">.*?</a>";
    static String regularBaidupanName = urlBaiduPan + ".*?\">(.*?)</a>";
    static String regularBaidupanUrlMima = "密码：(.*?)</";
    static String regularChili = "<li><a class=\"am-inline am-text-break\" href=\"(.*?)\">";
    static String regularChiliName = "<li><a class=\"am-inline am-text-break\" href=\".*?\">(.*?)</a>";

    static String[] regular={regularTable,regularZaixianUrl,regularBaidupanUrl,regularBaidupanName,regularBaidupanUrlMima,regularChiliName };


    public static InfoVideos getInfoVideos(String htmlStr) {
        InfoVideos mInfoVideos = new InfoVideos();
        List<String> itemList;
        for (int item=0;item<regular.length;item++) {
            itemList=getObjByRegular(htmlStr, regular[item]);
           // KJLoger.debug("yoyo:--"+ regular[item]+"--" + itemStr);
            if(itemList!=null){
                switch (item){
                    case 0:
                        mInfoVideos.setMovie_jvqing(itemList.get(0));
                        break;
                    case 1:
                        mInfoVideos.setMovie_payZaixian(id97Url + itemList.get(0));
                        break;
                    case 2:
                        mInfoVideos.setBaiduPanUrl(id97Url + itemList.get(0));
                        break;
                    case 3:
                        mInfoVideos.setBaiduPanName(itemList.get(0));
                        break;
                    case 4:
                        mInfoVideos.setBaiduPanUrlMima(itemList.get(0));
                        break;
                    case 5:
                        List<Map<String, String>> regularChili=new ArrayList<>();
                        for(int i=1;i<itemList.size();i=i+2){
                            Map<String, String> map=new HashMap<>();
                            map.put("url",itemList.get(i-1));
                            map.put("name",itemList.get(i));
                            regularChili.add(map);
                        }
                        mInfoVideos.setRegularChili(regularChili);
                        break;
                }
            }

        }
        return mInfoVideos;
    }

    private static List<String> getObjByRegular(String htmlStr, String regularStr) {
        List<String> listStr=new ArrayList<>();
        Pattern p = Pattern.compile(regularStr);
        Matcher m = p.matcher(htmlStr);
        while (m.find()) { // 循环查找匹配字串
            MatchResult mr = m.toMatchResult();
            for (int groupItem = 1; groupItem <= mr.groupCount(); groupItem++) {
                if (mr != null && mr.group(groupItem) != null) {
                   // System.out.println(mr.group(groupItem));
                    listStr.add(mr.group(groupItem));
                }

            }

        }
        if(listStr!=null&&listStr.size()==0){
            listStr=null;
        }

        return listStr;
    }
}

