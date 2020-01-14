package com.song.tasty.common.app.music.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * @date : 2019-08-20 11:24
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
class MusicBean1() : Parcelable {

    var id: String? = null
    var name: String? = null
    var author: String? = null
    var cover: String? = null
    var url: String? = null
    var totalTime: Long = 0

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        name = parcel.readString()
        author = parcel.readString()
        cover = parcel.readString()
        url = parcel.readString()
        totalTime = parcel.readLong()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(author)
        parcel.writeString(cover)
        parcel.writeString(url)
        parcel.writeLong(totalTime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MusicBean1> {
        override fun createFromParcel(parcel: Parcel): MusicBean1 {
            return MusicBean1(parcel)
        }

        override fun newArray(size: Int): Array<MusicBean1?> {
            return arrayOfNulls(size)
        }
    }

}
