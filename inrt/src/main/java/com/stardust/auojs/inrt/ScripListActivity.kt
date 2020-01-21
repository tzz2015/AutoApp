package com.stardust.auojs.inrt

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.stardust.auojs.inrt.autojs.AccessibilityServiceTool
import com.stardust.auojs.inrt.autojs.AutoJs
import com.stardust.auojs.inrt.launch.GlobalDouYinLauncher
import com.stardust.auojs.inrt.launch.GlobalKuaiShouLauncher
import com.stardust.auojs.inrt.launch.GlobalNotificationLuncher
import com.stardust.auojs.inrt.launch.GlobalRedLauncher
import com.stardust.util.UiHandler
import kotlinx.android.synthetic.main.activity_scrip_list.*


class ScripListActivity : AppCompatActivity(), View.OnClickListener {
    private val mUiHandler: UiHandler = UiHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrip_list)
        initView()
    }

    private fun initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (notificationListenerEnable()) {
                bt_notification.visibility = View.GONE
            } else {
                bt_notification.visibility = View.VISIBLE
            }
        }

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
                        GlobalNotificationLuncher.launch(this)
                        GlobalRedLauncher.launch(this)
                    } else {
                        Log.e("", "")
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
                // 开启通知栏
                R.id.bt_notification -> {
                    gotoNotificationAccessSetting(this)
                }
                else -> {
                    Log.e("", "")
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


    private fun notificationListenerEnable(): Boolean {
        var enable = false
        val packageName = packageName
        val flat = Settings.Secure.getString(contentResolver, "enabled_notification_listeners")
        if (flat != null) {
            enable = flat.contains(packageName)
        }
        return enable
    }


    /**
     * 开启通知栏
     */
    private fun gotoNotificationAccessSetting(context: Context): Boolean {
        try {
            val intent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
            return true
        } catch (e: ActivityNotFoundException) {
            try {
                val intent = Intent()
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                val cn = ComponentName("com.android.settings", "com.android.settings.Settings.NotificationAccessSettingsActivity")
                intent.setComponent(cn)
                intent.putExtra(":settings:show_fragment", "NotificationAccessSettings")
                context.startActivity(intent)
                return true
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
            return false
        }
    }


}
