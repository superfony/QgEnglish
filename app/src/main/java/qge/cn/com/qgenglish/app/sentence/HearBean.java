package qge.cn.com.qgenglish.app.sentence;

import java.io.Serializable;

/**
 * Created by fony on 2018/3/13.
 */

public class HearBean implements Serializable {


    /**
     * fileName : 18647dfd-9280-4fdf-88e3-bb12f1ed9248.mp3
     * displayName : 01-2017年普通高等学校招生全国统一考试英语听力(浙江2016年10月).mp3
     * length : 22712320
     * url : /download/40295/type/03
     */

    private String fileName;
    private String displayName;
    private int length;
    private String url;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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
