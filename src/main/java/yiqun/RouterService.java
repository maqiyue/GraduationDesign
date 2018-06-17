package yiqun;

/**
 * Created by maqiyue on 2018/5/28
 */
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 简易路由器
 * @author Administrator
 *
 */
public class RouterService {
    HashMap<String, String> routerInfoMap = new HashMap<String, String>();
    /**
     * 载入路由表规则：
     * 1、号段num和网元都不能重复
     * 2、号段num不能重复
     * @param routerRule 路由规则
     * @return
     */
    public boolean loadRule(String routerRule) {
        try {
            Document doc = DocumentHelper.parseText(routerRule);
            Element routertable = doc.getRootElement();
            Iterator routerIterator = routertable.elementIterator("router");
            while(routerIterator.hasNext())
            {
                Element router = (Element) routerIterator.next();
                String num = router.elementTextTrim("num");
                String net = router.elementTextTrim("net");
                if(num.isEmpty()||net.isEmpty())
                {
                    return false;
                }

                if(routerInfoMap.containsKey(num))
                {
                    return false;
                }
                else {
                    routerInfoMap.put(num, net);
                }

            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    /**
     * 选择路由
     * 1、选择号码适配长度最长的网元为路由目的地
     * @param phone 电话号码
     * @return
     */
    public String selRouter(String phone) {
        Iterator iterator = routerInfoMap.entrySet().iterator();
        String num = "";
        String net = "";
        while (iterator.hasNext()) {
            Entry<String, String> entry = (Entry<String, String>)iterator.next();
            String key = entry.getKey();
            if(phone.indexOf(key)>-1)
            {
                if(key.length()>num.length())
                {
                    net = entry.getValue();
                }
            }
        }

        return net;
    }
}