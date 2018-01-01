package qge.cn.com.qgenglish.app.word.table;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import qge.cn.com.qgenglish.db.Column;
import qge.cn.com.qgenglish.db.PrimaryKey;
import qge.cn.com.qgenglish.db.Table;

/**
 * Created by fony on 2017/11/21.
 * 根据原数据库的单词表结构而建
 * 牛津版
 */
@SuppressWarnings("all")
@Table(tableName = "word_compulsory_9")
public class Word_niujin_compulsory_9 implements Serializable {
    @PrimaryKey(autoincrement = true, column = "_id")
    @SerializedName("index")
    public int _id;
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
