package qge.cn.com.qgenglish.app.articel.question;
import com.google.gson.annotations.SerializedName;
import qge.cn.com.qgenglish.db.Column;
import qge.cn.com.qgenglish.db.PrimaryKey;
import qge.cn.com.qgenglish.db.Table;
/**
 * Created by fony on 2017/11/22.
 */
@Table(tableName ="question_text")
public class QuestionText {
    @PrimaryKey(autoincrement = true, column = "textID")
    @SerializedName("index")
    public int textID;
    @Column(column = "questionBody")
    public String questionBody;
    @Column(column = "questionIDs")
    public String questionIDs;
}
