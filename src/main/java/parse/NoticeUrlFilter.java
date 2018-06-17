package parse;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by maqiyue on 2018/5/19
 */
public class NoticeUrlFilter {

    public static List<String> getNoticeUrlList(String host,String str){
        List<String> list = new ArrayList<>();
        Pattern p = Pattern.compile("infoSingleArticle.+?columnId=354");
        Matcher m = p.matcher(str);
        while (m.find()){
            list.add(new StringBuffer(host).append(m.group()).toString());
        }
        return list;
    }
}
