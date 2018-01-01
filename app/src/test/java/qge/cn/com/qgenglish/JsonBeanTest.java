package qge.cn.com.qgenglish;

import com.google.gson.annotations.SerializedName;

/**
 * Created by fony on 2017/11/20.
 */

public class JsonBeanTest {


    @SerializedName("success")
    private boolean _$Success231; // FIXME check this code
    private DataBean data;

    public boolean is_$Success231() {
        return _$Success231;
    }

    public void set_$Success231(boolean _$Success231) {
        this._$Success231 = _$Success231;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        @SerializedName("default")
        private String _$DefaultX189; // FIXME check this code
        private ReqBean _req;
        private String name;

        public String get_$DefaultX189() {
            return _$DefaultX189;
        }

        public void set_$DefaultX189(String _$DefaultX189) {
            this._$DefaultX189 = _$DefaultX189;
        }

        public ReqBean get_req() {
            return _req;
        }

        public void set_req(ReqBean _req) {
            this._req = _req;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public static class ReqBean {
            @SerializedName("method")
            private String _$Method326; // FIXME check this code
            private String url;
            private HeaderBean header;

            public String get_$Method326() {
                return _$Method326;
            }

            public void set_$Method326(String _$Method326) {
                this._$Method326 = _$Method326;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public HeaderBean getHeader() {
                return header;
            }

            public void setHeader(HeaderBean header) {
                this.header = header;
            }

            public static class HeaderBean {
                @SerializedName("host")
                private String _$Host101; // FIXME check this code
                @SerializedName("x-forwarded-for")
                private String xforwardedfor;
                private String connection;
                private String accept;
                @SerializedName("user-agent")
                private String useragent;
                @SerializedName("content-type")
                private String contenttype;
                private String referer;
                @SerializedName("accept-encoding")
                private String acceptencoding;
                @SerializedName("accept-language")
                private String acceptlanguage;
                private String cookie;

                public String get_$Host101() {
                    return _$Host101;
                }

                public void set_$Host101(String _$Host101) {
                    this._$Host101 = _$Host101;
                }

                public String getXforwardedfor() {
                    return xforwardedfor;
                }

                public void setXforwardedfor(String xforwardedfor) {
                    this.xforwardedfor = xforwardedfor;
                }

                public String getConnection() {
                    return connection;
                }

                public void setConnection(String connection) {
                    this.connection = connection;
                }

                public String getAccept() {
                    return accept;
                }

                public void setAccept(String accept) {
                    this.accept = accept;
                }

                public String getUseragent() {
                    return useragent;
                }

                public void setUseragent(String useragent) {
                    this.useragent = useragent;
                }

                public String getContenttype() {
                    return contenttype;
                }

                public void setContenttype(String contenttype) {
                    this.contenttype = contenttype;
                }

                public String getReferer() {
                    return referer;
                }

                public void setReferer(String referer) {
                    this.referer = referer;
                }

                public String getAcceptencoding() {
                    return acceptencoding;
                }

                public void setAcceptencoding(String acceptencoding) {
                    this.acceptencoding = acceptencoding;
                }

                public String getAcceptlanguage() {
                    return acceptlanguage;
                }

                public void setAcceptlanguage(String acceptlanguage) {
                    this.acceptlanguage = acceptlanguage;
                }

                public String getCookie() {
                    return cookie;
                }

                public void setCookie(String cookie) {
                    this.cookie = cookie;
                }
            }
        }
    }
}
