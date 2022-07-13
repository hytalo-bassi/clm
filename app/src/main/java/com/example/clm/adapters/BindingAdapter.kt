package com.example.clm.adapters

import android.view.View
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

@BindingAdapter("mask", "affine", requireAll = false)
fun bindMask(view: TextInputEditText, mask: String, affine: String?) {
    val affineFormats = ArrayList<String>()
    affine?.let { affineFormats.add(affine) }

    val listener = MaskedTextChangedListener(
        mask,
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