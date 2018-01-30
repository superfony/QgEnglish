package qge.cn.com.qgenglish.app.word.table;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import qge.cn.com.qgenglish.db.Column;
import qge.cn.com.qgenglish.db.PrimaryKey;
import qge.cn.com.qgenglish.db.Table;

/**
 * Created by fony on 2017/11/21.
 * 体验用户信息
 */
@SuppressWarnings("all")
@Table(tableName = "userinfo")
public class Userinfo implements Serializable {
    @PrimaryKey(autoincrement = true, column = "_id")
    @SerializedName("index")
    public int _id;
    @Column(column = "user_mobile")
    public String user_mobile;
    @Column(column = "password")
    public String password;
    @Column(column = "username")
    public String username;
    @Column(column = "grade")
    public String grade;
    @Column(column = "school")
    public String school;
    @Column(column = "parent_mobile")
    public String parent_mobile;
    @Column(column = "province")
    public String province;
    @Column(column = "city")
    public String city;
    @Column(column = "area")
    public String area;
    @Column(column = "coin")
    public String coin;
    @Column(column = "test_result")
    public String test_result;
    @Column(column = "feedback")
    public String feedback;
    @Column(column = "last_questionID")
    public String last_questionID;

}
