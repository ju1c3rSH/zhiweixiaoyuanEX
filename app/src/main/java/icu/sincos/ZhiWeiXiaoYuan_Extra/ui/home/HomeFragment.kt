package icu.sincos.ZhiWeiXiaoYuan_Extra.ui.home

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import icu.sincos.ZhiWeiXiaoYuan_Extra.R
import icu.sincos.ZhiWeiXiaoYuan_Extra.bean.UserData
import icu.sincos.ZhiWeiXiaoYuan_Extra.databinding.FragmentHomeBinding
import icu.sincos.ZhiWeiXiaoYuan_Extra.model.UsrDataModel
import icu.sincos.ZhiWeiXiaoYuan_Extra.widget.ZeroLineLayout


class HomeFragment : Fragment() {
    private val MUsrDataModel by viewModels<UsrDataModel>()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("ResourceType", "UnspecifiedRegisterReceiverFlag")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val cbTV: TextView = root.findViewById(R.id.card_balance_textview)

        val textView: TextView = binding.textHome
        val CardBalance = root.findViewById<TextView>(R.id.card_balance_textview)
        val CardName = root.findViewById<TextView>(R.id.card_name_textview)
        val cCount = root.findViewById<TextView>(R.id.card_cCount_textview)
        val cardNumberTV = root.findViewById<TextView>(R.id.card_cardNumber_textview)

        MUsrDataModel._userData.observe(viewLifecycleOwner) {
            Log.d("HF", it.studentName)
        }

        return root


        //Log.d("HF", "2")
    }

    /*
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
    */




    fun updateUi(root: View,balance: String, studentName: String, consumptionCount: String, cardNumber: String){
        //传root是为了解决在后台睡死之后突然唤醒，接收不到信息导致闪退 23/10/4 LongEP Flandre
        //val root: View = binding.root
        //什么抽象代码 24/1/6 Flandre LongEP
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