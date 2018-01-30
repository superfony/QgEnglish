package qge.cn.com.qgenglish.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author fony
 */
public interface IActivitySupport {

    /**
     * @author fony
     * @update 2018-1-6 上午9:05:51
     * 终止服务.
     */
    public abstract void stopIService();

    /**
     * 开启服务.
     */
    public abstract void startIService();

    /**
     * 校验网络-如果没有网络就弹出设置,并返回true.
     */
    public abstract boolean validateInternet();

    /**
     * 校验网络-如果没有网络就返回true.
     */
    public abstract boolean hasInternetConnected();


}
