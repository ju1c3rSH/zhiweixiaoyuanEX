package icu.sincos.ZhiWeiXiaoYuan_Extra

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import icu.sincos.ZhiWeiXiaoYuan_Extra.databinding.ActivityMainBinding
import icu.sincos.ZhiWeiXiaoYuan_Extra.model.UsrDataModel
import icu.sincos.ZhiWeiXiaoYuan_Extra.services.DataService

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<UsrDataModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.userData.observe(this, Observer { _userData ->
            Log.d("MainActivity", "User data updated: $_userData")
        })


        val intent = Intent(baseContext, DataService::class.java)
        baseContext.startService(intent)

        val navView: BottomNavigationView = binding.navView

        // 获取导航控制器
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        // 配置顶级目标的 ID（根据你的导航图进行调整）
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )



        // 将导航控制器与操作栏关联
        setupActionBarWithNavController(navController, appBarConfiguration)

        // 将底部导航栏与导航控制器关联
        navView.setupWithNavController(navController)
    }
}
