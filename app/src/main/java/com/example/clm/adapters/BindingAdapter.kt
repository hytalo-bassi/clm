package com.example.clm.adapters

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.redmadrobot.inputmask.helper.AffinityCalculationStrategy

@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

@BindingAdapter("mask")
fun bindMask(view: TextInputEditText, _mask: String) {
    val affineFormats = ArrayList<String>()
    val masks = _mask.split(("[|]"))
    affineFormats.addAll(masks.drop(1))

    val listener = MaskedTextChangedListener(
        masks[0],
        affineFormats,
        AffinityCalculationStrategy.CAPACITY,
        false,
        view,
        null,
        null
    )

    view.addTextChangedListener(listener)
    view.onFocusChangeListener = listener
}

@BindingAdapter("mask")
fun bindMask(view: TextView, _mask: String) {
    val masks = _mask.split("[|]")
    val mask = if (masks.size == 1) {
        masks[0]
    } else {
        masks[findAffine(view.text, masks)]
    }

    view.text = numberFormat(view.text as String, mask, '#')
}

fun qtHash(mask: String): Int {
    return Regex("[^#]+").replace(mask, "").length
}

fun findAffine(str: CharSequence, masks: List<String>) : Int {
    for ((index, mask) in masks.withIndex()) {
        if (qtHash(mask) != str.length) { return index }
    }

    return -1
}

fun numberFormat(number: String, template: String, charTemplate: Char): String {
    var formatted = ""
    var numberPosition  = 0

    for (c in template) {
        if (c == charTemplate) {
            formatted += number[numberPosition]
            numberPosition += 1
            continue
        }

        formatted += c
    }

    return formatted
}