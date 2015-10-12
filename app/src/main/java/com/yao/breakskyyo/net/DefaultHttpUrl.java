package com.yao.breakskyyo.net;

/**
 * Created by nideyoyo on 2015/10/12.
 */
public class DefaultHttpUrl {
    public final static String MainList="http://www.id97.com";
    public final static String FindList="http://www.id97.com/videos/movie?";
    public final static String UpdateApp="";


    public final static String  UpdateAppWeb="http://pan.baidu.com/s/1jGzYX9O";
    public final static String HellpUrl="http://www.id97.com/topic/91";
    public final static String SearchInfo = "http://www.id97.com/videos/search/name/";
    public final static String year = "&year=";
    public final static String rating = "&rating=";
    public final static String country = "&country=";
    public final static String tags = "&tags=";
    public final static String page = "page=";

    public final static  String zhengZeItem = "title=\"(.*?)\" target=\"_blank\" href=\"(.*?)\">[\\s\\S]*?<img alt=\".*?\" title=\".*?\" src=\"(.*?)\".*?>([\\s\\S]*?)</a>[\\s\\S]*?<span class=\"otherinfo\"> - (.*?)分</span></div>[\\s\\S]*?<div class=\"otherinfo\">类型：(.*?)</div>";
    public final static  String zhengZeId = "id/(.*?).html";
    public final static  String zhengZeType = "<a.*?class=\"movietype\">(.*?)</a>";
    public final static  String zhengZeTag = "[\\s\\S]*?>(.*?)</";
    public static final String id97Url = "http://www.id97.com/videos/play";
    public static final String urlBaiduPan = "http://pan.baidu.com";
    public static final String regularTable = "【导演】([\\s\\S]*?)<div class=\"am-g\">";
    public static final String regularZaixianUrl = "videos/play([\\s\\S]*?)\"";
    public static final String regularBaidupanUrl = urlBaiduPan + "(.*?)\">.*?</a>";
    public static final String regularBaidupanName = urlBaiduPan + ".*?\">(.*?)</a>";
    public static final String regularBaidupanUrlMima = "- 密码：(.*?)</";
    public static final String regularChiliName = "<a class=\"am-inline am-text-break\" href=\"(.*?)\">(.*?)</a>";
    //public static final String[] regular = {regularTable, regularZaixianUrl, regularBaidupanUrl, regularBaidupanName, regularBaidupanUrlMima, regularChiliName};
    public static final String yearRegular = "/videos/movie\\?year=([\\s\\S]*?)\"[\\s\\S]*?>([\\s\\S]*?)<";
    public static final String ratingRegular = "/videos/movie\\?rating=([\\s\\S]*?)\"[\\s\\S]*?>([\\s\\S]*?)<";
    public static final String countryRegular = "/videos/movie\\?country=([\\s\\S]*?)\"[\\s\\S]*?>([\\s\\S]*?)<";
    public static final String tagsRegular = "/videos/movie\\?tags=([\\s\\S]*?)\" class=\"movie-tags[\\s\\S]*?>([\\s\\S]*?)<";
   // public static final String[] regularSelectHead = {yearRegular, ratingRegular, countryRegular, tagsRegular};
    public static final String zhengZeBanerItem = "<a target=\"_blank\" href=\"/videos/resource/id/(.*?).html[\\s\\S]*?\">[\\s\\S]*?<img src=\"([\\s\\S]*?)\" alt=\"(.*?)\"";
    public static final String zhengZeHotNewItem = "/videos/resource/id/(.*?).html\">[\\s\\S]*?<div class=\"index-img\">[\\s\\S]*?<img src=\"(.*?)\" alt=\".*?\" />[\\s\\S]*?</div>[\\s\\S]*?<h3 class=\"am-gallery-title\">(.*?)</h3>[\\s\\S]*?<div class=\"am-gallery-desc\">(.*?)<";
    public static final String zhengZeHotHtml = "<h3>热门电影：</h3>([\\s\\S]*?)<h3>最新电影：</h3>";
    public static final String zhengZeNewHtml = "<h3>最新电影：</h3>([\\s\\S]*?)</body>";
    public static final String  zhengZeSearch="result-item[\\s\\S]*?\"/videos/resource/id/(.*?).html\"[\\s\\S]*?<img class=\"img-thumbnail\"[\\s\\S]*?alt=\"([\\s\\S]*?)\"[\\s\\S]*?src=([\\s\\S]*?)>[\\s\\S]*?<button class=\"hdtag\">(.*?)</button>[\\s\\S]*?<div class=\"col-md-7\">([\\s\\S]*?)<p>资源下载地址";
    public static final String SearchBaiduUrl="http://www.baidu.com/s?wd=";
    public static final String videosResourceIdUrl="http://www.id97.com/videos/resource/id/%s.html";
    public static final String UCUrl="http://www.uc.cn/";
}
