package qge.cn.com.qgenglish.iciba;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import qge.cn.com.qgenglish.db.Column;
import qge.cn.com.qgenglish.db.PrimaryKey;
import qge.cn.com.qgenglish.db.Table;

/**
 * 用户习惯收集字段
 * Created by haibin on 2017/5/22.
 */
@SuppressWarnings("all")
@Table(tableName = "word")
public class WordBean implements Serializable {

    @PrimaryKey(autoincrement = true, column = "id")
    @SerializedName("index")
    private int id;

    /**
     * 字段名称
     */
    @Column(column = "key")
    private String key;

    /**
     * 英音标
     */
    @Column(column = "psE", isNotNull = true)
    private String psE;

    /**
     * 英发音
     */
    @Column(column = "pronE", isNotNull = true)
    private String pronE;

    /**
     * 美音标
     */
    @Column(column = "psA", isNotNull = true)
    private String psA;

    /**
     * 美发音
     */
    @Column(column = "pronA", isNotNull = true)
    private String pronA;

    /**
     * 词义
     */
    @Column(column = "acceptation")
    private String acceptation;


    public WordBean(WorldResp worldResp) {
        this.key = worldResp.key;
         ArrayList<PosBean> posBeanArrayList=worldResp.posBeanArrayList;
         ArrayList<PsPronBean> psPronBeanArrayList=worldResp.psPronBeanArrayList;
         ArrayList<SentBean> sentBeanArrayList=worldResp.sentBeanArrayList;
         if(psPronBeanArrayList.size()==1){
             this.psA=psPronBeanArrayList.get(0).ps;
             this.pronA=psPronBeanArrayList.get(0).pron;
         }if(psPronBeanArrayList.size()==2) {
            this.psA = psPronBeanArrayList.get(0).ps;
            this.pronA = psPronBeanArrayList.get(0).pron;
            this.psE = psPronBeanArrayList.get(1).ps;
            this.pronE = psPronBeanArrayList.get(1).pron;
        }
        this.acceptation=getAcc(posBeanArrayList);
    }




    public WordBean(){};

    public WordBean(String key, String psE, String pronE, String psA,String pronA, String acceptation) {
        this.key = key;
        this.psE = psE;
        this.pronE = pronE;
        this.psA = psA;
        this.pronA = pronA;
        this.acceptation = acceptation;
    }

    private String getAcc(ArrayList<PosBean> posBeanArrayList){
        StringBuffer stringBuffer=new StringBuffer();

        for(int i=0;i<posBeanArrayList.size();i++){
            PosBean posBean=posBeanArrayList.get(i);
            stringBuffer=stringBuffer.append(posBean.pos).append(posBean.acceptation);
        }

        return stringBuffer.toString();
    }

    public String getAcceptation() {
        return acceptation;
    }

    public void setAcceptation(String acceptation) {
        this.acceptation = acceptation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPronA() {
        return pronA;
    }

    public void setPronA(String pronA) {
        this.pronA = pronA;
    }

    public String getPronE() {
        return pronE;
    }

    public void setPronE(String pronE) {
        this.pronE = pronE;
    }

    public String getPsA() {
        return psA;
    }

    public void setPsA(String psA) {
        this.psA = psA;
    }

    public String getPsE() {
        return psE;
    }

    public void setPsE(String psE) {
        this.psE = psE;
    }

    @Override
    public String toString() {
        return "[id="+id+",key="+key+",psE="+psE+",psA="+psA+",acceptation="+acceptation+"]";
    }
}
