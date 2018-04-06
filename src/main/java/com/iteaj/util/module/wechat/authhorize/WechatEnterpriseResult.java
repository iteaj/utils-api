package com.iteaj.util.module.wechat.authhorize;

import com.iteaj.util.module.json.JsonWrapper;
import com.iteaj.util.module.oauth2.AbstractStorageContext;
import com.iteaj.util.module.oauth2.AbstractAuthorizeResult;

/**
 * Create Date By 2017-04-27
 *
 * @author iteaj
 * @since 1.7
 */
public class WechatEnterpriseResult extends AbstractAuthorizeResult {

    private String code;
    private UserInfo userInfo;
    private UserDetail userDetail;
    private JsonWrapper accessToken;

    public WechatEnterpriseResult(AbstractStorageContext context) {
        super(context);
    }

    @Override
    public void build() {
        this.code = (String)context.getContextParam("code");
        this.userInfo = (UserInfo)context.getContextParam("user");
        this.userDetail = (UserDetail) context.getContextParam("detail");
        this.accessToken = (JsonWrapper) context.getContextParam("token");
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

    public JsonWrapper getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(JsonWrapper accessToken) {
        this.accessToken = accessToken;
    }
}
