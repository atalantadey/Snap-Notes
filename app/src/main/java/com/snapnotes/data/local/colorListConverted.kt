package com.snapnotes.data.local

import androidx.room.TypeConverter

class colorListConverted {
    @TypeConverter
    fun fromColorList(colorList:List<Int>):String{
        return colorList.joinToString(","){it.toString()}
    }
    @TypeConverter
    fun toColorList(colorListString: String):List<Int>{
        return colorListString.split(",").map { it.toInt() }
    }

}