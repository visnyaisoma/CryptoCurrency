package com.example.cryptocurrency.formatters

import com.example.cryptocurrency.database.Converter
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat


class XAxisDateFormatter: ValueFormatter() {

    private val sdf =  SimpleDateFormat("dd MMM yy")
    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        val longVal = value.toLong()
        return sdf.format(Converter.fromLongToDate(longVal))
    }
}