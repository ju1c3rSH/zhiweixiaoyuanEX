package icu.sincos.ZhiWeiXiaoYuan_Extra.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "测试学习用"
    }
    val text: LiveData<String> = _text
}