package solr;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import vo.NoticeArticle;
import vo.Person;

/**
 * Created by maqiyue on 2018/5/20
 */

public class SolrJTest {
    //ָsolrĵַ
    private final static String SOLR_URL = "http://localhost:8983/solr/";

    /**
     * SolrServer
     *
     * öʹã̰߳ȫ
     * 1CommonsHttpSolrServerwebʹõģͨhttp
     * 2 EmbeddedSolrServerǶʽģsolrjarͿʹ
     * 3solr 4.0֮˲ٶCommonsHttpSolrServerΪHttpSolrClient
     *
     * @return
     */
    public HttpSolrClient createSolrServer(){
        HttpSolrClient solr = null;
        solr = new HttpSolrClient(SOLR_URL);
        return solr;
    }


    /**
     * ĵ
     * @throws IOException
     * @throws SolrServerException
     */
    public void addDoc() throws SolrServerException, IOException{
        //һƪĵ
        SolrInputDocument document = new SolrInputDocument();
        //docֶ,ڿͻӵֶαڷй
        document.addField("id", "8");
        document.addField("name", "");
        document.addField("description", "һҳţƵľ¼");
        //һsolr˵ȥύ ,ѡĳһsolr core
        HttpSolrClient solr = new HttpSolrClient(SOLR_URL + "test");
        solr.add(document);
        solr.commit();
        solr.close();
    }

    public void addDocNotice(int i,NoticeArticle noticeArticle) throws SolrServerException, IOException{
        //һƪĵ
        SolrInputDocument document = new SolrInputDocument();
        //docֶ,ڿͻӵֶαڷй
        document.addField("id", i);
        document.addField("url", noticeArticle.getUrl());
        document.addField("title", noticeArticle.getTitle());
        document.addField("article", noticeArticle.getArticle());
        //һsolr˵ȥύ ,ѡĳһsolr core
        HttpSolrClient solr = new HttpSolrClient(SOLR_URL + "test");
        solr.add(document);
        solr.commit();
        solr.close();
    }

    /**
     * idɾĵ
     */
    public void deleteDocumentById() throws Exception {
        //ѡĳһsolr core
        HttpSolrClient server = new HttpSolrClient(SOLR_URL+"test");
        //ɾĵ
        server.deleteById("8");
        //ɾе
        //solr.deleteByQuery("*:*");
        //ύ޸
        server.commit();
        server.close();
    }

    /**
     * ѯ
     * @throws Exception
     */
    public void querySolr() throws Exception{
        HttpSolrClient solrServer = new HttpSolrClient(SOLR_URL+"test/");
        SolrQuery query = new SolrQuery();
        //solrѯ
        //query.set("q", "*:*");// q ѯ
        query.set("q","ǳ");//زѯĳĳֶκܡǡ ѯ ѯ

        //fq, queryӹ˲ѯ
        query.addFilterQuery("id:[0 TO 9]");//idΪ0-4

        //queryӲ
        //query.addFilterQuery("description:Ա"); //descriptionֶкСԱֵ

        //df,queryĬ
        query.set("df", "name");

        //sort,÷ؽ
        query.setSort("id",SolrQuery.ORDER.desc);

        //÷ҳ
        query.setStart(0);
        query.setRows(10);//ÿһҳֵ

        //hl,ø
        query.setHighlight(true);
        //øֶ
        query.addHighlightField("name");
        //øʽ
        query.setHighlightSimplePre("<font color='red'>");
        query.setHighlightSimplePost("</font>");

        //ȡѯ
        QueryResponse response = solrServer.query(query);
        //ֽȡõĵϻʵ

        //ѯõĵļ
        SolrDocumentList solrDocumentList = response.getResults();
        System.out.println("ͨĵϻȡѯĽ");
        System.out.println("ѯ" + solrDocumentList.getNumFound());
        //б
        for (SolrDocument doc : solrDocumentList) {
            System.out.println("id:"+doc.get("id")+"  name:"+doc.get("name")+"  description:"+doc.get("description"));
        }

        //õʵ
        List<Person> tmpLists = response.getBeans(Person.class);
        if(tmpLists!=null && tmpLists.size()>0){
            System.out.println("ͨĵϻȡѯĽ");
            for(Person per:tmpLists){
                System.out.println("id:"+per.getId()+"  name:"+per.getName()+"  description:"+per.getDescription());
            }
        }
    }

    public void querySolr(String q,String type) throws Exception{
        HttpSolrClient solrServer = new HttpSolrClient(SOLR_URL+"test/");
        SolrQuery query = new SolrQuery();
        //solrѯ
        query.set("q",q);
        query.set("df", type);
        //sort,÷ؽ
        query.setSort("id",SolrQuery.ORDER.desc);

        //÷ҳ
        query.setStart(0);
        query.setRows(10);//ÿһҳֵ

        //hl,ø
        query.setHighlight(true);
        //øֶ
        query.addHighlightField("name");
        //øʽ
        query.setHighlightSimplePre("<font color='red'>");
        query.setHighlightSimplePost("</font>");

        //ȡѯ
        QueryResponse response = solrServer.query(query);
        //ֽȡõĵϻʵ

        //ѯõĵļ
        SolrDocumentList solrDocumentList = response.getResults();
        System.out.println("ͨĵϻȡѯĽ");
        System.out.println("ѯ" + solrDocumentList.getNumFound());
        //б
        for (SolrDocument doc : solrDocumentList) {
            System.out.println(new Gson().toJson(doc));
        }

//        //õʵ
//        List<Person> tmpLists = response.getBeans(Person.class);
//        if(tmpLists!=null && tmpLists.size()>0){
//            System.out.println("ͨĵϻȡѯĽ");
//            for(Person per:tmpLists){
//                System.out.println("id:"+per.getId()+"  name:"+per.getName()+"  description:"+per.getDescription());
//            }
//        }
    }

    public static void main(String[] args) throws Exception {
        SolrJTest solr = new SolrJTest();
        //solr.createSolrServer();
        solr.addDoc();

        solr.querySolr();
    }
}
