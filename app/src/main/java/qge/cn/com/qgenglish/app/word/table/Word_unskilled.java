package qge.cn.com.qgenglish.app.word.table;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import qge.cn.com.qgenglish.db.Column;
import qge.cn.com.qgenglish.db.PrimaryKey;
import qge.cn.com.qgenglish.db.Table;

/**
 * Created by fony on 2017/12/27.
 * 生词本
 */
@SuppressWarnings("all")
@Table(tableName = "word_unskilled")
public class Word_unskilled implements Serializable {
    @PrimaryKey(autoincrement = true, column = "_id")
    @SerializedName("index")
    private int _id;
    @Column(column = "user_id")
    public String user_id;
    @Column(column = "english")
    public String english;
    @Column(column = "phonetic")
    public String phonetic;
    @Column(column = "sense")
    public String sense;
    @Column(column = "sen")
    public String sen;
    @Column(column = "szh")
    public String szh;
    @Column(column = "voicePath")
    public String voicePath;
    @Column(column = "pass")
    public String pass;
    @Column(column = "queue")
    public String queue;
    @Column(column = "belong")
    public String belong;
}
