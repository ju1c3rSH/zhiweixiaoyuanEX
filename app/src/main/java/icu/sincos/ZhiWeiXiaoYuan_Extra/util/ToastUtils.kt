package icu.sincos.ZhiWeiXiaoYuan_Extra.util

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast

object ToastUtils {
    private val uiHandler = Handler(Looper.getMainLooper())

    fun showToastOnUiThread(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
        uiHandler.post {
            Toast.makeText(context, message, duration).show()
        }
    }


}