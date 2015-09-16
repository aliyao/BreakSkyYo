package com.yao.breakskyyo.tools;

import com.yao.breakskyyo.dummy.InfoVideos;

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
    static String regularTip = "<div class=\"panel-footer resource-help\">[\\s\\S]*?1.([\\s\\S]*?)请参考<[\\s\\S]*?2.([\\s\\S]*?)<[\\s\\S]*?3.([\\s\\S]*?)</div>";
    static String regularChili = "磁力[\\s\\S]*?<a title=\".*?\" rel=\"nofollow\" target=\"_blank\" href=\"(.*?)\">(.*?)</a>";
    static String regularBaidupanUrlMima = "密码：</a><strong style=\"color:red;\">(.*?)</strong>";
    static String regularBaidupanUrl = urlBaiduPan + "(.*?)\">";
    static String regularZaixian = "href=\"(.*?)\"><img src=\"/static/images/logo_id97_small.png\"> 在线播放";
    static String regularShowImg = "<a class=\"list-group-item moviecutpic\" href=\"[\\s\\S]*?\"><img src=\"([\\s\\S]*?)\">";
    static String regularJvqing = "class=\"summary\">([\\s\\S]*?)</";
    static String regularTable = "class=\"info-label\">(.*?)</[\\s\\S]*?<a.*?>(.*?)<";
    static String regular2 = "[\\s\\S]*?浏览次数：(.*?)<br>[\\s\\S]*?新时间：(.*?)</";
    static String regular1 = "class=\"movie-title\">(.*?)<span class=\"movie-year\">(.*?)</[\\s\\S]*?<img class=\"img-thumbnail\"[\\s\\S]*?src=\"(.*?)\">[\\s\\S]*?class=\"btn btn-success btn-block\" href=\"(.*?)>(.*?)</a>";

    /*public static InfoVideos getInfoVideos() {

    }*/
}
