package qge.cn.com.qgenglish.app.articel.question;

import com.google.gson.annotations.SerializedName;

import qge.cn.com.qgenglish.db.Column;
import qge.cn.com.qgenglish.db.PrimaryKey;
import qge.cn.com.qgenglish.db.Table;

/**
 * Created by fony on 2017/11/22.
 */
@Table(tableName = "question_item")
public class QuestionItem {
    @PrimaryKey(autoincrement = true, column = "itemID")
    @SerializedName("index")
    public int itemID;
    @Column(column = "itemValue")
    public String itemValue;
    @Column(column = "itemContent")
    public String itemContent;
    @Column(column = "questionID")
    public String questionID;
}
