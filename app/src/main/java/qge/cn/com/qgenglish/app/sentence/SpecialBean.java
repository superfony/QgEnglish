package qge.cn.com.qgenglish.app.sentence;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fony on 2018/3/14.
 */

public class SpecialBean implements Serializable {


    /**
     * answer : C
     * content : The question ______by us soon.
     * items : ["is going to discuss","will discuss","is going to be discussed ","has been discussed"]
     * taskName : 选择填空
     */

    private String answer;
    private String content;
    private String taskName;
    private int isornot; //1正确 2错误
    private List<String> items;
    private String myanswer;

    public String getMyanswer() {
        return myanswer;
    }

    public void setMyanswer(String myanswer) {
        this.myanswer = myanswer;
    }

    public int getIsornot() {
        return isornot;
    }

    public void setIsornot(int isornot) {
        this.isornot = isornot;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }
}
