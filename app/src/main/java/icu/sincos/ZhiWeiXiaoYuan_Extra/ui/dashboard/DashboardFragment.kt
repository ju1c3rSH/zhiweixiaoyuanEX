package icu.sincos.ZhiWeiXiaoYuan_Extra.ui.dashboard

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import icu.sincos.ZhiWeiXiaoYuan_Extra.MyViewModel
import icu.sincos.ZhiWeiXiaoYuan_Extra.adapter.MemberFlowAdapter
import icu.sincos.ZhiWeiXiaoYuan_Extra.bean.memberFlowBean
import icu.sincos.ZhiWeiXiaoYuan_Extra.databinding.FragmentDashboardBinding
import icu.sincos.ZhiWeiXiaoYuan_Extra.ui.JsonActivity

class DashboardFragment : Fragment(), MemberFlowAdapter.OnItemClickListener {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    val gson = Gson()


    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val recyclerView: RecyclerView = binding.recyclerView
        val viewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)
        var adper: List<memberFlowBean.Data> = gson.fromJson(viewModel.json.toString(), memberFlowBean::class.java).datas
        Log.i("DashBroadFragment", viewModel.json.toString())
        //Log.i("DashBroadFragment", memberFlowBean::)

        val adapter = this.context?.let { MemberFlowAdapter(adper, it) }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setLayoutManager(recyclerView.layoutManager);
        recyclerView.adapter = adapter


        val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(item: memberFlowBean.Data){
        val intent = Intent(this.context, JsonActivity::class.java)
        intent.putExtra("amount", item.amount)
        Log.i("DashBroadFragment", "JsonLaunch!!")
        startActivity(intent)
    }
}