package com.iteaj;

import com.iteaj.util.ApiResponse;
import com.iteaj.util.JsonUtils;
import com.iteaj.util.WebResponse;
import com.iteaj.util.module.json.fastjson.FastJsonAdapter;
import org.junit.Test;

/**
 * create time: 2018/9/20
 *
 * @author iteaj
 * @since 1.0
 */
public class ResponseTest {

    @Test
    public void webResponse() {
        WebResponse add = new WebResponse().add("iteaj", "ok");
        String s = JsonUtils.toJson(add.setStatus(true));
        System.out.println(s);

        assert s != null;
    }

    @Test
    public void apiResponse() {
        ApiResponse response = new ApiResponse(null).setErrMsg("xx");

        String s = JsonUtils.toJson(response);
        System.out.println(s);

        assert s != null;
    }
}
