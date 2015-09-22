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
    static String regularTable = "class=\"info-label\">(.*?)</[\\s\\S]*?<a.*?>(.*?)<";
    static String regularShowImg = "<a class=\"list-group-item moviecutpic\" href=\"[\\s\\S]*?\"><img src=\"([\\s\\S]*?)\">";
    static String regularTip = "<div class=\"panel-footer resource-help\">[\\s\\S]*?1.([\\s\\S]*?)请参考<[\\s\\S]*?2.([\\s\\S]*?)<[\\s\\S]*?3.([\\s\\S]*?)</div>";
    static String regularChili = "磁力[\\s\\S]*?<a title=\".*?\" rel=\"nofollow\" target=\"_blank\" href=\"(.*?)\">(.*?)</a>";

   // static String regularBaidupanUrlMima = "密码：</a><strong style=\"color:red;\">(.*?)</strong>";
    //static String regularBaidupanUrl = urlBaiduPan + "(.*?)\">";
   // static String regularZaixian = "href=\"(.*?)\"><img src=\"/static/images/logo_id97_small.png\"> 在线播放";
  //  static String regularJvqing = "class=\"summary\">([\\s\\S]*?)</";
   // static String regular2 = "[\\s\\S]*?浏览次数：(.*?)<br>[\\s\\S]*?新时间：(.*?)</";
   // static String regularPay = "class=\"btn btn-success btn-block\" href=\"(.*?)\">(.*?)</a>";



    static String[] regular={"class=\"am-article-title\">([\\s\\S]*?)<","class=\"movie-year\">(.*?)<","<img class=\"img-thumbnail\"[\\s\\S]*?src=\"(.*?)\">",
            "class=\"btn btn-success btn-block\" href=\"(.*?)\">","class=\"btn btn-success btn-block\" href=\".*?\">(.*?)</a>","浏览次数：(.*?)<",
            "更新时间：(.*?)<","class=\"summary\">([\\s\\S]*?)<","href=\"(.*?)\"><img src=\"/static/images/logo_id97_small.png\"> 在线播放",
             "http://pan.baidu.com(.*?)\">", "密码：</a><strong style=\"color:red;\">(.*?)</strong>"};


    public static InfoVideos getInfoVideos(String htmlStr) {
        InfoVideos mInfoVideos = new InfoVideos();
        String itemStr;
        for (int item=0;item<regular.length;item++) {
           itemStr=getObjByRegular(htmlStr, regular[item]);
            KJLoger.debug("yoyo:--"+ regular[item]+"--" + itemStr);
            switch (item){
                case 0:
                    mInfoVideos.setMovie_title(itemStr);
                    break;
                case 1:
                    mInfoVideos.setMovie_year(itemStr);
                    break;
                case 2:
                    mInfoVideos.setImg_thumbnail(itemStr);
                    break;
                case 3:
                    mInfoVideos.setMovie_payUrl(id97Url + itemStr);
                    break;
                case 4:
                    mInfoVideos.setMovie_payText(itemStr);
                    break;
                case 5:
                    mInfoVideos.setBrowse_num(itemStr);
                    break;
                case 6:
                    mInfoVideos.setUpadte_date(itemStr);
                    break;
                case 7:
                    mInfoVideos.setMovie_jvqing(itemStr);
                    break;
                case 8:
                    mInfoVideos.setMovie_payZaixian(itemStr);
                    break;
                case 9:
                    mInfoVideos.setBaiduPanUrl(urlBaiduPan+itemStr);
                    break;
                case 10:
                    mInfoVideos.setBaiduPanUrlMima(itemStr);
                    break;
               /* case 9:
                    mInfoVideos.setBaiduPanUrl(itemStr);
                    break;
                case 9:
                    mInfoVideos.setBaiduPanUrl(itemStr);
                    break;*/

            }
        }
        return mInfoVideos;
    }

    private static String getObjByRegular(String htmlStr, String regularStr) {
       /* Pattern p = Pattern.compile(regularStr);
        Matcher m = p.matcher(htmlStr);
        if(m!=null&&m.groupCount()>0){
            m.find();
            MatchResult mr = m.toMatchResult();
            if (mr != null && mr.group(1) != null) {
                return mr.group(1);
            }
        }*/
        Pattern p = Pattern.compile(regularStr);
        Matcher m = p.matcher(htmlStr);
        while (m.find()) { // 循环查找匹配字串
            MatchResult mr = m.toMatchResult();
            for (int groupItem = 1; groupItem <= mr.groupCount(); groupItem++) {
                if (mr != null && mr.group(groupItem) != null) {
                   // System.out.println(mr.group(groupItem));
                    return mr.group(groupItem);
                }

            }

        }

        return null;
    }
}

