package Hanlp;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;
import util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maqiyue on 2018/5/20
 */
public class NLPTokenizer {

    public static String split(String str){
        List<Term> termList = com.hankcs.hanlp.tokenizer.NLPTokenizer.segment(str);
        StringBuffer sb = new StringBuffer();
        for (Term term : termList){
            sb.append(term.word).append(" ");
        }
        return sb.toString().trim();
    }

    public static List<String> indexTokenizer(String str){
        List<Term> termList = com.hankcs.hanlp.tokenizer.IndexTokenizer.segment(str);
        List<String> keyList = new ArrayList<>();
        for (Term term : termList){
            keyList.add(term.word);
        }

        return keyList;
    }

    public static List<String> extractKeyWord(String text){ //提取关键词
        List<String> termList = HanLP.extractKeyword(text,3);
        List<String> keyList = new ArrayList<>();
        for (String s : termList){
            keyList.add(s);
        }
        return keyList;
    }

    public static void main(String[] args) {
        String str = "当前采用计算机绘制图形和处理图像的技术已成为现代工程设计与绘图的主要技术手段。为培养学生的大国工匠精神、合作精神，普及先进的制图技术，激发建筑制图的热情和兴趣，提升创新创业实践能力,建筑工程学院建筑学系将于2018年5月27日举办“高教杯”全国大学生先进成图技术与产品信息建模创新大赛校内选拔赛。\n" +
                "该项比赛教育部高等学校工程图学课程教学指导委员会、中国图学学会制图技术专业委员会、中国图学学会产品信息建模专业委员会联合举办，去年共吸引来自全国283所高校的445支代表队的1000多名教师和2916名本科生参加，涵盖了机械类、建筑类、水工类、土道桥类四个类别，在全国范围内影响较大。今年比赛于2018 年 7 月 20 日至 22 日在南京工业大学进行。此前，我校建筑代表队曾在此项竞赛中多次多项获奖，积累了竞赛经验，欢迎广大建筑学系学生参加。";
        System.out.println(str);
        System.out.println();
        System.out.println();
        System.out.println(split(str));
    }
}
