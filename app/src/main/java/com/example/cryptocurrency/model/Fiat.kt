package com.example.cryptocurrency.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.parcelize.Parcelize

@Parcelize
data class Fiat(
    @ColumnInfo(name = "fiat_name")
    val name:String,
    val rate:Double,
    @ColumnInfo(name = "fiat_symbol")
    val symbol:String,
    @ColumnInfo(name = "fiat_image_url")
    val imageUrl:String,
):Parcelable