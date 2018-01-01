package qge.cn.com.qgenglish.iciba;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 词句
 */

public class SentBean implements Parcelable {
    public String orig;
    public String trans;


    public static final Creator<SentBean> CREATOR = new Creator<SentBean>() {
        @SuppressWarnings("unchecked")
        public SentBean createFromParcel(Parcel source) {
            SentBean field = new SentBean();
            field.orig = source.readString();
            field.trans = source.readString();

            return field;
        }

        public SentBean[] newArray(int size) {
            return new SentBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(orig);
        parcel.writeString(trans);

    }

    @Override
    public String toString() {
        return "";
    }
}
