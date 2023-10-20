package icu.sincos.ZhiWeiXiaoYuan_Extra.ui.notifications

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import icu.sincos.ZhiWeiXiaoYuan_Extra.MainActivity
import icu.sincos.ZhiWeiXiaoYuan_Extra.R
import icu.sincos.ZhiWeiXiaoYuan_Extra.databinding.FragmentNotificationsBinding
import icu.sincos.ZhiWeiXiaoYuan_Extra.util.BitmapUtils

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val textView: TextView = binding.textNotifications
        notificationsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.flan)
        //val mBitmap = BitmapFactory.decodeResource(resources, R.drawable.flan)

        loadBlurBackground()


        return root
    }

    private fun loadBlurBackground() {
        try {
            val imageView : ImageView = binding.imageView2
            val mainActivity = requireActivity() as MainActivity
            val bitMap = BitmapFactory.decodeResource(mainActivity.baseContext.resources, R.drawable.flan)
            imageView.setImageBitmap(bitMap)
            BitmapUtils.setBlurBackground(binding.imageView, bitMap)

        } catch (e: Exception) {
            Log.e("NotificationsFragment", "Error: $e")
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}