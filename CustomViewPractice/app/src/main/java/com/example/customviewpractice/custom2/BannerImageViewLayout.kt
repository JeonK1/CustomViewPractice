package com.example.customviewpractice.custom2

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RelativeLayout
import com.example.customviewpractice.R
import kotlinx.android.synthetic.main.custom_banner_layout.view.*
import java.util.*

class BannerImageViewLayout(context: Context, attrs: AttributeSet): RelativeLayout(context, attrs) {
    companion object{
        val CUSTOM_NAMESPACE = "http://schemas.android.com/apk/res/com.example.customviewpractice"
        var IMAGE_SWITCH_INTERVAL = 5000L // 이미지 스위치되는 시간간격
        val image_list = arrayListOf(R.drawable.first_image, R.drawable.second_image, R.drawable.third_image)
        var page = 0
    }

    init {
        // interval 값 초기화
        attrs.getAttributeValue(CUSTOM_NAMESPACE, "switch_interval")?.let{
            IMAGE_SWITCH_INTERVAL = it.toLong()
        }

        // 타이머 초기화
        var timer = getTimer()

        inflate(context, R.layout.custom_banner_layout, this) // xml 파일 적용
        banner_image.setFactory { ImageView(context) }
        banner_image.setImageResource(image_list[0]) // 초기 이미지는 첫번째 이미지

        left_button.setOnClickListener {
            // 왼쪽 버튼 클릭
            banner_move_prev()
            
            // 타이머 취소 및 초기화
            timer.cancel()
            timer = getTimer()
        }
        right_button.setOnClickListener {
            // 오른쪽 버튼
            banner_move_next()
            
            // 타이머 취소 및 초기화
            timer.cancel()
            timer = getTimer()
        }
    }

    fun banner_move_next(){
        if(++page > image_list.size-1)
            page = 0
        banner_image.inAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_in_left) // image 전환 애니메이션
        banner_image.outAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_out_right)  // image 전환 애니메이션
        banner_image.setImageResource(image_list[page])
    }

    fun banner_move_prev() {
        if(--page < 0)
            page = image_list.size-1
        banner_image.inAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_in_right) // image 전환 애니메이션
        banner_image.outAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_out_left)  // image 전환 애니메이션
        banner_image.setImageResource(image_list[page])
    }

    fun getTimer(): Timer {
        val myHandler = Handler(Looper.getMainLooper()) {
            banner_move_next()
            true
        }

        return Timer().apply {
            schedule(object: TimerTask(){
                override fun run() {
                    myHandler.sendMessage(myHandler.obtainMessage())
                }
            }, IMAGE_SWITCH_INTERVAL, IMAGE_SWITCH_INTERVAL)
        }
    }
}
