package qge.cn.com.qgenglish.iciba;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 词义
 */

public class PsPronBean implements Parcelable {
	public String ps;
	public String pron;



	public static final Creator<PsPronBean> CREATOR = new Creator<PsPronBean>() {
		@SuppressWarnings("unchecked")
		public PsPronBean createFromParcel(Parcel source) {
			PsPronBean field = new PsPronBean();
			field.ps = source.readString();
			field.pron = source.readString();

			return field;
		}

		public PsPronBean[] newArray(int size) {
			return new PsPronBean[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags){
		parcel.writeString(ps);
		parcel.writeString(pron);

	}

	@Override
	public String toString() {
		return "";
	}
}
