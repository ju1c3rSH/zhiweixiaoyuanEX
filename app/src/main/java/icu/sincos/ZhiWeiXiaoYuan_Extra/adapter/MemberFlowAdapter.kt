package icu.sincos.ZhiWeiXiaoYuan_Extra.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import icu.sincos.ZhiWeiXiaoYuan_Extra.R
import icu.sincos.ZhiWeiXiaoYuan_Extra.bean.memberFlowBean
import icu.sincos.ZhiWeiXiaoYuan_Extra.bean.memberFlowBean.Data
import icu.sincos.ZhiWeiXiaoYuan_Extra.ui.JsonActivity

class MemberFlowAdapter(private val dataList: List<memberFlowBean.Data>, context: Context) :
    RecyclerView.Adapter<MemberFlowAdapter.ViewHolder>() {
    val gson = Gson()
    private val context: Context

    //var Context: Context = MainActivity().baseContext
    init {
        this.context = context
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val amountTextView: TextView = itemView.findViewById(R.id.tv_amount)
        val balanceTextView: TextView = itemView.findViewById(R.id.tv_balance)
        val timeTextView: TextView = itemView.findViewById(R.id.tv_time)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val dataItem = dataList[position]
        holder.amountTextView.text = "消费：" + dataItem.amount.toString()
        holder.balanceTextView.text = "余额：" + dataItem.balance.toString()
        holder.timeTextView.text = dataItem.consumeTime
        holder.itemView.setOnClickListener {
            //ToastUtils.showToastOnUiThread(, dataList[position].amount.toString())
            if (position != RecyclerView.NO_POSITION) {
                val clickedItem = dataList[position]
                listener?.onItemClick(clickedItem)
                val intent = Intent(context, JsonActivity::class.java)
                //val viewModel = ViewModelProvider(DashboardFragment()).get(MyViewModel::class.java) 必须绑定activity的onCreate()，希望某个傻逼以后可以重构这段
                //var adper: List<memberFlowBean.Data> = gson.fromJson(viewModel.json.toString(), memberFlowBean::class.java).datas
                intent.putExtra("list", dataItem.toString())
                Log.i("DashBroadFragment", "JsonLaunch!!  " + "heresdata" + dataItem.toString())
                context.startActivity(intent)
                Log.i("MFA", "clicked: " + holder.position.toString())
            } else {
                Log.i("MFA", "No Position.")
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val dataItem = this.dataList
        //this.Context = parent.context
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_balance, parent, false)
        return ViewHolder(view)
    }

    private var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(item: Data)
    }


}