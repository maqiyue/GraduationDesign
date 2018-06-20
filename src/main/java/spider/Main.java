package spider;

import Hanlp.NLPTokenizer;
import dao.*;
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Test;
import parse.NoticeArticleFilter;
import parse.NoticeUrlFilter;
import solr.SolrJTest;
import util.Utils;
import vo.NoticeArticle;
import vo.NoticeHtml;
import vo.Participle;
import vo.RangeFrequency;

import java.io.IOException;
import java.util.*;

/**
 * Created by maqiyue on 2018/5/19
 */
public class Main {

    @Test
    public void getNoticeUrl() {
        String hostUrl = "http://jwzx.hrbust.edu.cn/homepage/infoArticleList.do;jsessionid=D5C1F73CA61367389769528B8FD7BEA3.TH?sortColumn=publicationDate&columnId=354&sortDirection=-1&pagingPage=2&pagingNumberPer=1684";
        String host = "http://jwzx.hrbust.edu.cn/homepage/";
        HtmlGet htmlGet = new HtmlGet();
        String str = htmlGet.getHtml(hostUrl);
        List<String> list = NoticeUrlFilter.getNoticeUrlList(host, str);
        UrlDao urlDao = new UrlDao();
        urlDao.add(list);
    }

    @Test
    public void getNoticeHtml() {
        UrlDao urlDao = new UrlDao();
        List<String> urlList = urlDao.selectAll();
        List<NoticeHtml> htmlList = new ArrayList<>();
        HtmlGet htmlGet = new HtmlGet();
        NoticeHtmlDao noticeHtmlDao = new NoticeHtmlDao();
        for (String noticeUrl : urlList) {
            String body = htmlGet.getHtml(noticeUrl).trim();
            if(body.equals("error")){
                System.out.println("ErrorUrl"+noticeUrl);
            }
            noticeHtmlDao.add1(noticeUrl, body);
            NoticeHtml html = new NoticeHtml(noticeUrl, body);
            htmlList.add(html);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

//        noticeHtmlDao.add(htmlList);

    }

    @Test
    public void getNoticeArticle() {
        NoticeHtmlDao noticeHtmlDao = new NoticeHtmlDao();
        List<NoticeHtml> htmlList = noticeHtmlDao.selectAll();
        NoticeArticleFilter noticeArticleFilter = new NoticeArticleFilter();
        NoticeArticleDao noticeArticleDao = new NoticeArticleDao();
        int i = 0;
        for (NoticeHtml noticeHtml : htmlList) {
            String url = noticeHtml.getUrl();
            String html = noticeHtml.getHtml();
            NoticeArticle noticeArticle = noticeArticleFilter.getArticle(url, html);
            noticeArticleDao.add1(noticeArticle);
            System.out.println(i);
            System.out.println(noticeArticle.getUrl());
            System.out.println(noticeArticle.getTitle());
            System.out.println(noticeArticle.getArticle());
        }
    }

    @Test
    public void addSolr() {
        NoticeArticleDao noticeArticleDao = new NoticeArticleDao();
        List<NoticeArticle> articleList = noticeArticleDao.selectAll();
        SolrJTest solrJTest = new SolrJTest();
        int i = 0;
        for (NoticeArticle noticeArticle : articleList) {
            try {
                solrJTest.addDocNotice(i++, noticeArticle);
            } catch (SolrServerException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void query() {
        SolrJTest solrJTest = new SolrJTest();
        try {
            solrJTest.querySolr("�̲�", "title");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void split() {
        NoticeArticleDao noticeArticleDao = new NoticeArticleDao();
        List<NoticeArticle> articleList = noticeArticleDao.selectAll();
        NoticeParticipleDao noticeParticipleDao = new NoticeParticipleDao();
        for (NoticeArticle noticeArticle : articleList) {
            String title = NLPTokenizer.split(noticeArticle.getTitle());
            String artile = NLPTokenizer.split(noticeArticle.getArticle());
            String url = noticeArticle.getUrl();
            noticeParticipleDao.add1(new Participle(noticeArticle.getId(),
                    title,
                    artile, url,noticeArticle.getTime()));
        }

    }

    @Test
    public void indexByArticle() {
        Map<String, RangeFrequency> indexMap = new Hashtable<>();
        NoticeParticipleDao noticeParticipleDao = new NoticeParticipleDao();
        List<Participle> participleList = noticeParticipleDao.selectAll();
        for (Participle participle : participleList) {
            String[] participles = participle.getArticle().split(" ");
            int id = participle.getId();
            for (int i = 0; i < participles.length; i++) {
                indexMap = Utils.intoMap(indexMap, participles[i], id);
            }
        }
        ParticipleDao participleDao = new ParticipleDao();
        participleDao.addArticle(indexMap);
    }

    @Test
    public void indexByTitle() {
        Map<String, RangeFrequency> indexMap = new Hashtable<>();
        NoticeParticipleDao noticeParticipleDao = new NoticeParticipleDao();
        List<Participle> participleList = noticeParticipleDao.selectAll();
        for (Participle participle : participleList) {
            String[] participles = participle.getTitle().split(" ");
            int id = participle.getId();
            for (int i = 0; i < participles.length; i++) {
                indexMap = Utils.intoMap(indexMap, participles[i], id);
            }
        }
        ParticipleDao participleDao = new ParticipleDao();
        participleDao.addTitle(indexMap);
    }


    public void selectByArticle(String key) {
        List<String> keyList = NLPTokenizer.indexTokenizer(key);
        List<NoticeArticle> resultList = new ArrayList<>();
        ParticipleDao participleDao = new ParticipleDao();
        Set<Double> set = new HashSet<>();
        for (String k : keyList) {
            if(participleDao.selectArticle(k)!=null){
                set.addAll(participleDao.selectArticle(k));
            }

        }
        NoticeArticleDao noticeArticleDao = new NoticeArticleDao();

        for (Double i : set) {
            NoticeArticle noticeArticle = noticeArticleDao.selectById(i.intValue());
            resultList.add(noticeArticle);
        }


        count(resultList,keyList);
    }

    public void count(List<NoticeArticle> articleList, List<String> keyList) {

        for (int j = 0; j < articleList.size(); j++) {

            NoticeArticle article = articleList.get(j);
            article.setUrl(Utils.COOLOR1 + "原页面Url: " + article.getUrl());
            article.setTime(Utils.GREEN + "发布时间: " + article.getTime()+Utils.YELLOW);
            String[] strs1 = article.getTitle().split(" ");
            StringBuffer sb1 = new StringBuffer("标题： ");
            for (int i = 0; i < strs1.length; i++) {
                sb1.append(strs1[i]);
            }
            article.setTitle(sb1.append(Utils.WHITE).toString().trim());

            String[] strs = article.getArticle().split(" ");
            StringBuffer sb = new StringBuffer("文章： ");
            for (int i = 0; i < strs.length; i++) {
                boolean flag = false;
                for (String key : keyList) {
                    if (strs[i].equals(key)) {
                        sb.append(Utils.RED).append(strs[i]);
                        article.addMap(key);
                        flag = true;
                        break;
                    }
                }
                if (flag==false) {
                    sb.append(Utils.WHITE).append(strs[i]);

                }else {
                    flag = false;
                }
            }
            article.setArticle(sb.append(Utils.OFF).toString().trim());
        }
        print(articleList,keyList);
    }


    //    public void selectByTitle(String key){
//        List<String> keyList = NLPTokenizer.indexTokenizer(key);
//        Map<Integer,NoticeArticle> articleMap = new HashMap<>();
//        for(String k : keyList){
//            ParticipleDao participleDao = new ParticipleDao();
//            Set<Double> set = participleDao.selectTitle(k);
//            NoticeArticleDao noticeArticleDao = new NoticeArticleDao();
//            for(Double i : set){
//                NoticeArticle noticeArticle = noticeArticleDao.selectById(i.intValue());
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        System.out.println("搜索结果共"+set.size()+"条");
//        System.out.println("序号： "+count+++": ");
//        print(noticeArticle,key);
//
//
//    }
    @Test
    public void test() {
        getNoticeUrl();
    }

    public void print(List<NoticeArticle> articleList, List<String> keyList) {
        StringBuffer sb1 = new StringBuffer();
        for (String key : keyList) {
            sb1.append(key).append(" ");
        }
        System.out.println("为您提取出关键词："+sb1.toString());
        if(articleList.size()==0){
            System.out.println("无搜索结果");
        }else {
            System.out.println("搜索结果共" + articleList.size() + "条,默认显示前50条,搜索结果按发布时间倒序展示");
        }

        Collections.sort(articleList);
        int count =1;
        for (NoticeArticle noticeArticle : articleList){
            System.out.println(Utils.WHITE+"序号： "+count+++": ");
            Map<String,Integer> map = noticeArticle.getMap();
            StringBuffer sb = new StringBuffer(Utils.BLUE+"搜索到关键词： ");
            for (Map.Entry<String,Integer> entry: map.entrySet()){
                sb.append(entry.getKey()).append("-->出现次数：").append(entry.getValue()).append(" ");
            }
            System.out.println(sb.toString());
            System.out.println(noticeArticle.getUrl());
            System.out.println(noticeArticle.getTime());
            System.out.println(noticeArticle.getTitle());
            System.out.println(noticeArticle.getArticle());

            System.out.println();
            System.out.println();
            if (count == 51){
                break;
            }
        }

    }


}
