package qge.cn.com.qgenglish.app.word.table;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import qge.cn.com.qgenglish.db.Column;
import qge.cn.com.qgenglish.db.PrimaryKey;
import qge.cn.com.qgenglish.db.Table;

/**
 * Created by fony on 2017/11/21.
 * 人教版 八年级
 */
@SuppressWarnings("all")
@Table(tableName = "word_renjiao_8_2")
public class Word_renjiao_8_2 implements Serializable {
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
}
