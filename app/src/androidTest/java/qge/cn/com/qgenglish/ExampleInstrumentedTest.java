package qge.cn.com.qgenglish;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

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


        String jsonuser="{\"requestcode\": \" 0\",\"message\": \"成功失败信息\"}";



        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(jsonuser);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String request= jsonObj.getJSONObject("qgroot").getString("requestcode");
        // gson = new Gson();
        //TestUser testUser=gson.fromJson(jsonuser,TestUser.class);
        System.out.print(request);
    }
}
