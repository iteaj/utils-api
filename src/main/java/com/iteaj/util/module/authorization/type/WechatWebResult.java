package com.iteaj.util.module.authorization.type;

import com.iteaj.util.module.authorization.http.HttpAuthorizeResult;
import com.iteaj.util.module.authorization.http.SessionStorageContext;

/**
 * Created by iteaj on 2017/3/14.
 */
public class WechatWebResult extends HttpAuthorizeResult {

    private String code;
    private UserInfo userInfo;
    private AccessToken accessToken;

    public WechatWebResult(SessionStorageContext context) {
        super(context);
        this.code = (String)context.getParam("code");
        this.userInfo = (UserInfo) context.getParam("user");
        this.accessToken = (AccessToken) context.getParam("token");
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public AccessToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * 微信网页授权返回的AccessToken对象
     */
    public static class AccessToken extends WechatResultEntityAbstract{
        private String scope;
        private String openid;
        private String expires_in;
        private String access_token;
        private String refresh_token;

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getExpires_in() {
            return expires_in;
        }

        public void setExpires_in(String expires_in) {
            this.expires_in = expires_in;
        }

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public String getRefresh_token() {
            return refresh_token;
        }

        public void setRefresh_token(String refresh_token) {
            this.refresh_token = refresh_token;
        }

        @Override
        public String toString() {
            return "AccessToken{" +
                    "scope='" + scope + '\'' +
                    ", openid='" + openid + '\'' +
                    ", expires_in='" + expires_in + '\'' +
                    ", access_tToken='" + access_token + '\'' +
                    ", refresh_token='" + refresh_token + '\'' +
                    '}';
        }
    }

    /**
     * 微信返回的用户信息
     */
    public static class UserInfo extends WechatResultEntityAbstract{
        private String sex;
        private String city;
        private String openid;
        private String unionid;
        private String country;
        private String nickname;
        private String province;
        private String privilege;
        private String headimgurl;

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getUnionid() {
            return unionid;
        }

        public void setUnionid(String unionid) {
            this.unionid = unionid;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getPrivilege() {
            return privilege;
        }

        public void setPrivilege(String privilege) {
            this.privilege = privilege;
        }

        public String getHeadimgurl() {
            return headimgurl;
        }

        public void setHeadimgurl(String headimgurl) {
            this.headimgurl = headimgurl;
        }

        @Override
        public String toString() {
            return "UserInfo{" +
                    "sex='" + sex + '\'' +
                    ", city='" + city + '\'' +
                    ", openid='" + openid + '\'' +
                    ", unionid='" + unionid + '\'' +
                    ", country='" + country + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", province='" + province + '\'' +
                    ", privilege='" + privilege + '\'' +
                    ", headimgurl='" + headimgurl + '\'' +
                    '}';
        }
    }
}
