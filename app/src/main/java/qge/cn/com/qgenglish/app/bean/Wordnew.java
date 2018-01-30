package qge.cn.com.qgenglish.app.bean;

import java.util.List;

/**
 * Created by fony on 2018/1/16.
 */

public class Wordnew {
    /**
     * code : 200
     * data : [{"english":"abandon","id":3,"pass":null,"phonetic":"","queue":null,"sen":"","sense":"丢弃;放弃，抛弃","szh":"","voicePath":""},{"english":"aboard","id":4,"pass":null,"phonetic":"","queue":null,"sen":"","sense":"在船(车)上;上船","szh":"","voicePath":""},{"english":"absolute","id":5,"pass":null,"phonetic":"","queue":null,"sen":"","sense":"绝对的;纯粹的","szh":"","voicePath":""},{"english":"absolutely","id":6,"pass":null,"phonetic":"","queue":null,"sen":"","sense":"完全地;绝对地","szh":"","voicePath":""},{"english":"absorb","id":7,"pass":null,"phonetic":"","queue":null,"sen":"","sense":"吸收;使专心","szh":"","voicePath":""},{"english":"abstract","id":8,"pass":null,"phonetic":"","queue":null,"sen":"","sense":"摘要","szh":"","voicePath":""},{"english":"abundant","id":9,"pass":null,"phonetic":"","queue":null,"sen":"","sense":"丰富的;大量的","szh":"","voicePath":""},{"english":"academic","id":10,"pass":null,"phonetic":"","queue":null,"sen":"","sense":"学院的;学术的","szh":"","voicePath":""},{"english":"accelerate","id":11,"pass":null,"phonetic":"","queue":null,"sen":"","sense":"(使)加快;促进","szh":"","voicePath":""},{"english":"access","id":12,"pass":null,"phonetic":"","queue":null,"sen":"","sense":"接近;通道，入口","szh":"","voicePath":""},{"english":"accidental","id":13,"pass":null,"phonetic":"","queue":null,"sen":"","sense":"偶然的;非本质的","szh":"","voicePath":""},{"english":"accommodate","id":14,"pass":null,"phonetic":"","queue":null,"sen":"","sense":"容纳;供应，供给","szh":"","voicePath":""},{"english":"accommodation","id":15,"pass":null,"phonetic":"","queue":null,"sen":"","sense":"招待设备;预定铺位","szh":"","voicePath":""},{"english":"accompany","id":16,"pass":null,"phonetic":"","queue":null,"sen":"","sense":"陪伴，陪同;伴随","szh":"","voicePath":""},{"english":"accomplish","id":17,"pass":null,"phonetic":"","queue":null,"sen":"","sense":"达到(目的);完成","szh":"","voicePath":""},{"english":"accordance","id":18,"pass":null,"phonetic":"","queue":null,"sen":"","sense":"一致;和谐;授予","szh":"","voicePath":""},{"english":"accordingly","id":19,"pass":null,"phonetic":"","queue":null,"sen":"","sense":"因此，所以;照着","szh":"","voicePath":""},{"english":"account","id":20,"pass":null,"phonetic":"","queue":null,"sen":"","sense":"记述;解释;帐目","szh":"","voicePath":""},{"english":"accuracy","id":21,"pass":null,"phonetic":"","queue":null,"sen":"","sense":"准确(性);准确度","szh":"","voicePath":""},{"english":"accurate","id":22,"pass":null,"phonetic":"","queue":null,"sen":"","sense":"准确的，正确无误的","szh":"","voicePath":""}]
     * message : SUCCESS
     */

    private int code;
    private String message;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * english : abandon
         * id : 3
         * pass : null
         * phonetic :
         * queue : null
         * sen :
         * sense : 丢弃;放弃，抛弃
         * szh :
         * voicePath :
         */

        private String english;
        private int id;
        private Object pass;
        private String phonetic;
        private Object queue;
        private String sen;
        private String sense;
        private String szh;
        private String voicePath;

        public String getEnglish() {
            return english;
        }

        public void setEnglish(String english) {
            this.english = english;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getPass() {
            return pass;
        }

        public void setPass(Object pass) {
            this.pass = pass;
        }

        public String getPhonetic() {
            return phonetic;
        }

        public void setPhonetic(String phonetic) {
            this.phonetic = phonetic;
        }

        public Object getQueue() {
            return queue;
        }

        public void setQueue(Object queue) {
            this.queue = queue;
        }

        public String getSen() {
            return sen;
        }

        public void setSen(String sen) {
            this.sen = sen;
        }

        public String getSense() {
            return sense;
        }

        public void setSense(String sense) {
            this.sense = sense;
        }

        public String getSzh() {
            return szh;
        }

        public void setSzh(String szh) {
            this.szh = szh;
        }

        public String getVoicePath() {
            return voicePath;
        }

        public void setVoicePath(String voicePath) {
            this.voicePath = voicePath;
        }
    }
}
