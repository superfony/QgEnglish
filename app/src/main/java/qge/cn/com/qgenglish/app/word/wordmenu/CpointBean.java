package qge.cn.com.qgenglish.app.word.wordmenu;

import java.io.Serializable;

/**
 * Created by fony on 2017/11/23.
 * 关卡bean
 */

public class CpointBean implements Serializable {
    // 菜单id
    public String id;
    // 菜单名称
    public String name;
    // 权限
    public boolean isPromiss;
    // 是否被选中
    public boolean isChecked;

    public String tableName;
}
