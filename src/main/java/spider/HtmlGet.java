package spider;

import org.apache.http.HttpEntity;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import javax.swing.text.html.HTML;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by maqiyue on 2018/5/8
 */
public class HtmlGet {

    public String getHtml(String url) {
        BasicCookieStore cookieStore = new BasicCookieStore();
        CloseableHttpClient httpclient = HttpClients.custom()
            .setDefaultCookieStore(cookieStore)
            .build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        httpGet.setHeader("Accept-Encoding","gzip, deflate");
        httpGet.setHeader("Accept-Language","zh-CN,zh;q=0.9");
        httpGet.setHeader("Cache-Control","max-age=0");
        httpGet.setHeader("Connection","keep-alive");
        httpGet.setHeader("Cookie","JSESSIONID=64CC762548BB8AB22C5EF2C04B9D5ABF.TH");
        httpGet.setHeader("Host","jwzx.hrbust.edu.cn");
        httpGet.setHeader("Referer","http://jwzx.hrbust.edu.cn/homepage/index.do");
        httpGet.setHeader("Upgrade-Insecure-Requests","1");
        httpGet.setHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36");
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String body = null;
        try {
            CloseableHttpResponse response=httpclient.execute(httpGet);
            int code = response.getStatusLine().getStatusCode();
            HttpEntity entity=response.getEntity();
            if(code == 200){
                body = EntityUtils.toString(entity, "utf-8");
            } else {
                return "error";
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpGet.abort();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return body;
    }




}
