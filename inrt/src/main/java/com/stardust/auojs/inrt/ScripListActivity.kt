package com.stardust.auojs.inrt

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import com.stardust.auojs.inrt.autojs.AccessibilityServiceTool
import com.stardust.auojs.inrt.autojs.AutoJs
import com.stardust.auojs.inrt.launch.GlobalDouYinLauncher
import com.stardust.auojs.inrt.launch.GlobalKuaiShouLauncher
import com.stardust.auojs.inrt.launch.GlobalRedLauncher
import com.stardust.util.UiHandler
import kotlinx.android.synthetic.main.activity_scrip_list.*
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import androidx.fragment.app.FragmentActivity


class ScripListActivity : AppCompatActivity(), View.OnClickListener {
    private val mUiHandler: UiHandler = UiHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrip_list)
    }

    /**
     * 检测是否开启无障碍无法
     */
    private fun checkAccessibility() {
        val enableAccessibility = AccessibilityServiceTool.isAccessibilityServiceEnabled(this)
        if (enableAccessibility) {
            bt_accessibility.text = "已启动无障碍服务"
        } else {
            bt_accessibility.text = "去启动无障碍服务"
            val builder = AlertDialog.Builder(this)
            // 设置参数
            builder.setMessage("无障碍服务未开启，去启动无障碍服务？")
                    .setTitle("提示")
                    .setPositiveButton("确定") { dialog, which ->
                        AccessibilityServiceTool.goToAccessibilitySetting()
                    }
            // 创建对话框
            val dialog = builder.create()
            dialog.show()

        }
    }

    override fun onResume() {
        super.onResume()
        checkAccessibility()
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    override fun onClick(v: View?) {
        v?.let {
            when (v.id) {
                // 抢红包
                R.id.bt_red -> {
                    if (canDrawOverlays()) {
                        mUiHandler.toast("请手动打开群聊天")
                        GlobalRedLauncher.launch(this)
                    }
                }
                // 快手
                R.id.bt_kuaishou -> {
                    GlobalKuaiShouLauncher.launch(this)
                }
                // 抖音
                R.id.bt_douyin -> {
                    GlobalDouYinLauncher.launch(this)
                }
                // 启动无障碍
                R.id.bt_accessibility -> {
                    AccessibilityServiceTool.goToAccessibilitySetting()
                }
                // 停止所有服务
                R.id.bt_stop_scrip -> {
                    AutoJs.instance.run { scriptEngineService.stopAllAndToast() }
                }
            }
        }
    }

    /**
     * 是否有悬浮窗权限
     */
    private fun canDrawOverlays(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                mUiHandler.toast("当前无权限，请授权")
                startActivityForResult(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + packageName)), 0)
                return false
            }
        }
        return true
    }


}
