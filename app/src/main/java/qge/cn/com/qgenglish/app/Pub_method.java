package qge.cn.com.qgenglish.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.provider.Settings.Secure;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Pub_method {

    /**
     * 得到设备id
     *
     * @param context
     * @return 得到设备id
     */
    public static String getDeviceID(Context context) {


        return Secure.getString(context.getContentResolver(),
                Secure.ANDROID_ID);

    }


    /**
     * MD5加密方法
     *
     * @param plainText
     * @return
     */
    @SuppressLint("DefaultLocale")
    public static String Md5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString().toUpperCase();
            // return plainText;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取设备可使用的系统
     *
     * @param DeviceID 设备id
     */
    public static void get_Device_Use_system(Context context,
                                             final String DeviceID, final Handler handler) {
    }

    /**
     * @return 得到系统时间
     */
    @SuppressLint("SimpleDateFormat")
    public static String get_system_Time() {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        return formatter.format(curDate);
    }

}
