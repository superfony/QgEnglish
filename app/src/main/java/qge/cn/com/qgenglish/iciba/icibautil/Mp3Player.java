package qge.cn.com.qgenglish.iciba.icibautil;


import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.io.InputStream;

import qge.cn.com.qgenglish.R;
import qge.cn.com.qgenglish.db.DBManager;

/**
 * Created by fony on 2017/11/4.
 */

public class Mp3Player {
    public final static String MUSIC_ENG_RELATIVE_PATH = "afony/sounds/sounds_EN/";
    public final static String MUSIC_USA_RELATIVE_PATH = "afony/sounds/sounds_US/";
    public final static int ENGLISH_ACCENT = 0;
    public final static int USA_ACCENT = 1;
    public Context context = null;
    public MediaPlayer mediaPlayer;
    public MediaPlayer mp;
    FileUtils fileU = null;

    public boolean isMusicPermitted = true;     //用于对是否播放音乐进行保护性设置，当该变量为false时，可以阻止一次音乐播放

    public Mp3Player(Context context) {
        this.context = context;
        fileU = new FileUtils();
        isMusicPermitted = true;

    }

    /**
     * 首先先看一下SD卡上有没有，若有则播放，没有执行下一步
     * 看一下dict表中有没有单词的记录，若有，看一下发音字段是不是有美式发音或英式发音，若无则退出
     * 若没有字段记录，访问网络下载Mp3然后播放
     * 一个Activity中一般只能有一个Voice成员变量，对应的也就只有一个MediaPlayer对象，这样才能对播放
     * 状态进行有效控制
     * 该方法原则上只能在线程中调用
     */
    public void playMusicByWord(String word, int accent) {

        String path = null;
        String pronUrl = null;

        if (TextUtils.isEmpty(word))
            return;
        char[] wordArray = word.toCharArray();
        char initialCharacter = wordArray[0];


        if (accent == ENGLISH_ACCENT) {
            path = MUSIC_ENG_RELATIVE_PATH;
        } else {
            path = MUSIC_USA_RELATIVE_PATH;
        }

        if (fileU.isFileExist(path + initialCharacter + "/", "-$-" + word + ".mp3") == false) {

            //为了避免多次多个线程同时访问网络下载同一个文件，这里加了这么一个控制变量
            if (accent == ENGLISH_ACCENT) {
                pronUrl = DBManager.getInstance().getCursorValue(word, "pronE");
            } else if (accent == USA_ACCENT) {
                pronUrl = DBManager.getInstance().getCursorValue(word, "pronA");
            }
            if (TextUtils.isEmpty(pronUrl))
                return;
            //得到了Mp3地址后下载到文件夹中然后进行播放
            InputStream in = null;
            in = HttpURLConnectionUtil.getInputStreamByUrl(pronUrl);
            if (in == null)
                return;
            if (fileU.saveInputStreamToFile(in, path + initialCharacter + "/", "-$-" + word + ".mp3") == false)
                return;
        }
        //走到这里说明文件夹里一定有响应的音乐文件，故在这里播放
        /**
         * 这个方法存在缺点，可能因为同时new 了多个MediaPlayer对象，导致start方法失效，
         * 因此解决的方法是，使用同一个MediaPlayer对象，若一次播放时发现对象非空，那么先
         * 调用release()方法释放资源，再重新create
         */
        if (isMusicPermitted == false) {
            return;
        }
        try {
            Uri uri = Uri.parse("file://" + fileU.getSDRootPath()
                    + path + initialCharacter + "/-$-" + word + ".mp3");

            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(context, uri);
            } else {
                mediaPlayer.setDataSource(context, uri);
            }

            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
            mediaPlayer.start();
        } catch (Exception e) {
            mediaPlayer.reset();
            mediaPlayer.release();
            e.printStackTrace();
        }

    }


    public void soundMp3(int r) {
        try {

            mp = MediaPlayer.create(context, r);
            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
            mp.start();

        } catch (Exception e) {
            if (mp != null)
                mp.release();
            e.printStackTrace();
        }
    }

    public void stopSoundMp3() {

        if (mp != null) {
            if (mp.isPlaying()) {
                mp.stop();
            }
            mp.reset();
            mp.release();
        }
    }
}
