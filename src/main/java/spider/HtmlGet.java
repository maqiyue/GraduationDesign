package spider;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import javax.swing.text.html.HTML;
import java.io.File;
import java.io.FileWriter;
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
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String response = "";
        try {
            response = httpclient.execute(httpGet, responseHandler);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpGet.abort();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }


}
