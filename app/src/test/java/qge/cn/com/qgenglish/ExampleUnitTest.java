package qge.cn.com.qgenglish;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import qge.cn.com.qgenglish.app.Result;
import qge.cn.com.qgenglish.app.bean.Wordnew;
import qge.cn.com.qgenglish.app.word.table.Word_niujinban_7_1;
import qge.cn.com.qgenglish.db.DBManager;
import qge.cn.com.qgenglish.iciba.WordBean;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);

    }

    @Test
    public void fanTest() throws JSONException {

        System.out.print("12456789");
        Student stu = new Student(123, "Tom", "male");
        Gson gson = new Gson();
        String str = gson.toJson(stu);
        System.out.print(str);

        String jsonData = "{ \"stu_no\":12345,\"stu_name\":\"John\",\"stu_sex\":\"male\" }";
        gson = new Gson();
        Student student = gson.fromJson(jsonData, Student.class);


        String jsonuser = "{\"qgroot\": {\"requestcode\": \" 0\",\"message\": \"成功失败信息\"}}";


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
    public void resultTest() {

        String result = "{\n" +
                "  \"code\": 200,\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"english\": \"abandon\",\n" +
                "      \"id\": 3,\n" +
                "      \"pass\": null,\n" +
                "      \"phonetic\": \"\",\n" +
                "      \"queue\": null,\n" +
                "      \"sen\": \"\",\n" +
                "      \"sense\": \"丢弃;放弃，抛弃\",\n" +
                "      \"szh\": \"\",\n" +
                "      \"voicePath\": \"\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"english\": \"accurate\",\n" +
                "      \"id\": 22,\n" +
                "      \"pass\": null,\n" +
                "      \"phonetic\": \"\",\n" +
                "      \"queue\": null,\n" +
                "      \"sen\": \"\",\n" +
                "      \"sense\": \"准确的，正确无误的\",\n" +
                "      \"szh\": \"\",\n" +
                "      \"voicePath\": \"\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"message\": \"SUCCESS\"\n" +
                "}";


        Gson gson = new Gson();
        Result student = gson.fromJson(result, new TypeToken<Result<ArrayList<Word_niujinban_7_1>>>() {
        }.getType());
        System.out.print(student);
        ArrayList arrayList = (ArrayList) student.getData();
        int size = arrayList.size();
        // Object object = arrayList.get(0);
    }


    @Test
    public void testDouble() {

        String dou = "3.23";
        String dou1 = "3";
        String dou2 = "0.0";
        String dou3 = "0.01";

        BigDecimal b1 = new BigDecimal(dou);
        BigDecimal b2 = new BigDecimal(dou1);
        BigDecimal b3 = new BigDecimal(dou2);
        BigDecimal b4 = new BigDecimal(dou3);
        System.out.println(b1.add(b2).toString());
        System.out.println(b3.add(b3).toString());
        System.out.println(b2.subtract(b1).toString());
        System.out.println(subZeroAndDot(b3.add(b3).toString()));
        System.out.println(b4.toString());
        System.out.println(subZeroAndDot(dou3));

        String xx = new BigDecimal(dou3).subtract(new BigDecimal(dou3)).toString();

        System.out.println(b1.add(b2).toString());
        System.out.println(subZeroAndDot(xx));
        System.out.println("-----------");
        System.out.println(b1.compareTo(b2) > 0);

    }


    public String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

    @Test
    public void testString() {

        String url = String.format(RequestUrls.COMMONURL, 2490).toString();
        System.out.println(url);
        String str = "aaaaa\nbbbbb";
        String str2 = str.replaceAll("\n", "  ");
        System.out.println(str2);
    }
}