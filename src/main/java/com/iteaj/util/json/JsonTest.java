package com.iteaj.util.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iteaj.util.json.fastjson.FastJsonAdapter;
import com.iteaj.util.json.jackson.JacksonAdapter;

/**
 * create time: 2018/3/29
 *
 * @author iteaj
 * @version 1.0
 * @since 1.7
 */
public class JsonTest {

    public static void main(String[] args) {
        JsonAdapter adapter = new JacksonAdapter();
        long l = System.currentTimeMillis();
        for(int i=0; i<5; i++) {
            JsonWrapper build = adapter.build();
            build.addNode("hello", new Test(1, "3"))
                    .addNode("who", new String[]{"1", "2", "3"})
                    .addNode("doing", adapter.build());

            NodeWrapper who = build.getNode("who");
            NodeWrapper doing = build.getNode("doing");
            doing.addNode("test", who);
        }
        System.out.println((System.currentTimeMillis()-l));
    }

    static class Test {
        private int id;
        private String name;

        public Test(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
