package com.u1fukui.bbs.customview

import android.content.Context
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView

import com.u1fukui.bbs.R

class ErrorView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    interface ErrorViewListener {
        fun onClickReloadButton()
    }

    init {
        orientation = LinearLayout.VERTICAL
        gravity = Gravity.CENTER
        DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(context), R.layout.view_error, this, true)
    }

    companion object {

        @BindingAdapter("errorMessage")
        @JvmStatic
        fun errorMessage(view: View, message: String?) {
            val messageView = view.findViewById<View>(R.id.error_message) as TextView
            messageView.text = message
        }

        @BindingAdapter("errorViewListener")
        @JvmStatic
        fun onClickReloadButton(view: View, listener: ErrorViewListener?) {
            view.findViewById<View>(R.id.reload_btn).apply {
                setOnClickListener {
                    listener?.onClickReloadButton()
                }
            }
        }
    }
}
