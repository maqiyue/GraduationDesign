package util;

import org.fusesource.jansi.Ansi;
import vo.RangeFrequency;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * Created by maqiyue on 2018/5/20
 */
public class Utils {

//    public static final String BLICK = "\u001b[30m";
//    public static final String RED = "\u001b[31m";
//    public static final String GREEN = "\u001b[32m";
//    public static final String YELLOW = "\u001b[33m";
//    public static final String BLUE = "\u001b[34m";
//    public static final String COOLOR1 = "\u001b[35m";
//    public static final String COOLOR2 = "\u001b[36m";
//    public static final String WHITE = "\u001b[37m";
//    public static final String OFF = "\u001b[0m";

    public static final String BLICK = "\u001b[0m";
    public static final String RED = "\u001b[0m";
    public static final String GREEN = "\u001b[0m";
    public static final String YELLOW = "\u001b[0m";
    public static final String BLUE = "\u001b[0m";
    public static final String COOLOR1 = "\u001b[0m";
    public static final String COOLOR2 = "\u001b[0m";
    public static final String WHITE = "\u001b[0m";
    public static final String OFF = "\u001b[0m";

    public static Map<String, RangeFrequency> intoMap (Map<String,RangeFrequency> map, String key, int id){
        if(map.containsKey(key)){
            RangeFrequency rangeFrequency = map.get(key);
            rangeFrequency.setFrequency(rangeFrequency.getFrequency()+1);
            rangeFrequency.add(id);
            map.put(key,rangeFrequency);
        }else {
            RangeFrequency rangeFrequency = new RangeFrequency();
            rangeFrequency.setFrequency(1);
            Set<Integer> set = new HashSet<>();
            set.add(id);
            rangeFrequency.setRange(set);
            map.put(key,rangeFrequency);
        }
        return map;
    }

    public static void printWithRed(String str){
        System.out.println(ansi().eraseScreen().fg(Ansi.Color.RED).a(str));
    }
    public static void printWithRedNoSpace(String str){
        System.out.print(ansi().eraseScreen().fg(Ansi.Color.RED).a(str));
    }

    public static void printWithBlackNoSpace(String str){
        System.out.print(ansi().eraseScreen().fg(Ansi.Color.WHITE).a(str));
    }

    public static void printWithBlack(String str){
        System.out.println(ansi().eraseScreen().fg(Ansi.Color.WHITE).a(str));
    }

    public static void printWithGreen(String str){
        System.out.println(ansi().eraseScreen().fg(Ansi.Color.GREEN).a(str));
    }

    public static void printWithBlue(String str){
        System.out.println(ansi().eraseScreen().fg(Ansi.Color.BLUE).a(str));
    }


}
