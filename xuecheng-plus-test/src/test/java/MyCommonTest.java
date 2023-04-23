import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class MyCommonTest {


    @Test
    public void test01() {

        List<String> list = new ArrayList<>();
        list.add("张无忌");
        list.add("周芷若");
        list.add("赵敏");
        list.add("张强");
        list.add("张三丰");

        //list.stream().filter(s -> s.startsWith("张")).filter(s -> s.length() == 3).forEach(System.out::println);


        /*String str = "10a张三丰";
        System.out.println(str.length());
        if(str.startsWith("1")){
            System.out.println("111");
        }*/

        list.forEach(System.out::println);

        System.out.println("==========================");

    }


}
