package com.example.cryptocurrency.formatters

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.DecimalFormat
import kotlin.math.exp

class YAxisLogFormatter:ValueFormatter() {
    private val longFormatter = DecimalFormat("#.######")
    private val normalFormatter = DecimalFormat("#.###")
    private val shortFormatter = DecimalFormat("#.#")

    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        val form:DecimalFormat
        when {
            value<1 -> form=longFormatter
            value<1000 -> form=normalFormatter
            else -> form=shortFormatter
        }
        return form.format(exp(value)).toString()
    }

}