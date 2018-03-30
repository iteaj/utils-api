package com.iteaj.util.http.param;

import com.iteaj.util.CommonUtils;
import com.iteaj.util.UtilsException;
import com.iteaj.util.UtilsType;

import java.util.ArrayList;
import java.util.List;

/**
 * Create Date By 2018-03-30
 *
 * @author iteaj
 * @since 1.7
 */
public class UrlBuilder {

    private String url;
    private List<UrlParam> params;

    private UrlBuilder(String url) {
        this.url = url;
    }

    public static UrlBuilder build(String url) {
        if(!CommonUtils.isNotBlank(url))
            throw new UtilsException("http Url错误", UtilsType.HTTP);

        return new UrlBuilder(url);
    }

    public UrlBuilder addParam(String name, String value) {
        if(null == name || name.length() == 0 || null == value)
            throw new UtilsException("新增的Url参数错误", UtilsType.HTTP);

        if(!CommonUtils.isNotEmpty(params))
            params = new ArrayList<>();

        params.add(new UrlParam(name, value));
        return this;
    }

    class UrlParam {
        private String name;
        private String value;

        public UrlParam(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
