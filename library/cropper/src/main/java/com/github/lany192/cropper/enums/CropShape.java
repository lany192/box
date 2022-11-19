package com.github.lany192.cropper.enums;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * The possible cropping area shape.<br>
 * To set square/circle crop shape set aspect ratio to 1:1.
 */
public enum CropShape implements Parcelable {

    RECTANGLE(0), OVAL(1);

    private int value;

    CropShape(int value) {
        this.value = value;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(value);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CropShape> CREATOR = new Creator<CropShape>() {
        @Override
        public CropShape createFromParcel(Parcel in) {
            return CropShape.values()[in.readInt()];
        }

        @Override
        public CropShape[] newArray(int size) {
            return new CropShape[size];
        }
    };
}