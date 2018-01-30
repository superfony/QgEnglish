package qge.cn.com.qgenglish.db;

import android.content.Context;
import android.os.Environment;

import com.baiyang.android.util.osutil.StreamUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import qge.cn.com.qgenglish.application.AppContext;

/**
 * Created by fony on 2017/8/1.
 */
@SuppressWarnings("unused")
public class DBManager extends DBHelper {
    private DBManager(Context context) {
        super(context);
    }

    private static DBManager mInstance;
    private static DBManager wordManager;

    // 自建数据库
    public static void init(Context context) {
        if (mInstance == null) {
            mInstance = new DBManager(context);
        }
        if (wordManager == null) {
            wordManager = getAssetSQLite(context);
        }
    }

    private DBManager(Context context, String name) {
        super(context, name);
    }

    public static DBManager getInstance() {
        return mInstance;
    }

    // 文件数据库
    public static DBManager getWordManager() {
        if (wordManager == null) {
            wordManager = getAssetSQLite(AppContext.getInstance());
        }
        return wordManager;
    }

    /**
     * 打开assets的数据库
     * copy数据库 返回data/data/下的数据库操作对象
     *
     * @param context context
     * @return SQLiteDatabase
     */
    private static DBManager getAssetSQLite(Context context) {
        try {
            String path = Environment.getDataDirectory().getAbsolutePath() + "/data/" + context.getPackageName() + "/databases/qg_data.db";
            if (!new File(path).exists()) {
                InputStream is = context.getAssets().open("word.db");
                inputStreamToFile(is, path);
            }
            DBManager manager = new DBManager(context, "qg_data.db");
            if (!manager.isExist("question")) {
                context.deleteDatabase("qg_data.db");
                InputStream is = context.getAssets().open("word.db");
                inputStreamToFile(is, path);
            }
            return new DBManager(context, "qg_data.db");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static void inputStreamToFile(InputStream is, String newPath) {
        FileOutputStream fs = null;
        try {
            int read;
            fs = new FileOutputStream(newPath);
            byte[] buffer = new byte[1444];
            while ((read = is.read(buffer)) != -1) {
                fs.write(buffer, 0, read);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StreamUtil.close(fs, is);
        }
    }
}
