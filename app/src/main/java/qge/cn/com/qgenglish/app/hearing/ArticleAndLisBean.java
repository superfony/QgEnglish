package qge.cn.com.qgenglish.app.hearing;

import java.io.Serializable;

/**
 * Created by fony on 2018/3/6.
 */

public class ArticleAndLisBean implements Serializable {


    /**
     * listeningInfo : {"displayName":"2006年高考英语听力（全国卷）.mp3","fileName":"a2b6bc2a-4821-4b87-b4cc-accb3bb15954.mp3","length":20422212,"url":"/download/40293/type/mp3"}
     * paperUrl : /download/40293/type/doc
     * paperName : 2006年高考英语听力（全国卷）
     */

    private ListeningInfoBean listeningInfo;
    private String paperUrl;
    private String paperName;
    private String paperContent;

    public String getPaperContent() {
        return paperContent;
    }

    public void setPaperContent(String paperContent) {
        this.paperContent = paperContent;
    }


    public ListeningInfoBean getListeningInfo() {
        return listeningInfo;
    }

    public void setListeningInfo(ListeningInfoBean listeningInfo) {
        this.listeningInfo = listeningInfo;
    }

    public String getPaperUrl() {
        return paperUrl;
    }

    public void setPaperUrl(String paperUrl) {
        this.paperUrl = paperUrl;
    }

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public static class ListeningInfoBean implements Serializable {
        /**
         * displayName : 2006年高考英语听力（全国卷）.mp3
         * fileName : a2b6bc2a-4821-4b87-b4cc-accb3bb15954.mp3
         * length : 20422212
         * url : /download/40293/type/mp3
         */

        private String displayName;
        private String fileName;
        private int length;
        private String url;

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
