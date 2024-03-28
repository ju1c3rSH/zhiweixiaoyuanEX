package icu.sincos.ZhiWeiXiaoYuan_Extra.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import icu.sincos.ZhiWeiXiaoYuan_Extra.bean.UserData
import icu.sincos.ZhiWeiXiaoYuan_Extra.services.DataService
import org.json.JSONObject

class UsrDataModel : ViewModel() {

    val _userData = MutableLiveData<UserData>()
    val userData: LiveData<UserData> get() = _userData

    private val dataService = DataService()

    fun setUserData(userDataJson: String) {
        val json = JSONObject(userDataJson)

        val newData = UserData(
            json.getString("balance"),
            json.getString("studentName"),
            json.getString("cardNumber"),
            json.getString("consumptionCount"),
            json.getString("resultMemberFlow")
        )

        _userData.postValue(newData)
        Log.d("UserDataModel", "newData: $newData")
        Log.d("UserDataModel", "setUserData: ${userData.value}")
        Log.d("UserDataModel", "_UserData: ${_userData.value}")
    }
}
