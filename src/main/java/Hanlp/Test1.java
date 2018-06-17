package Hanlp;

import java.util.List;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.IndexTokenizer;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;

public class Test1 {
    public static void main(String[] args) {

        //第一次运行会有文件找不到的错误但不影响运行，缓存完成后就不会再有了
        System.out.println("标准分词：");
        System.out.println(HanLP.segment("关于举办第四届哈尔滨理工大学“互联网+”大学生创新创业大赛赛前培训的通知"));
        System.out.println("\n");

        List<Term> termList = NLPTokenizer.segment("关于举办第四届哈尔滨理工大学“互联网+”大学生创新创业大赛赛前培训的通知");
        System.out.println("NLP分词：");
        System.out.println(termList);
        System.out.println("\n");


        System.out.println("关键字提取：");
        List<String> keywordList = HanLP.extractKeyword("关于举办第四届哈尔滨理工大学“互联网+”大学生创新创业大赛赛前培训的通知", 3);
        System.out.println(keywordList);
        System.out.println("\n");

        System.out.println("索引分词：");
        List<Term> termList1 = IndexTokenizer.segment("主副食品");
        for (Term term : termList1)
        {
            System.out.println(term + " [" + term.offset + ":" + (term.offset + term.word.length()) + "]");
        }
      
    }


    /**
     * 关键字提取
     */
    public static void getMainIdea() {

        List<String> keywordList = HanLP.extractKeyword("关于举办第四届哈尔滨理工大学“互联网+”大学生创新创业大赛赛前培训的通知", 5);
        System.out.println(keywordList);
    }



}