package parse;

import vo.NoticeArticle;
import vo.NoticeHtml;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by maqiyue on 2018/5/20
 */
public class NoticeArticleFilter {

    public NoticeArticle getArticle(String url,String html){
        html = html.replaceAll("\n","");
        html = html.replaceAll("\r","");
        return new NoticeArticle(url,getTitle(html),getArticle(html),getTime(html));
    }

    public  String getTitle(String html){
        Pattern p = Pattern.compile("<h2>.+?</h2>");
        Matcher m = p.matcher(html);
        String result = null;
        if (m.find()){
            result = m.group();
        }
        result = result.substring(4,result.length()-5);
        return result;
    }

    public  String getArticle(String html){
        Pattern p = Pattern.compile("<div class=\"body\">.+?<div id=\"thirdbutton\">");
        Matcher m = p.matcher(html);
        String result = null;
        if (m.find()){
            result = m.group();
        }
        result = result.replaceAll("<.+?>","");
        result = result.replaceAll("&nbsp;"," ").trim();
        System.out.println(result);
        return result;
    }

    public  String getTime(String html){
        Pattern p = Pattern.compile("发布日期：.+?</li>");
        Matcher m = p.matcher(html);
        String result = null;
        if (m.find()){
            result = m.group();
        }
        result = result.replaceAll("发布日期：","");
        result = result.replaceAll("</li>"," ").trim();
        System.out.println(result);
        return result;
    }


}
