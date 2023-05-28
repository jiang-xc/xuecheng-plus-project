package com.jiang.test;

import com.jiang.po.CourseBase;
import com.xuecheng.base.exception.XueChengPlusException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
//@SpringBootTest
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
    public void test05() {
        System.out.println("2023-05-04 00:19:34");
        System.out.println(LocalDateTime.now());
    }

    @Test
    public void test06() {
        Date date = new Date(1683646284L * 1000);
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(date);
    }


    @Test
    public void test07() {
        String[] strings = new String[10];
        strings[0] = "aaa";
        strings[1] = "bbb";
        strings[2] = "ccc";
        strings[3] = "ddd";
        strings[4] = "eee";
        List<String> arrayList = new ArrayList<>();
        arrayList.add("mmm");
        arrayList.add("nnn");
        arrayList.add("vvv");
        arrayList.add("ccc");
        arrayList.add("xxx");
        String[] strings1 = arrayList.toArray(new String[0]);
        System.out.println(Arrays.toString(strings));
        System.out.println("strings111" + Arrays.toString(strings1));

    }


    @Test
    public void test08() {
        //List<String> list = new ArrayList<>();

        List<String> list = null;
        /*if (list == null) {
            System.out.println("list列表不存在");
        }*/

        if (list != null && list.size() > 0) {
            //System.out.println("list列表不存在或list内部元素为0");
            System.out.println("111111111111");
        }

        /*if(CollectionUtils.isEmpty(list)){
            System.out.println("list列表不存在或list内部元素为0");
        }*/
    }


    @Test
    public void test09() {
        //Map<String, String> map = new HashMap<>();
        Map<String, String> map = null;

        /*if (map == null || map.size() == 0) {
            System.out.println("map为null或者map中没有元素");
        }*/

        if (map != null && map.size() > 0) {
            System.out.println("map为null或者map中没有元素");
        }
    }

    @Test
    public void test10() {
        if ("123".equals("123")) {
            System.out.println(111);
        } else if (2 > 0) {
            System.out.println(222);
        }
    }


    @Test
    public void test11() {

        Random random = new Random();
        //生成一个0到8999之间的随机整数，再加上1000，就可以得到一个4位随机数字
        int randomNumber = random.nextInt(9000) + 1000;
        String randomString = String.valueOf(randomNumber);
        System.out.println("随机生成的4位数字是：" + randomString);

    }


    @Test
    public void test12() {

        List<String> arrayList = new ArrayList<>();
        arrayList.add("eee");
        arrayList.add("aaa");
        arrayList.add("bbb");
        arrayList.add("ccc");
        arrayList.add("ddd");


        System.out.println(arrayList.get(2));

    }


    @Test
    public void test13() throws InterruptedException {

        LocalDateTime beforeTime = LocalDateTime.now();
        System.out.println("beforeTime:" + beforeTime);
        Thread.sleep(5000);
        System.out.println();
        LocalDateTime nowTime = LocalDateTime.now();
        System.out.println("nowTime:" + nowTime);
        if (beforeTime.isBefore(nowTime)) {
            System.out.println("1111");
        }

    }


    @Test
    public void test14() {
        int a = 10;
        int b = 20;
        if(a<=b){
            System.out.println("a<=b"+true);
        }

        if(a<b){
            System.out.println("a<b"+true);
        }
    }

    @Test
    public void test15() {
        Float a = 1.11F*100;
        Float b = 2.99F*100;
        System.out.println(a.intValue());
        System.out.println(b.intValue());
    }


    @Test
    public void test16() {
        Float a = 1.11F*100;
        Float b = 2.99F*100;
        //System.out.println(a.intValue());
        //System.out.println(b.intValue());
        /*if(true){
            return;
        }*/

        System.out.println("111");
    }
}
