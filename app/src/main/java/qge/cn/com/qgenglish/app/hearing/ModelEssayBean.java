package qge.cn.com.qgenglish.app.hearing;

import java.io.Serializable;

/**
 * Created by fony on 2018/3/13.
 * 范文
 */

public class ModelEssayBean implements Serializable {


    /**
     * name : 范文10篇
     * content :
     * url : /download/40285/type/05
     */

    private String name;
    private String content;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
