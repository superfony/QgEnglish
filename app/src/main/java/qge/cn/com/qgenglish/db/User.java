package qge.cn.com.qgenglish.db;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 用户习惯收集字段
 * Created by haibin on 2017/5/22.
 */
@SuppressWarnings("all")
@Table(tableName = "user")
public class User implements Serializable {

    @PrimaryKey(autoincrement = true, column = "id")
    @SerializedName("index")
    private int id;

    /**
     * 用户id
     */
    @Column(column = "userid")
    private String userid;

    /**
     * 用户名称
     */
    @Column(column = "username", isNotNull = true)
    private String userName;

    /**
     * 用户密码
     */
    @Column(column = "password", isNotNull = true)
    private String password;

    /**
     * 操作时间
     */
    @SerializedName("operate_time")
    @Column(column = "operate_time", isNotNull = true)
    private long operateTime;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(long operateTime) {
        this.operateTime = operateTime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "[id=" + id + ",userid=" + userid + ",userName=" + userName + ",password=" + password + ",operateTime=" + operateTime + "]";
    }
}
