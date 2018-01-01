package qge.cn.com.qgenglish;

/**
 * Created by fony on 2017/12/6.
 */

public class TestUser {


    /**
     * qgroot : {"requestcode":" 1/0","message":"成功失败信息","userinfo":{"persionid":"学生id","persioncode":"工号","persionname":"姓名","persionimg":"头像url","role":"角色类型"}}
     */

    private QgrootBean qgroot;

    public QgrootBean getQgroot() {
        return qgroot;
    }

    public void setQgroot(QgrootBean qgroot) {
        this.qgroot = qgroot;
    }

    public static class QgrootBean {
        /**
         * requestcode :  1/0
         * message : 成功失败信息
         * userinfo : {"persionid":"学生id","persioncode":"工号","persionname":"姓名","persionimg":"头像url","role":"角色类型"}
         */

        private String requestcode;
        private String message;
        private UserinfoBean userinfo;

        public String getRequestcode() {
            return requestcode;
        }

        public void setRequestcode(String requestcode) {
            this.requestcode = requestcode;
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

        public static class UserinfoBean {
            /**
             * persionid : 学生id
             * persioncode : 工号
             * persionname : 姓名
             * persionimg : 头像url
             * role : 角色类型
             */

            private String persionid;
            private String persioncode;
            private String persionname;
            private String persionimg;
            private String role;

            public String getPersionid() {
                return persionid;
            }

            public void setPersionid(String persionid) {
                this.persionid = persionid;
            }

            public String getPersioncode() {
                return persioncode;
            }

            public void setPersioncode(String persioncode) {
                this.persioncode = persioncode;
            }

            public String getPersionname() {
                return persionname;
            }

            public void setPersionname(String persionname) {
                this.persionname = persionname;
            }

            public String getPersionimg() {
                return persionimg;
            }

            public void setPersionimg(String persionimg) {
                this.persionimg = persionimg;
            }

            public String getRole() {
                return role;
            }

            public void setRole(String role) {
                this.role = role;
            }
        }
    }
}
