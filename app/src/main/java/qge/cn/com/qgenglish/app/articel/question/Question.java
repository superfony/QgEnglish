package qge.cn.com.qgenglish.app.articel.question;

import com.google.gson.annotations.SerializedName;
import com.ta.util.db.annotation.TATableName;

import qge.cn.com.qgenglish.db.Column;
import qge.cn.com.qgenglish.db.PrimaryKey;
import qge.cn.com.qgenglish.db.Table;

/**
 * Created by fony on 2017/11/22.
 */
@Table(tableName ="question")
public class Question {
    @PrimaryKey(autoincrement = true, column = "questionID")
    @SerializedName("index")
    public int questionID;
    @Column(column = "questionBody")
    public String questionBody;
    @Column(column = "questionItems")
    public String questionItems;
    @Column(column = "answer")
    public String answer;
    @Column(column = "userAnswer")
    public String userAnswer;
    @Column(column = "parentID")
    public String parentID;

}
