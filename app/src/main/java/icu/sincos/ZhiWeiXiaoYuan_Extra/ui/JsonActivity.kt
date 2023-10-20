package icu.sincos.ZhiWeiXiaoYuan_Extra.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import icu.sincos.ZhiWeiXiaoYuan_Extra.MainActivity
import icu.sincos.ZhiWeiXiaoYuan_Extra.MyViewModel
import icu.sincos.ZhiWeiXiaoYuan_Extra.R
import icu.sincos.ZhiWeiXiaoYuan_Extra.adapter.MemberFlowAdapter
import icu.sincos.ZhiWeiXiaoYuan_Extra.bean.memberFlowBean

class JsonActivity : AppCompatActivity(), MemberFlowAdapter.OnItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        val gson = Gson()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_json)
        val list = intent.getStringExtra("list")
        Log.i("JsonActivity", "amount is $list")
        if(list != null){
            val textViewAmount = findViewById<TextView>(R.id.editTextCopyable)
            textViewAmount.text = list
        }else{
            Log.d("JsonActivity", "list is null")
        }
        //val viewModel = ViewModelProvider(MainActivity()).get(MyViewModel::class.java)
        //var adper: List<memberFlowBean.Data> = gson.fromJson(viewModel.json.toString(), memberFlowBean.Data[]::class.java)
    }

    override fun onItemClick(item: memberFlowBean.Data) {

        val amount = item.amount
        val textViewAmount = findViewById<TextView>(R.id.editTextCopyable)
        textViewAmount.text = "金额：" + amount.toString()
    }




}