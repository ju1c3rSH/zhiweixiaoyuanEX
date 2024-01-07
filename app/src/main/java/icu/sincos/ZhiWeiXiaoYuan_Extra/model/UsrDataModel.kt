package icu.sincos.ZhiWeiXiaoYuan_Extra.model

import android.annotation.SuppressLint
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
    val MuserData: LiveData<String> get() = DataService().MuserData

    @SuppressLint("SuspiciousIndentation")
    fun setUserData(userDataJson: String) {
        val json = JSONObject(userDataJson)
        //Log.d("UserDataModel", "1")
        val newData = UserData(
            json.getString("balance").toString(),
            json.getString("studentName").toString(),
            json.getString("cardNumber").toString(),
            json.getString("consumptionCount").toString(),
            json.getString("resultMemberFlow").toString()
        )
        //Log.d("UserDataModel", "2")
            _userData.postValue(newData)
            Log.d("UserDataModel", "newData: ${newData}")
            Log.d("UserDataModel", "setUserData: ${userData.value}" )
            Log.d("UserDataModel", "_UserData: ${_userData.value}")
    }





}


