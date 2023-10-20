package icu.sincos.ZhiWeiXiaoYuan_Extra

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import icu.sincos.ZhiWeiXiaoYuan_Extra.databinding.ActivityMainBinding
import icu.sincos.ZhiWeiXiaoYuan_Extra.services.DataService

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
