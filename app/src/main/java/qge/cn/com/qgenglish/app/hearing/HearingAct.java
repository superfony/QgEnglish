package qge.cn.com.qgenglish.app.hearing;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;
import qge.cn.com.qgenglish.R;
import qge.cn.com.qgenglish.RequestUrls;
import qge.cn.com.qgenglish.app.BaseActivity;
import qge.cn.com.qgenglish.app.fourlevel.Menu;

/**
 * Created by fony on 2018/3/1.
 * 听力训练语音播放的操作
 */

public class HearingAct extends BaseActivity implements View.OnClickListener,
        SeekBar.OnSeekBarChangeListener {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.start)
    Button start;
    @Bind(R.id.end)
    Button end;
    @Bind(R.id.pause)
    Button pause;
    @Bind(R.id.seekbar)
    SeekBar seekbar;
    @Bind(R.id.time_start)
    TextView timeStart;
    @Bind(R.id.time_finish)
    TextView timeFinish;

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hearing_act);
        ButterKnife.bind(this);
        activity = this;
        reqData();
        initListener();
        //initMediaplayer();
    }

    //
    private void reqData() {
        Intent intent = activity.getIntent();
        menu = (Menu) intent.getSerializableExtra("menu");
        title.setText(menu.menuName);
        String url = String.format(RequestUrls.COMMONURL, menu.id).toString();
        startHttpGet(url, null);
    }

    private void initListener() {
        start.setOnClickListener(this);
        pause.setOnClickListener(this);
        end.setOnClickListener(this);

    }


    @Override
    protected void onFailureBase(Throwable throwable, String s) {
        super.onFailureBase(throwable, s);
    }

    @Override
    protected void onSuccessBase(String s) {
        super.onSuccessBase(s);
        // 获取mp3 url
        Gson gson = new Gson();
//         Result result = gson.fromJson(s, new TypeToken<Result<ArrayList<Word_niujinban_7_1>>>() {
//         Word_niujinban_7_1}.getType());
//         ArrayList arrayList = (ArrayList) result.getData();
//        String url = "";
        richHandler.obtainMessage(1, url).sendToTarget();
    }

    Handler richHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    // web显示数据
                    break;
                case 1:
                    url = msg.obj.toString();
                    initMediaplayer(url);
                    break;
                default:
                    break;
            }
        }
    };

    private boolean hadDestroy = false;
    private MediaPlayer player;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (!hadDestroy) {
                mHandler.postDelayed(this, 1000);
                int currentTime = Math
                        .round(player.getCurrentPosition() / 1000);
                String currentStr = String.format("%s%02d:%02d", " ",
                        currentTime / 60, currentTime % 60);
                timeStart.setText(currentStr);
                seekbar.setProgress(player.getCurrentPosition());
            }
        }
    };

    private void initMediaplayer(String url) {
        try {
//            showPD();
            player = new MediaPlayer();
            player.setDataSource(url);
            player.prepare();//
//            player.prepareAsync();
//            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(MediaPlayer mp) {
//                    mHandler.sendEmptyMessage(0);
//
//
//                }
//            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {

            switch (msg.what) {

                case 0:
                    hidePD();
                default:
                    break;
            }
        }

        ;
    };

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.start:

                if (!player.isPlaying()) {
                    player.start();
                    int totalTime = Math.round(player.getDuration() / 1000);
                    String str = String.format("%02d:%02d", totalTime / 60,
                            totalTime % 60);
                    timeFinish.setText(str);
                    seekbar.setMax(player.getDuration());
                    mHandler.postDelayed(runnable, 1000);
                }

                break;
            case R.id.pause:
                if (player.isPlaying()) {
                    player.pause();
                } else {
                    player.start();
                }
                break;
            case R.id.end:
                if (player.isPlaying()) {
                    player.reset();
                    initMediaplayer(url);
                }
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (player != null) {
            player.seekTo(seekBar.getProgress());
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.stop();
            hadDestroy = true;
            player.release();
        }

    }
}
