package icu.sincos.ZhiWeiXiaoYuan_Extra.bean

import android.os.Parcelable
import icu.sincos.ZhiWeiXiaoYuan_Extra.model.UsrDataModel

data class UserData(
    val balance: String,
    val studentName: String,
    val cardNumber: String,
    val consumptionCount: String,
    val resultMemberFlow: String
)
