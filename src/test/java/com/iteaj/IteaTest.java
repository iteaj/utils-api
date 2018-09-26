package com.iteaj;

import com.iteaj.util.JsonUtils;
import com.iteaj.util.module.json.Json;
import com.iteaj.util.module.json.JsonAdapter;
import com.iteaj.util.module.json.JsonArray;
import com.iteaj.util.module.json.fastjson.FastJsonAdapter;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * create time: 2018/9/19
 *
 * @author iteaj
 * @since 1.0
 */
public class IteaTest {
    FastJsonAdapter adapter = new FastJsonAdapter();

    @Test
    public void fastJson() {
        FastJsonAdapter adapter = new FastJsonAdapter();
        Json build = adapter.builder();

        System.out.println(build.toJsonString());
    }

    @Test
    public void jackson() {
        String s = "{\"name\":\"iteaj\",\"30\":null,\"we\":null , \"obj\": {\"name\":\"iteaj\",\"30\":true}}";

        Json builder = JsonUtils.builder(s);
        String kk = builder.getString("30");

        FastJsonAdapter adapter = new FastJsonAdapter();
        adapter.builder("3");

        System.out.println(builder);
    }

    @Test
    public void array() {
        String aInt = "{\"name\":\"iteaj\", \"ani\": [1,2,3]}";
        Json aJson = JsonUtils.builder(aInt);
        JsonArray ani = aJson.getJsonArray("ani");
        ArrayList<Integer> integers = ani.toList(Integer.class);

        String aObj = "{\"name\":\"iteaj\", \"ani\": {\"age\":1,\"name\":\"iteaj\",\"time\":1537421793322}}";
        Json builder = JsonUtils.builder(aObj);
        Json build = adapter.builder(aObj);
        Json path1 = (Json)build.getPath("/ani");
        Object o = path1.toBean(TestInner.class);

        String aAry = "{\"name\":\"iteaj\", \"ani\": [{\"age\":1,\"name\":\"iteaj\",\"time\":1537421793322},{},null]}";
        Json aAryJson = JsonUtils.builder(aAry);
        JsonArray ani2 = aAryJson.getJsonArray("ani");
        ArrayList<TestInner> testInners = ani2.toList(TestInner.class);
        JsonArray jsonArray = ani2.getJsonArray(2);

        JsonArray path = (JsonArray)adapter.builder(aAry).getPath("/ani");

        String ary = "[{\"name\":\"iteaj\", \"ani\": [[3],[4],[5]]}, 3, {}]";
        Json json1 = JsonUtils.builder(ary);
        JsonArray array = json1.toJsonArray();
        JsonArray ani3 = array.getJson(0).getJsonArray("ani", true);
        ArrayList<ArrayList> arrayLists = ani3.toList(ArrayList.class);
        System.out.println(aInt);
    }

    @Test
    public void toBean() {
//        JsonAdapter adapter = new JacksonAdapter();
        JsonAdapter adapter = new FastJsonAdapter();
        String list = "[\"1\",3,5,8]";
        String[] s = JsonUtils.toArray(list, String[].class);

        FastJsonAdapter adapter1 = new FastJsonAdapter();
        Integer[] strings = adapter1.toArray(list, Integer[].class);

        Map<String, TestInner> map = new HashMap<>();
        map.put("1", new TestInner(1, "iteaj"));
        map.put("2", new TestInner(2, "doing"));
        String toJson = adapter.toJson(map);
        System.out.println(toJson);

        Map toMap = adapter.toMap(toJson, TreeMap.class, String.class, TestInner.class);
        System.out.println(toMap);

        String iteaj = adapter.toJson(new TestInner(1, "iteaj")
                , new SimpleDateFormat("yyyyMMdd hh:mm:ss"));
        System.out.println(iteaj);
    }

    public List getList() {
        List list = new ArrayList();
        list.add("23");
        list.add("5");
        list.add(10);
        list.add(new TestInner(3, "iteaj"));
        return list;
    }

    static class TestInner {
        private int age;
        private String name;
        private Date time = new Date();

        public TestInner() {

        }

        public TestInner(int age, String name) {
            this.age = age;
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Date getTime() {
            return time;
        }

        public void setTime(Date time) {
            this.time = time;
        }
    }
}
