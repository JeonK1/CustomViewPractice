package com.example.customviewpractice.custom

import android.util.Log
import android.view.View

class OnAgainstMultiClickListener(
    private val clickListener: View.OnClickListener,
    private val interval: Long = 1000
): View.OnClickListener {
    private var clickable = true

    override fun onClick(p0: View?) {
        if(clickable){
            clickable = false
            p0?.run {
                postDelayed({
                    clickable = true
                }, interval)
                clickListener.onClick(p0)
            }
        } else {
            Log.e("test", "wait...")
        }
    }
}
