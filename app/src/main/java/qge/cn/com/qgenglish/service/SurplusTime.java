package qge.cn.com.qgenglish.service;

import java.io.Serializable;

/**
 * Created by fony on 2018/2/6.
 */

public class SurplusTime implements Serializable {


    /**
     * id : 1
     * timeTotal : 1000
     * timeUsed : 1
     * userId : 1
     */

    private int id;
    private int timeTotal;
    private int timeUsed;
    private int userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTimeTotal() {
        return timeTotal;
    }

    public void setTimeTotal(int timeTotal) {
        this.timeTotal = timeTotal;
    }

    public int getTimeUsed() {
        return timeUsed;
    }

    public void setTimeUsed(int timeUsed) {
        this.timeUsed = timeUsed;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
