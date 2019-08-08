package com.example.Parcelable

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Stefan on 2019-07-28.
 */
class DemoEntity : Parcelable {
    var tips: String? = null

    var warn: String? = null

    var url: String? = null

    constructor()
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString()
    )

    constructor(tips: String, warn: String, url: String) {
        this.tips = tips
        this.warn = warn
        this.url = url
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(tips)
        writeString(warn)
        writeString(url)
    }

    override fun toString(): String {
        return "DemoEntity(tips=$tips, warn=$warn, url=$url)"
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<DemoEntity> = object : Parcelable.Creator<DemoEntity> {
            override fun createFromParcel(source: Parcel): DemoEntity = DemoEntity(source)
            override fun newArray(size: Int): Array<DemoEntity?> = arrayOfNulls(size)
        }
    }


}