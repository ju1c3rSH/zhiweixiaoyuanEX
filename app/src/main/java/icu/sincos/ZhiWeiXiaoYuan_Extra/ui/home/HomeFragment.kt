package icu.sincos.ZhiWeiXiaoYuan_Extra.ui.home

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
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
    private var balance: String = ""
    private var studentName: String = ""
    private var cardNumber: String = ""
    private var consumptionCount: String = ""
    private lateinit var sharedPreferences: SharedPreferences
    @SuppressLint("UnspecifiedRegisterReceiverFlag")
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
        sharedPreferences = requireContext().getSharedPreferences("ZWPerfs", Context.MODE_PRIVATE)
        MUsrDataModel.userData.observe(viewLifecycleOwner) {
            Log.d("HF", it.studentName)
        }

        val filter = IntentFilter("icu.sincos.zhiweixiaoyuan_rebuild.service.DataService")
        requireContext().registerReceiver(receiver, filter)

        val usrDataModel = ViewModelProvider(this).get(UsrDataModel::class.java)

        requireActivity().runOnUiThread {
            usrDataModel.userData.observe(viewLifecycleOwner, Observer { userData ->
                Log.d("HF", userData.toString())
                updateUi(root, userData.balance, userData.studentName, userData.consumptionCount, userData.cardNumber)
            })
        }

        return root
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.i("receiver", "broadCast received!!")
            if (intent != null && intent.action == "icu.sincos.zhiweixiaoyuan_rebuild.service.DataService") {
                balance = intent.getStringExtra("balance").toString()
                studentName = intent.getStringExtra("studentName").toString()
                cardNumber = intent.getStringExtra("_cardNumber").toString()
                consumptionCount = intent.getStringExtra("consumptionCount").toString()
                val resultMemberFlow = intent.getStringExtra("resultMemberFlow").toString()
                Log.i("receiver", "broadCast received!!")
                val ViewModel = ViewModelProvider(requireActivity()).get(icu.sincos.ZhiWeiXiaoYuan_Extra.MyViewModel::class.java)
                ViewModel.json = resultMemberFlow
                Log.i("HomeFragmentViewModel", ViewModel.json)
                updateUi(binding.root, balance, studentName, cardNumber, consumptionCount)
                val editor = sharedPreferences.edit()
                editor.putString("balance", balance)
                editor.putString("studentName", studentName)
                editor.putString("cardNumber", cardNumber)
                editor.putString("consumptionCount", consumptionCount)
                editor.apply()
            }
        }
    }

    fun updateUi(root: View, balance: String, studentName: String, cardNumber: String, consumptionCount: String) {
        val CardBalance = root.findViewById<TextView>(R.id.card_balance_textview)
        val CardName = root.findViewById<TextView>(R.id.card_name_textview)
        val cCount = root.findViewById<TextView>(R.id.card_cCount_textview)
        val cardNumberTV = root.findViewById<TextView>(R.id.card_cardNumber_textview)
        cardNumberTV.text = cardNumber
        cCount.text = consumptionCount
        CardBalance.text = balance
        CardName.text = studentName
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        requireContext().unregisterReceiver(receiver)
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    override fun onResume() {
        super.onResume()

        balance = sharedPreferences.getString("balance", "") ?: ""
        studentName = sharedPreferences.getString("studentName", "") ?: ""
        cardNumber = sharedPreferences.getString("cardNumber", "") ?: ""
        consumptionCount = sharedPreferences.getString("consumptionCount", "") ?: ""
        updateUi(binding.root, balance, studentName, cardNumber, consumptionCount)

        val filter = IntentFilter("icu.sincos.zhiweixiaoyuan_rebuild.service.DataService")
        requireContext().registerReceiver(receiver, filter)
    }

    override fun onPause() {
        super.onPause()
        //requireContext().unregisterReceiver(receiver)
    }
}
