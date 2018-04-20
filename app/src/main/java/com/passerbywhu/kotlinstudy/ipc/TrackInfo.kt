package com.passerbywhu.kotlinstudy.ipc

import android.os.Parcel
import android.os.Parcelable

class TrackInfo(name_: String) : Parcelable {
    var id = ""

    var name = name_

    var alias = ArrayList<String>()

    var duration = 0

    var timePublished = 0L

    var countComments = 0

    var countCollected = 0

    var isCollected = false

    var vid = ""

    constructor(parcel: Parcel) : this("") {
        id = parcel.readString()
        name = parcel.readString()
        duration = parcel.readInt()
        timePublished = parcel.readLong()
        countComments = parcel.readInt()
        countCollected = parcel.readInt()
        isCollected = parcel.readByte() != 0.toByte()
        vid = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeInt(duration)
        parcel.writeLong(timePublished)
        parcel.writeInt(countComments)
        parcel.writeInt(countCollected)
        parcel.writeByte(if (isCollected) 1 else 0)
        parcel.writeString(vid)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TrackInfo> {
        override fun createFromParcel(parcel: Parcel): TrackInfo {
            return TrackInfo(parcel)
        }

        override fun newArray(size: Int): Array<TrackInfo?> {
            return arrayOfNulls(size)
        }
    }
}