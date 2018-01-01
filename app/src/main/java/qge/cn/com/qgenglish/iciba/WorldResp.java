package qge.cn.com.qgenglish.iciba;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class WorldResp implements Parcelable {
    public String requestcode = "1";
    public String message;
    public String key;
    public ArrayList<PosBean> posBeanArrayList;
    public ArrayList<PsPronBean> psPronBeanArrayList;
    public ArrayList<SentBean> sentBeanArrayList;


    public static final Creator<WorldResp> CREATOR = new Creator<WorldResp>() {
        @SuppressWarnings("unchecked")
        public WorldResp createFromParcel(Parcel source) {
            WorldResp field = new WorldResp();
            field.requestcode = source.readString();
            field.message = source.readString();
            field.key = source.readString();
            field.posBeanArrayList = source.readArrayList(PosBean.class.getClassLoader());
            field.psPronBeanArrayList = source.readArrayList(PsPronBean.class.getClassLoader());
            field.sentBeanArrayList = source.readArrayList(SentBean.class.getClassLoader());

            return field;
        }

        public WorldResp[] newArray(int size) {
            return new WorldResp[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(requestcode);
        parcel.writeString(message);
        parcel.writeString(key);
        parcel.writeList(posBeanArrayList);
        parcel.writeList(psPronBeanArrayList);
        parcel.writeList(sentBeanArrayList);


    }

    @Override
    public String toString() {
        return "";
    }
}
