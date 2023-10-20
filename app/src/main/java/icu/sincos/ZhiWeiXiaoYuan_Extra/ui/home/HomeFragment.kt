package icu.sincos.ZhiWeiXiaoYuan_Extra.ui.home

import android.annotation.SuppressLint
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import icu.sincos.ZhiWeiXiaoYuan_Extra.R
import icu.sincos.ZhiWeiXiaoYuan_Extra.databinding.FragmentHomeBinding
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import icu.sincos.ZhiWeiXiaoYuan_Extra.services.DataService
import icu.sincos.ZhiWeiXiaoYuan_Extra.widget.ZeroLineLayout


class HomeFragment : Fragment() {
    private var resultGetRole: String? = null
    private var resultLogin: String? = null
    private var resultKid: String? = null
    private  var resultBalance: String? = null
    private  var resultMemberFlow: String? = null
    private  var cipherText: String? = null
    private  var getRoleNoAuthUrlEncoded: String? = null
    private val uiHandler = Handler(Looper.getMainLooper())
    private val RETRY_DELAY_MS = 10000L // 重试延迟时间（毫秒!!!）
    private val MAX_RETRY_COUNT = 3 // 最大重试次数（毫秒!!!）
    val gson = Gson()


    private var _binding: FragmentHomeBinding? = null

    //var be : balanceBean? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.i("receiver", "broadCast received!!")
            if (intent != null && intent.action == "icu.sincos.zhiweixiaoyuan_rebuild.service.DataService") {
                val balance = intent.getStringExtra("balance").toString()
                val studentName = intent.getStringExtra("studentName").toString()
                val _cardNumber = intent.getStringExtra("_cardNumber").toString()
                val consumptionCount = intent.getStringExtra("consumptionCount").toString()
                val resultMemberFlow = intent.getStringExtra("resultMemberFlow").toString()
                Log.i("receiver", "broadCast received!!")
                val ViewModel = ViewModelProvider(requireActivity()).get(icu.sincos.ZhiWeiXiaoYuan_Extra.MyViewModel::class.java)
                ViewModel.json = resultMemberFlow.toString()
                Log.i("HomeFragmentViewModel", ViewModel.json)

                // 处理接收到的数据
                updateUi(binding.root, balance, studentName, _cardNumber, consumptionCount)
            }
        }
    }



    @SuppressLint("ResourceType", "UnspecifiedRegisterReceiverFlag")
    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        val filter = IntentFilter("icu.sincos.zhiweixiaoyuan_rebuild.service.DataService")
        requireActivity().registerReceiver(receiver, filter)
        // 在 HomeFragment 中启动 DataService 服务
        val intent: Intent = Intent(requireContext(), DataService::class.java)
        requireActivity().startService(intent)

        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val cbTV : TextView = root.findViewById(R.id.card_balance_textview)
        //val bTV : TextView = root.findViewById(R.id.balance_textView)

        //val nTV : TextView = root.findViewById(R.id.name_textView)
        val zeroLineLayout = root.findViewById<ZeroLineLayout>(R.layout.item_balance)
        //bTV.setText("Now Loading")

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

