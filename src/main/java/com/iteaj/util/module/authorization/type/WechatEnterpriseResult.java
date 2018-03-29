package com.iteaj.util.module.authorization.type;

import com.alibaba.fastjson.JSONObject;
import com.iteaj.util.module.authorization.http.HttpAuthorizeResult;
import com.iteaj.util.module.authorization.http.SessionStorageContext;

/**
 * Create Date By 2017-04-27
 *
 * @author iteaj
 * @since 1.7
 */
public class WechatEnterpriseResult extends HttpAuthorizeResult {

    private String code;
    private UserInfo userInfo;
    private UserDetail userDetail;
    private JSONObject accessToken;

    public WechatEnterpriseResult(SessionStorageContext context) {
        super(context);
        this.code = (String)context.getParam("code");
        this.accessToken = (JSONObject) context.getParam("token");
        this.userInfo = (UserInfo)context.getParam("user");
        this.userDetail = (UserDetail) context.getParam("detail");
    }

    public static class UserInfo extends WechatResultEntityAbstract{
        private String userId;
        private String deviceId;
        private String expires_in;
        private String user_ticket;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getExpires_in() {
            return expires_in;
        }

        public void setExpires_in(String expires_in) {
            this.expires_in = expires_in;
        }

        public String getUser_ticket() {
            return user_ticket;
        }

        public void setUser_ticket(String user_ticket) {
            this.user_ticket = user_ticket;
        }
    }

    public static class UserDetail extends WechatResultEntityAbstract {
        private String userid;
        private String name;
        private String department;
        private String position;
        private String mobile;
        private String gender;
        private String email;
        private String avatar;

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public JSONObject getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(JSONObject accessToken) {
        this.accessToken = accessToken;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public UserDetail getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
    }
}
