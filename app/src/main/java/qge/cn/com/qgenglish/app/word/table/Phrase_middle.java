package qge.cn.com.qgenglish.app.word.table;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import qge.cn.com.qgenglish.db.Column;
import qge.cn.com.qgenglish.db.PrimaryKey;
import qge.cn.com.qgenglish.db.Table;

/**
 * Created by fony on 2017/11/21.
 * 初中短语
 */
@SuppressWarnings("all")
@Table(tableName = "phrase_middle")
public class Phrase_middle implements Serializable {
    @PrimaryKey(autoincrement = true, column = "_id")
    @SerializedName("index")
    private int _id;
    @Column(column = "english")
    private String english;
    @Column(column = "phonetic")
    private String phonetic;
    @Column(column = "sense")
    private String sense;
    @Column(column = "sen")
    private String sen;
    @Column(column = "szh")
    private String szh;
    @Column(column = "voicePath")
    private String voicePath;
    @Column(column = "pass")
    private String pass;
    @Column(column = "queue")
    private String queue;
    @Column(column = "belong")
    private String belong;

    public void set_id(int _id) {
        this._id = _id;
    }

    public void setBelong(String belong) {
        this.belong = belong;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public void setSen(String sen) {
        this.sen = sen;
    }

    public void setSense(String sense) {
        this.sense = sense;
    }

    public void setSzh(String szh) {
        this.szh = szh;
    }

    public void setVoicePath(String voicePath) {
        this.voicePath = voicePath;
    }











}
