import com.jiang.po.CourseBase;
import com.xuecheng.base.exception.XueChengPlusException;
import javafx.util.converter.LocalDateTimeStringConverter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MyCommonTest {

    @Autowired
    private LocalDateTimeStringConverter localDateTimeStringConverter;

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

    @Test
    public void test() {
        List<String> list = new ArrayList<>();
        list.add("张无忌");
        list.add("周芷若");
        list.add("赵敏");
        list.add("张强");
        list.add("张三丰");

        List<Integer> list1 = Stream.iterate(0, new UnaryOperator<Integer>() {
            @Override
            public Integer apply(Integer integer) {
                return ++integer;
            }
        }).limit(3).map(i -> i + 2).collect(Collectors.toList());
        System.out.println(list1);

        list.stream().map(i -> i.concat("hello")).forEach(System.out::println);

    }

    @Test
    public void test02() throws IOException {
        File tempFile = File.createTempFile("minio", ".temp");
        //filedata.transferTo(tempFile);
        System.out.println(tempFile);
    }


    @Test
    public void test03() {
        System.out.println("11111111111111");

        if (0 == 1) {

            XueChengPlusException.cast("异常信息出现了，，，");
            //return;
        }
        System.out.println("==============");
        System.out.println("222222222222222222222");

        int i = Runtime.getRuntime().availableProcessors();
        System.out.println(i);
    }

    @Test
    public void test04() {

        //XueChengPlusException.cast("111111");

        CourseBase courseBase = null;
        //System.out.println(courseBase.getAuditStatus());
    }


    @Test
    public void test05(){
        System.out.println("2023-05-04 00:19:34");
        System.out.println(LocalDateTime.now());
    }



}
