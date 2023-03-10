package com.bpi.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.bpi.utils.CurrencyTypeConverter
import com.bpi.utils.DateTypeConverter
import java.util.*

@Entity(tableName = "bpi_table")
@TypeConverters(CurrencyTypeConverter::class, DateTypeConverter::class)
data class Bpi(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val USD: Currency,
    val GBP: Currency,
    val EUR: Currency,
    val updatedISO: Date
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readParcelable<Currency>(Currency::class.java.classLoader)!!,
        parcel.readParcelable<Currency>(Currency::class.java.classLoader)!!,
        parcel.readParcelable<Currency>(Currency::class.java.classLoader)!!,
        Date(parcel.readLong())
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeParcelable(USD, flags)
        parcel.writeParcelable(GBP, flags)
        parcel.writeParcelable(EUR, flags)
        parcel.writeLong(updatedISO?.time ?: 0L)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Bpi> {
        override fun createFromParcel(parcel: Parcel): Bpi {
            return Bpi(parcel)
        }

        override fun newArray(size: Int): Array<Bpi?> {
            return arrayOfNulls(size)
        }
    }
}
