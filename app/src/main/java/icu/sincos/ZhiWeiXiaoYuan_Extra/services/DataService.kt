package icu.sincos.ZhiWeiXiaoYuan_Extra.services

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import icu.sincos.ZhiWeiXiaoYuan_Extra.TextUtils.toast
import icu.sincos.ZhiWeiXiaoYuan_Extra.bean.Student
import icu.sincos.ZhiWeiXiaoYuan_Extra.bean.balanceBean

import icu.sincos.ZhiWeiXiaoYuan_Extra.bean.MemberFlowJsonBuilder
import icu.sincos.ZhiWeiXiaoYuan_Extra.bean.QueryJsonBuilder
import icu.sincos.ZhiWeiXiaoYuan_Extra.bean.UserData
import icu.sincos.ZhiWeiXiaoYuan_Extra.bean.memberFlowBean
import icu.sincos.ZhiWeiXiaoYuan_Extra.model.UsrDataModel
import icu.sincos.ZhiWeiXiaoYuan_Extra.util.AppConstants
import icu.sincos.ZhiWeiXiaoYuan_Extra.util.AppConstants.copyPersonCode
import icu.sincos.ZhiWeiXiaoYuan_Extra.util.CiperTextUtil
import icu.sincos.ZhiWeiXiaoYuan_Extra.util.DateUtils
import icu.sincos.ZhiWeiXiaoYuan_Extra.util.OkHttpUtils
import org.json.JSONObject
import java.net.URLEncoder
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

class DataService : Service() {


    
    val BROADCAST_ACTION = "icu.sincos.zhiweixiaoyuan_rebuild.service.DataService"

    val gson = Gson()
    private var resultGetRole: String? = null
    private var resultLogin: String? = null
    private var resultKid: String? = null
    private  var resultBalance: String? = null
    private  var resultMemberFlow: String? = null
    private  var cipherText: String? = null
    private var resultQuery: String? = null
    private  var getRoleNoAuthUrlEncoded: String? = null
    private val uiHandler = Handler(Looper.getMainLooper())
    private val RETRY_DELAY_MS = 10000L // 重试延迟时间（毫秒!!!）
    private val MAX_RETRY_COUNT = 3 // 最大重试次数
    private val executor = Executors.newSingleThreadScheduledExecutor()

    var MuserData = MutableLiveData<String>()


    override fun onCreate() {
        super.onCreate()

        Log.d("DataService", "OnCreate!!")

    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        executor.scheduleAtFixedRate(threakd(), 0, 10, TimeUnit.SECONDS);
        return START_STICKY
    }

