package qge.cn.com.qgenglish.app.fourlevel;

import java.io.Serializable;

import qge.cn.com.qgenglish.db.Column;

/**
 * Created by fony on 2018/1/30.
 * <p>
 * 树菜单bean
 */

public class Menu implements Serializable {

    /**
     * {
     * "child":"true",
     * "id":2491,
     * "menuName":"第1关",
     * "menuType":"01",
     * "pmenuId":2490,
     * "sortIndex":"01"
     * },
     */
//    @Column(column = "child")
    public String child;  //是否有子菜单(true/false)
    //    @Column(column = "id")
    public String id;  // 网络菜单 ()
    //    @Column(column = "menuName")
    public String menuName;  // 名称
    //    @Column(column = "menuType")
    public String menuType;  // 网络菜单
    //    @Column(column = "pmenuId")
    public String pmenuId;  // 网络菜单
    //    @Column(column = "sortIndex")
    public String sortIndex;  // 网络菜单
}
