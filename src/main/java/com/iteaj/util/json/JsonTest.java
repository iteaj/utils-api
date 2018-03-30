package com.iteaj.util.json;

/**
 * create time: 2018/3/29
 *
 * @author iteaj
 * @version 1.0
 * @since 1.7
 */
public class JsonTest {

    public static void main(String[] args) {
        JsonWrapper wrapper = JsonFactory.create();
        wrapper.put("hello", "world")
                .put("test", new Test(1, "3"));

        NodeWrapper test = wrapper.get("test");
        NodeWrapper hello = wrapper.get("hello");
        test.put(hello);

        System.out.println(test.toJsonString());

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
