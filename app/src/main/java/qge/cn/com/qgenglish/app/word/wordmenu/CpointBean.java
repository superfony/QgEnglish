package qge.cn.com.qgenglish.app.word.wordmenu;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import qge.cn.com.qgenglish.db.Column;
import qge.cn.com.qgenglish.db.PrimaryKey;
import qge.cn.com.qgenglish.db.Table;

/**
 * Created by fony on 2017/11/23.
 * 关卡bean
 */
@SuppressWarnings("all")
@Table(tableName = "cpointBean")
public class CpointBean implements Serializable {
    // 菜单id
    @PrimaryKey(autoincrement = true, column = "_id")
    @SerializedName("index")
    public int _id;
    @Column(column = "name")
    public String name; //菜单名称
    @Column(column = "isPromiss")
    public int isPromiss;//权限
    @Column(column = "ischecked")
    public int ischecked; //是否被选中 复习、检查用
    @Column(column = "code")
    public int code;//当前关卡编号
    @Column(column = "tablename")
    public String tablename; //当前关卡所属的单词表
    @Column(column = "type")
    public int type; //当前关卡的类型  1 识记,2 复习 ,3 检查
    @Column(column = "state")
    public int state; //当前关卡学习状态  0 未通关,1 通关


}