    private fun threakd(): Runnable {
        return Runnable {
            var retryCount = 0
            val getMemberFlowJson = MemberFlowJsonBuilder(
                "cfb0961b2c844923902ed6544a878467",
                listOf(2, 5, 6, 7),
                7,
                1,
                100,
                DateUtils.Date2Str(-30),
                DateUtils.Date2Str(1)
            )

            val QueryJson = QueryJsonBuilder(
                1,
                10,
                copyPersonCode.toString(),
                listOf(1)
            )
            var QueryJsonPostJson = gson.toJson(QueryJson)
            var MemberFlowPostjson = gson.toJson(getMemberFlowJson)
            while (retryCount < MAX_RETRY_COUNT) {
                try {
                    cipherText = CiperTextUtil.encrypt(AppConstants.wxOa)
                    getRoleNoAuthUrlEncoded = AppConstants.getNoAuth + URLEncoder.encode(cipherText, "utf-8")
                    val headerMap = HashMap<String, String>().apply {
                        put(
                            "User-Agent",
                            "FuckYou/5.0 (Linux; Android 5.0; SM-N9100 Build/LRX21V) > AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 > Chrome/37.0.0.0 Mobile Safari/537.36 > MicroMessenger/6.0.2.56_r958800.520 NetType/WIFI"
                        )
                    }
                    resultGetRole = OkHttpUtils.get(getRoleNoAuthUrlEncoded, headerMap).toString()
                    if(resultGetRole!!.contains("unavailable")){
                        "Server Error".toast()
                        Log.e("Login", "Server Error")
                        break
                    }
                    resultLogin =
                        OkHttpUtils.postJson(AppConstants.loginUrl, AppConstants.jsonStr, headerMap).toString()
                    resultKid = OkHttpUtils.get(AppConstants.getAllStudentsOfParentUrl, headerMap).toString()
                    resultBalance = OkHttpUtils.get(AppConstants.getBalance, headerMap).toString()
                    resultMemberFlow =
                        OkHttpUtils.postJson(AppConstants.getMemberFlow, MemberFlowPostjson, headerMap)
                    resultQuery = OkHttpUtils.postJson(AppConstants.queryUrl, QueryJsonPostJson, headerMap)
                    break // 如果成功，跳出重试循环
                } catch (e: Exception) {
                    "网络连接失败，请检查网络".toast()
                    Log.e("DataService", "getSomeInfo" + e.message)
                    retryCount++
                    if (retryCount < MAX_RETRY_COUNT) {
                        try {
                            Thread.sleep(RETRY_DELAY_MS)
                        } catch (ex: InterruptedException) {
                            Log.e("DataService", "Thread interrupted: ${ex.message}")
                            break
                        }
                    } else {
                        "网络连接失败，请检查网络".toast()
                        break
                    }
                }
            }
            Log.i("DataService", "GetRoleInfo: $resultGetRole")
            Log.i("DataService", "LoginInfo: $resultLogin")

            Log.i("DataService", "KidInfo: $resultKid")
            Log.i("DataService", "BalanceInfo: $resultBalance")
            Log.i("DataService", "MemberFlowInfo: $resultMemberFlow")
            Log.i("DataService", "QueryInfo: $resultQuery")

            if (resultGetRole != null) {

                var balance = gson.fromJson(resultBalance, balanceBean::class.java).balance
                var studentList: List<Student> =
                    gson.fromJson(resultKid, object : TypeToken<List<Student>>() {}.type)
                var studentName: String = studentList.firstOrNull()?.studentName ?: "默认姓名"
                var consumptionCount: String =
                    gson.fromJson(resultMemberFlow, memberFlowBean::class.java).total.toString()
                var _cardNumber: String =
                    gson.fromJson(resultMemberFlow, memberFlowBean::class.java).datas[0].cardNumber.toString()

                    sendData2Activity(balance.toString()
                        , studentName.toString()
                        , consumptionCount.toString()
                        , _cardNumber.toString()
                        , resultMemberFlow.toString()
                    )

                val jsonData = JSONObject().apply {
                    put("balance", balance)
                    put("studentName", studentName)
                    put("cardNumber", _cardNumber)
                    put("consumptionCount", consumptionCount)
                    put("resultMemberFlow", resultMemberFlow)
                }
                val jsonDataString = jsonData.toString()
                Log.i("DataService", "jsonDataString: $jsonDataString")
                UsrDataModel().setUserData(jsonDataString)
                MuserData.postValue(jsonDataString)
                Log.i("DataService", "balance: $balance")
                //uiHandler.post {
                    //val balanceTextView = binding.balanceTextView
                    //val studentNameTV = root.findViewById<TextView>(R.id.name_textView)
//                val CardBalance = root.findViewById<TextView>(R.id.card_balance_textview)
//                val CardName = root.findViewById<TextView>(R.id.card_name_textview)
                    //balanceTextView.text = balance.toString()
                    // updateUi(balance.toString(), studentName, consumptionCount, _cardNumber)
//                CardBalance.text = balance.toString()
//                CardName.text = studentName
                    //studentNameTV.text = studentName.toString()
               // }
            } else {
                "网络连接失败，请检查网络".toast()
            }
        }
    }


    private fun sendData2Activity(
        balance: String,
        studentName: String,
        _cardNumber: String,
        consumptionCount: String,
        resultMemberFlow: String
    ){
        val intent = Intent(BROADCAST_ACTION)
        intent.putExtra("balance", balance)
                .putExtra("studentName", studentName)
                .putExtra("_cardNumber", _cardNumber)
                .putExtra("consumptionCount", consumptionCount)
                .putExtra("resultMemberFlow", resultMemberFlow)
        sendBroadcast(intent)
    }
}


