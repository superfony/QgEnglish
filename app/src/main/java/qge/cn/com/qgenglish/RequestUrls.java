package qge.cn.com.qgenglish;

/**
 * Created by fony on 2017/12/22.
 */

public class RequestUrls {
    public static final String HOST = "116.196.98.96";
    // 登录请求接口
    public static String IP = "http://116.196.98.96:8888";
    // 获取获取四级单词的操作 获取四级关卡
    public static String COMMONURL = IP + "/app/menu/?pmenuId=%s";
    public static String CONTENTURL = IP + "/app/menu/%s/contents";
    public static String schoollogin = IP + "/app/school/login";
    public static String studentlogin = IP + "/app/student/login";
    public static String studentRegist = IP + "/app/student/resgit";
    public static String surplusTime = IP + "/app/school/surplusTime?id=%s";

    // 单词短语请求接口
    public static final int word_six = 2723;
    public static final int word_four = 2490;
    public static final int phrase_small = 2669;
    public static final int phrase_middle = 2441;
    public static final int phrase_high = 145;


    //  超级记单词体验
    public static final int small_ty = 2897;
    public static final int middle_ty = 2884;
    public static final int high_ty = 2923;
    public static final int tfys_ty = 2910;

    // 阅读理解的
    public static final int rootid = 2;
    public static final String middle_article = "3114"; // 关卡上一级id
    public static final String high_article = ""; //

    // 登出的操作
    public static String schoollogout = IP + "/app/school/logout";
    public static String studentlogout = IP + "/app/student/logout";

    // 版本更新的操作
    public static String UPDATE_VERSION = IP + "/versionFile";
    // http://116.196.98.96:8888/versionFile

}
