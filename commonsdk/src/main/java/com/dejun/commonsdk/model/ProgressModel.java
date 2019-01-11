package com.dejun.commonsdk.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author:DoctorWei
 * Time:2018/12/22 14:39
 * Description:
 * email:1348172474@qq.com
 */

public class ProgressModel implements Parcelable{
    int progress;
    ProgressModelSub progressModelSub;
    public ProgressModel(int progress) {
        this.progress = progress;
    }

    protected ProgressModel(Parcel in) {
        progress = in.readInt();
        progressModelSub = in.readParcelable(ProgressModelSub.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(progress);
        dest.writeParcelable(progressModelSub, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProgressModel> CREATOR = new Creator<ProgressModel>() {
        @Override
        public ProgressModel createFromParcel(Parcel in) {
            return new ProgressModel(in);
        }

        @Override
        public ProgressModel[] newArray(int size) {
            return new ProgressModel[size];
        }
    };

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
