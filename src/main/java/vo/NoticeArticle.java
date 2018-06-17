package vo;

import util.Utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by maqiyue on 2018/5/20
 */
public class NoticeArticle implements Comparable<NoticeArticle>{
    private int id;
    private String url;
    private String title;
    private String article;
    private Map<String,Integer> map;
    private String time;

    private int count;

    public void addMap(String key) {
        if(map == null){
            map = new HashMap<>();
        }
        if(map.containsKey(key)){
            map.put(key,map.get(key)+1);
        }else {
            map.put(key,1);
        }
        this.map = map;
    }

    public Map<String, Integer> getMap() {
        return map;
    }

    public void setMap(Map<String, Integer> map) {
        this.map = map;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void addCount() {
        this.count ++;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }



    public NoticeArticle(String url, String title, String article) {
        this.url = url;
        this.title = title;
        this.article = article;
    }

    public NoticeArticle(String url, String title, String article, String time) {
        this.url = url;
        this.title = title;
        this.article = article;
        this.time = time;
    }

    public NoticeArticle(int id, String url, String title, String article,String time) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.article = article;
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int compareTo(NoticeArticle o) {
        if(intTime(o.time)>intTime(this.time)){
            return 1;
//            if(o.map.size()>this.map.size()){
//                return 1;
//            }else {
//                return 0;
//            }
        }else {
            return -1;
        }
    }

    public int intTime(String time){
        String str = time.replaceAll("发布时间: ","")
                .replaceAll("-","")
                .replaceAll("\u001b\\[33m","")
                .replaceAll("\u001b\\[32m","")
                .replaceAll("\u001b\\[0m","");
        return Integer.parseInt(str);
    }
}
