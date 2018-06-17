package yiqun;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class First {
    public static void main(String[] args) throws Exception{
        RequestConfig requestConfig = RequestConfig.custom()
        .setConnectTimeout(5000)
                .setSocketTimeout(5000)
                .setConnectionRequestTimeout(5000)  
                .build();
        BasicCookieStore cookieStore = new BasicCookieStore();
        CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultCookieStore(cookieStore)
                .build();
        HttpGet httpGet = new HttpGet("http://jwzx.hrbust.edu.cn/homepage/index.do");

        httpGet.setConfig(requestConfig);
        HttpResponse response = httpclient.execute(httpGet);
    
        if (response.getStatusLine().getStatusCode() == 200) {  
            HttpEntity resEntity = response.getEntity();  
            String message = EntityUtils.toString(resEntity, "utf-8");
            System.out.println(message);  
        } else {  
            System.out.println("请求失败");  
        }  
    }

    public void disguise(HttpGet httpGet){
        httpGet.setHeader("Accept", "Accept text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        httpGet.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
        httpGet.setHeader("Accept-Encoding", "gzip, deflate");
        httpGet.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
        httpGet.setHeader("Connection", "keep-alive");
        httpGet.setHeader("Cookie", "JSESSIONID=4A74B157A7B8B99DBFD04A0B43BDD037.TH");
        httpGet.setHeader("Host", "jwzx.hrbust.edu.cn");
        httpGet.setHeader("refer", "http://jwzx.hrbust.edu.cn/");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36");

    }
   
}  