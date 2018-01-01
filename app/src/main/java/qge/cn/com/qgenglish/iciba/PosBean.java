package qge.cn.com.qgenglish.iciba;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 发音
 */

public class PosBean implements Parcelable {
	public String pos;
	public String acceptation;



	public static final Creator<PosBean> CREATOR = new Creator<PosBean>() {
		@SuppressWarnings("unchecked")
		public PosBean createFromParcel(Parcel source) {
			PosBean field = new PosBean();
			field.pos = source.readString();
			field.acceptation = source.readString();

			return field;
		}

		public PosBean[] newArray(int size) {
			return new PosBean[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags){
		parcel.writeString(pos);
		parcel.writeString(acceptation);

	}

	@Override
	public String toString() {
		return "";
	}
}