//        thread {
//            var retryCount = 0
//            //1 = 充值记录
//            //2 = 正常消费
//            //3 = 扣工本费
//            //4 = 未知
//            //5 = 未知
//            //6 = 未知
//            //7 = 未知
//
//
//            val getMemberFlowJson = getMemberFlowJsonBuilder(
//                "cfb0961b2c844923902ed6544a878467",
//                listOf(2,5,6,7),
//                7,
//                1,
//                150,
//                DateUtils.Date2Str (null,true),
//                DateUtils.Date2Str(1)
//            )
//            var json = gson.toJson(getMemberFlowJson)
//            while (retryCount < MAX_RETRY_COUNT) {
//                try {
//                    cipherText = CiperTextUtil.encrypt(AppConstants.wxOa)
//                    getRoleNoAuthUrlEncoded = AppConstants.getNoAuth + URLEncoder.encode(cipherText, "utf-8")
//                    val headerMap = HashMap<String, String>().apply {
//                        put("User-Agent", "FuckYou/5.0 (Linux; Android 5.0; SM-N9100 Build/LRX21V) > AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 > Chrome/37.0.0.0 Mobile Safari/537.36 > MicroMessenger/6.0.2.56_r958800.520 NetType/WIFI")
//                    }
//                    resultGetRole = OkHttpUtils.get(getRoleNoAuthUrlEncoded, headerMap).toString()
//                    resultLogin = OkHttpUtils.postJson(AppConstants.loginUrl, AppConstants.jsonStr, headerMap).toString()
//                    resultKid = OkHttpUtils.get(AppConstants.getAllStudentsOfParentUrl, headerMap).toString()
//                    resultBalance = OkHttpUtils.get(AppConstants.getBalance, headerMap).toString()
//                    resultMemberFlow = OkHttpUtils.postJson(AppConstants.getMemberFlow, json, headerMap)
//                    break // 如果成功，跳出重试循环
//                } catch (e: Exception) {
//                    activity?.runOnUiThread {
//                        Toast.makeText(context, "网络连接失败，请检查网络", Toast.LENGTH_SHORT).show()
//                    }
//                    Log.e("HomeFragment", "getSomeInfo" + e.message)
//                    retryCount++
//                    if (retryCount < MAX_RETRY_COUNT) {
//                        try {
//                            Thread.sleep(RETRY_DELAY_MS)
//                        } catch (ex: InterruptedException) {
//                            Log.e("HomeFragment", "Thread interrupted: ${ex.message}")
//                            break
//                        }
//                    } else {
//                        uiHandler.post {
//                            Toast.makeText(context, "操你妈的还不去联网在这干嘛呢？", Toast.LENGTH_SHORT).show()
//                        }
//                        break
//                    }
//                }
//            }
//
//            Log.i("HomeFragment", "LoginInfo: $resultLogin")
//            Log.i("HomeFragment", "GetRoleInfo: $resultGetRole")
//            Log.i("HomeFragment", "KidInfo: $resultKid")
//            Log.i("HomeFragment", "BalanceInfo: $resultBalance")
//            Log.i("HomeFragment", "MemberFlowInfo: $resultMemberFlow")
//            Log.i("HomeFragment", "MemberFlowInfo: $json")
//
//
//
//            if(resultGetRole != null){
//
//                val balance = gson.fromJson(resultBalance, balanceBean::class.java).balance
//                val studentList: List<Student> = gson.fromJson(resultKid, object : TypeToken<List<Student>>() {}.type)
//                val studentName: String = studentList.firstOrNull()?.studentName ?: "默认姓名"
//                val consumptionCount: String = gson.fromJson(resultMemberFlow, memberFlowBean::class.java).total.toString()
//                val _cardNumber: String = gson.fromJson(resultMemberFlow, memberFlowBean::class.java).datas[0].cardNumber.toString()
//                val ViewModel = ViewModelProvider(requireActivity()).get(icu.sincos.ZhiWeiXiaoYuan_Extra.MyViewModel::class.java)
//                ViewModel.json = resultMemberFlow.toString()
//                Log.i("HomeFragmentViewModel", ViewModel.json)
//
//
//
//
//                Log.i("HomeFragment", "balance: $balance")
//                uiHandler.post {
//                    // 在主线程中更新 UI 或执行其他操作
//                    //val balanceTextView = binding.balanceTextView // 替换为你的 TextView 的引用
//                    //val studentNameTV = root.findViewById<TextView>(R.id.name_textView)
////                val CardBalance = root.findViewById<TextView>(R.id.card_balance_textview)
////                val CardName = root.findViewById<TextView>(R.id.card_name_textview)
//                    //balanceTextView.text = balance.toString()
//                    updateUi(root, balance.toString(), studentName, consumptionCount, _cardNumber)
////                CardBalance.text = balance.toString()
////                CardName.text = studentName
//
//                    //studentNameTV.text = studentName.toString()
//
//                    // 更新其他 UI 元素
//                }
//            } else { uiHandler.post { Toast.makeText(context, "网络连接失败，请检查网络", Toast.LENGTH_SHORT).show() }}
//
//        }





        return root
    }



    fun updateUi(root: View,balance: String, studentName: String, consumptionCount: String, cardNumber: String){
        //传root是为了解决在后台睡死之后突然唤醒，接收不到信息导致闪退 23/10/4 LongEP Flandre
        //val root: View = binding.root
        val CardBalance = root.findViewById<TextView>(R.id.card_balance_textview)
        val CardName = root.findViewById<TextView>(R.id.card_name_textview)
        val cCount = root.findViewById<TextView>(R.id.card_cCount_textview)
        val cardNumberTV = root.findViewById<TextView>(R.id.card_cardNumber_textview)
        cardNumberTV.text = cardNumber
        cCount.text = consumptionCount
        CardBalance.text = balance.toString()
        CardName.text = studentName

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}