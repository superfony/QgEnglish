package qge.cn.com.qgenglish;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import qge.cn.com.qgenglish.app.word.table.Phrase_high;
import qge.cn.com.qgenglish.app.word.table.Phrase_middle;
import qge.cn.com.qgenglish.app.word.table.Phrase_small;
import qge.cn.com.qgenglish.db.DBManager;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("qge.cn.com.qgenglish", appContext.getPackageName());
        String jsonuser = "{\"requestcode\": \" 0\",\"message\": \"成功失败信息\"}";

        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(jsonuser);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String request = jsonObj.getJSONObject("qgroot").getString("requestcode");
        // gson = new Gson();
        //TestUser testUser=gson.fromJson(jsonuser,TestUser.class);
        System.out.print(request);
    }

    @Test
    public void insertDy() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        // 小学短语
//      DBManager.getInstance().clear("phrase_small");

//        getFileToStr("phrasesmall.txt");
//     List<Phrase_small> list= DBManager.getInstance().get(Phrase_small.class);
//        System.out.print(list.size());
//        // 初中短语
//        getFileToStr("phrasemiddle.txt");
//        List<Phrase_middle> list1= DBManager.getInstance().get(Phrase_middle.class);
//        System.out.print(list1.size());
//        // 高中短语
//        getFileToStr("phrasehigh.txt");
//        List<Phrase_high> list2= DBManager.getInstance().get(Phrase_high.class);
//        System.out.print(list2.size());


        DBManager.getWordManager().clear("phrase_small");
        DBManager.getWordManager().clear("phrase_middle");
        DBManager.getWordManager().clear("phrase_high");

        DBManager.getWordManager().create(Phrase_small.class, DBManager.getWordManager().getReadableDatabase());
        DBManager.getWordManager().create(Phrase_middle.class, DBManager.getWordManager().getReadableDatabase());
        DBManager.getWordManager().create(Phrase_high.class, DBManager.getWordManager().getReadableDatabase());

        getFileToStr("phrasesmall.txt", DBManager.getWordManager());
        getFileToStr1("phrasemiddle.txt", DBManager.getWordManager());
        getFileToStr2("phrasehigh.txt", DBManager.getWordManager());

        List<Phrase_small> list2 = DBManager.getWordManager().get(Phrase_small.class);
        System.out.print(list2.size());

    }

    public String getFileToStr(String fileName, DBManager dbManager) {
        StringBuffer temp = new StringBuffer();
        try {
            InputStream in = this.getClass().getClassLoader()
                    .getResourceAsStream(fileName);// 获取src 目录底下的文件
            BufferedReader rd = new BufferedReader(new InputStreamReader(in,
                    "UTF-8"));
            String tempLine = rd.readLine();
            while (tempLine != null) {
                Phrase_small phrase_small = new Phrase_small();
                phrase_small.setEnglish(tempLine);
                tempLine = rd.readLine();
                phrase_small.setSense(tempLine);
                tempLine = rd.readLine();
                dbManager.insert(phrase_small);
            }
        } catch (Exception e) {
//            responseHandler.sendFailureMessage(e, "测试数据文件路径不正确，请将文件放到项目的src根目录下！");
        }
        String result = temp.toString();
        return result;
    }

    public String getFileToStr1(String fileName, DBManager dbManager) {
        StringBuffer temp = new StringBuffer();
        try {
            InputStream in = this.getClass().getClassLoader()
                    .getResourceAsStream(fileName);// 获取src 目录底下的文件
            BufferedReader rd = new BufferedReader(new InputStreamReader(in,
                    "UTF-8"));
            String tempLine = rd.readLine();
            while (tempLine != null) {
                Phrase_middle phrase_small = new Phrase_middle();
                phrase_small.setEnglish(tempLine);
                tempLine = rd.readLine();
                phrase_small.setSense(tempLine);
                tempLine = rd.readLine();
                dbManager.insert(phrase_small);
            }
        } catch (Exception e) {
//            responseHandler.sendFailureMessage(e, "测试数据文件路径不正确，请将文件放到项目的src根目录下！");
        }
        String result = temp.toString();
        return result;
    }

    public String getFileToStr2(String fileName, DBManager dbManager) {
        StringBuffer temp = new StringBuffer();
        try {
            InputStream in = this.getClass().getClassLoader()
                    .getResourceAsStream(fileName);// 获取src 目录底下的文件
            BufferedReader rd = new BufferedReader(new InputStreamReader(in,
                    "UTF-8"));
            String tempLine = rd.readLine();
            while (tempLine != null) {
                Phrase_high phrase_small = new Phrase_high();
                phrase_small.setEnglish(tempLine);
                tempLine = rd.readLine();
                phrase_small.setSense(tempLine);
                tempLine = rd.readLine();
                dbManager.insert(phrase_small);
            }
        } catch (Exception e) {
//            responseHandler.sendFailureMessage(e, "测试数据文件路径不正确，请将文件放到项目的src根目录下！");
        }
        String result = temp.toString();
        return result;
    }
}
