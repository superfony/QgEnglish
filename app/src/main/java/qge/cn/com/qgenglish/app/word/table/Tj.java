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
@Table(tableName = "tj")
public class Tj implements Serializable {
    @PrimaryKey(autoincrement = true, column = "_id")
    @SerializedName("index")
    public int _id;
    @Column(column = "userid")  // 用户id
    public int userid;
    @Column(column = "tablename") // 表名
    public String tablename;
    @Column(column = "tabledes") // 表描述
    public String tabledes;
    @Column(column = "redudate") // 预计剩余时间
    public String redudate;
    @Column(column = "allwordcount")// 单词总数量
    public int allwordcount;
    @Column(column = "goldcoin")// 金币数量
    public int goldcoin;
    @Column(column = "resdual") // 剩余单词数量
    public int resdual;
    @Column(column = "lcount")  // 已识记单词数量
    public int lcount;
    @Column(column = "jccount")  // 已检查单词数量
    public int jccount;
    @Column(column = "jcwrong")  // 已检查 单词错误数量
    public int jcwrong;
    @Column(column = "remark")// 备注
    public String remark;


}
