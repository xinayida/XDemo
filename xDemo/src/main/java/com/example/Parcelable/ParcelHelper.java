package com.example.Parcelable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Stefan on 2019-07-28.
 */
public class ParcelHelper {

    public static <T> T copy(Parcelable input) {
        Parcel parcel = null;

        try {
            parcel = Parcel.obtain();
            parcel.writeParcelable(input, 0);

            parcel.setDataPosition(0);
            return parcel.readParcelable(input.getClass().getClassLoader());
        } finally {
            parcel.recycle();
        }
    }
}