package com.u1fukui.bbs.customview

import android.content.Context
import android.databinding.BindingAdapter
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView

import com.u1fukui.bbs.R

class ErrorView : LinearLayout {

    interface ErrorViewListener {
        fun onClickReloadButton()
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        orientation = LinearLayout.VERTICAL
        gravity = Gravity.CENTER
        DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(context), R.layout.view_error, this, true)
    }

    companion object {

        @BindingAdapter("errorMessage")
        fun errorMessage(view: View, message: String) {
            val messageView = view.findViewById<View>(R.id.error_message) as TextView
            messageView.text = message
        }

        @BindingAdapter("errorViewListener")
        fun onClickReloadButton(view: View, listener: ErrorViewListener?) {
            val reloadButton = view.findViewById<View>(R.id.reload_btn)
            if (reloadButton != null && listener != null) {
                reloadButton.setOnClickListener { view1 ->
                    listener?.onClickReloadButton()
                }
            }
        }
    }
}
