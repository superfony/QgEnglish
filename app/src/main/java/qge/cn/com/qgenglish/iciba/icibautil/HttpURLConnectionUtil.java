package qge.cn.com.qgenglish.iciba.icibautil;


import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by fony on 2017/11/4.
 * 阿帕奇的请求方式
 */

public class HttpURLConnectionUtil {

//    public final static String iciba_key="41DFE6A573E4538D2AB79EA55479D466";
//    public final static String iCiBaURL1="http://dict-co.iciba.com/api/dictionary.php?w=";
//    public final static String iCiBaURL2="&key="+iciba_key;

    public static InputStream getInputStreamByUrl(String urlStr){
        InputStream tempInput=null;
        URL url=null;
        HttpURLConnection connection=null;
        //设置超时时间

        try{
            url=new URL(urlStr);
            connection=(HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(10000);
            tempInput=connection.getInputStream();
        }catch(Exception e){
            e.printStackTrace();
        }
        return tempInput;
    }

}
