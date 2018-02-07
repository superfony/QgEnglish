package qge.cn.com.qgenglish;

/**
 * Created by fony on 2017/12/22.
 */

public class RequestUrls {
    // 登录请求接口
    public static String IP = "http://116.196.98.96:9999";
    public static String login = IP + "/app/school/login";
    // 注册接口
    public static String regist = IP + "/app/student/resgit";
    //  计时定时任务
    public static String sendTime = IP + "/app/student/timmer";
    // 获取获取四级单词的操作 获取四级关卡
    public static String COMMONURL = "http://116.196.98.96:9999/app/menu/?pmenuId=%s";
    public static String CONTENTURL = "http://116.196.98.96:9999/app/menu/%s/contents";


    public static String IP2 = "http://116.196.98.96:8888";
    public static String schoollogin = IP2 + "/app/school/login";
    public static String studentlogin = IP2 + "/app/student/login";
    public static String studentRegist = IP2 + "/app/student/resgit";
    public static String surplusTime = IP2 + "/app/school/surplusTime?id=%s";


    // 单词短语请求接口
    public static final int word_six = 2723;
    public static final int word_four = 2490;
    public static final int phrase_small = 2669;
    public static final int phrase_middle = 2441;
    public static final int phrase_high = 145;


}
