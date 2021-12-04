package com.example.cryptocurrency.formatters

import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.DecimalFormat
import kotlin.math.exp

class PointLogValueFormatter : ValueFormatter() {
    private val normalFormatter = DecimalFormat("#.###")

    override fun getPointLabel(entry: Entry?): String {
        var value = entry?.y
        if (value!=null){
            value= exp(value)
        }
        return normalFormatter.format(value)
    }
}