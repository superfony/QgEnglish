package qge.cn.com.qgenglish;

/**
 * Created by fony on 2017/12/6.
 */

public class Root {


    /**
     * qgroot : {"requestcode":" 0","message":"成功失败信息"}
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
         * requestcode :  0
         * message : 成功失败信息
         */

        private String requestcode;
        private String message;

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
    }
}
