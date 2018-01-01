package qge.cn.com.qgenglish.app.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fony on 2017/12/22.
 */

public class User implements Serializable{


    /**
     * request : 1
     * message : 成功失败信息
     * userinfo : {"userid":"学生id","usercode":"学生编号","username":"学生姓名","userimg":"头像url","role":"角色类型"}
     * permission : [{"code":"功能编号","des":"功能描述","state":"功能状态1/0"},{"code":"功能编号","des":"功能描述","state":"功能状态1/0"}]
     */

    private String request;
    private String message;
    private UserinfoBean userinfo;
    private List<PermissionBean> permission;

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserinfoBean getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserinfoBean userinfo) {
        this.userinfo = userinfo;
    }

    public List<PermissionBean> getPermission() {
        return permission;
    }

    public void setPermission(List<PermissionBean> permission) {
        this.permission = permission;
    }

    public static class UserinfoBean implements Serializable{
        /**
         * userid : 学生id
         * usercode : 学生编号
         * username : 学生姓名
         * userimg : 头像url
         * role : 角色类型
         */

        private String userid;
        private String usercode;
        private String username;
        private String userimg;
        private String role;

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getUsercode() {
            return usercode;
        }

        public void setUsercode(String usercode) {
            this.usercode = usercode;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUserimg() {
            return userimg;
        }

        public void setUserimg(String userimg) {
            this.userimg = userimg;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }

    public static class PermissionBean implements Serializable{
        /**
         * code : 功能编号
         * des : 功能描述
         * state : 功能状态1/0
         */

        private String code;
        private String des;
        private String state;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }
}
