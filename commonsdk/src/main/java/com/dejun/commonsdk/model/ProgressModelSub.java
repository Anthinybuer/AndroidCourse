package com.dejun.commonsdk.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author:DoctorWei
 * Time:2018/12/22 14:39
 * Description:
 * email:1348172474@qq.com
 */

public class ProgressModelSub implements Parcelable{
    int progress;

    public ProgressModelSub(int progress) {
        this.progress = progress;
    }

    protected ProgressModelSub(Parcel in) {
        progress = in.readInt();
    }

    public static final Creator<ProgressModelSub> CREATOR = new Creator<ProgressModelSub>() {
        @Override
        public ProgressModelSub createFromParcel(Parcel in) {
            return new ProgressModelSub(in);
        }

        @Override
        public ProgressModelSub[] newArray(int size) {
            return new ProgressModelSub[size];
        }
    };

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(progress);
    }
}
