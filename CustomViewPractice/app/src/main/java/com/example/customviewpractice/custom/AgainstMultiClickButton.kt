package com.example.customviewpractice.custom

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatButton

class AgainstMultiClickButton(
    context: Context,
    attrs: AttributeSet
): AppCompatButton(context, attrs) {

    companion object {
        val CUSTOM_NAMESPACE = "http://schemas.android.com/apk/res/com.example.customviewpractice"
        var interval:Long? = null
    }

    init {
        // interval 값 초기화
        attrs.getAttributeValue(CUSTOM_NAMESPACE, "interval")?.let{
            interval = it.toLong()
        }
    }

    override fun setOnClickListener(l: OnClickListener?) {
        l?.apply {
            if(interval != null){
                super.setOnClickListener(OnAgainstMultiClickListener(this, interval!!))
            } else {
                super.setOnClickListener(OnAgainstMultiClickListener(this))
            }
        }
    }
}
