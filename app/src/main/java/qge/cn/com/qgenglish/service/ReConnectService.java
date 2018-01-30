package qge.cn.com.qgenglish.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * @author fony
 */
public class ReConnectService extends Service {
    private Context context;
    private ConnectivityManager connectivityManager;
    private NetworkInfo info;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(reConnectionBroadcastReceiver, mFilter);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(reConnectionBroadcastReceiver);
        super.onDestroy();
    }

    BroadcastReceiver reConnectionBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                Log.w("mark", "网络状态已经改变");
                connectivityManager = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                info = connectivityManager.getActiveNetworkInfo();
                if (info != null && info.isAvailable()) {

                } else {
                    Toast.makeText(context, "网络已断开!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        }
    };
}
