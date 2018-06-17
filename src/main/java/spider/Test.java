package spider;

import java.util.Scanner;

/**
 * Created by maqiyue on 2018/5/27
 */
public class Test {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入要搜索的内容：");
        new Main().selectByArticle(sc.next());
    }
}
//         前景      背景       颜色
//        ---------------------------------------
//        30          40          黑色
//        31          41          红色
//        32          42          绿色
//        33          43          黄色
//        34          44          蓝色
//        35          45          紫红色
//        36          46          青蓝色
//        37          47          白色

//       代码       意义
//-------------------------
//        0            OFF
//        1            高亮显示
//        4            下划线
//        5            闪烁
//        7            反白显示
//        8            不可见