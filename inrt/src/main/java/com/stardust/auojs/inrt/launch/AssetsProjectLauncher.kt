package com.stardust.auojs.inrt.launch

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.widget.Toast
import com.stardust.auojs.inrt.BuildConfig
import com.stardust.auojs.inrt.autojs.AutoJs
import com.stardust.auojs.inrt.util.TimestampUtil
import com.stardust.autojs.engine.encryption.ScriptEncryption
import com.stardust.autojs.execution.ExecutionConfig
import com.stardust.autojs.execution.ScriptExecution
import com.stardust.autojs.project.ProjectConfig
import com.stardust.autojs.script.JavaScriptFileSource
import com.stardust.autojs.script.JavaScriptSource
import com.stardust.pio.PFiles
import com.stardust.pio.UncheckedIOException
import com.stardust.util.MD5
import java.io.File
import java.io.IOException

/**
 * Created by Stardust on 2018/1/24.
 */

open class AssetsProjectLauncher(private val mAssetsProjectDir: String, private val mActivity: Context) {
    private val mProjectDir: String = File(mActivity.filesDir, "$mAssetsProjectDir/").path
    private val mProjectConfig: ProjectConfig = ProjectConfig.fromAssets(mActivity, ProjectConfig.configFileOfDir(mAssetsProjectDir))
    private val mMainScriptFile: File = File(mProjectDir, mProjectConfig.mainScriptFile)
    private val mHandler: Handler = Handler(Looper.getMainLooper())
    private var mScriptExecution: ScriptExecution? = null
    private val mTimestampUtil: TimestampUtil = TimestampUtil()

    init {
        prepare()
    }

    fun launch(activity: Activity) {
        if (isOverTime()) {
            Toast.makeText(activity, "已经过期，请联系开发者者购买永久免费版本！", Toast.LENGTH_LONG).show()
        } else {
            runScript(activity)
        }

    }

    /**
     * 判断是否过期
     */
    private fun isOverTime(): Boolean {
        val overTime = "2020-01-23 23:59:00"
        val overStamp = mTimestampUtil.transToTimeStamp(overTime)
        val currentTimeMillis = System.currentTimeMillis()
        return currentTimeMillis > overStamp
    }


    private fun runScript(activity: Activity?) {
        if (mScriptExecution != null && mScriptExecution!!.engine != null &&
                !mScriptExecution!!.engine.isDestroyed) {
            return
        }
        try {
            val source = JavaScriptFileSource("main", mMainScriptFile)
            val config = ExecutionConfig(workingDirectory = mProjectDir)
            if (source.executionMode and JavaScriptSource.EXECUTION_MODE_UI != 0) {
                config.intentFlags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_TASK_ON_HOME
            }
            mScriptExecution = AutoJs.instance.scriptEngineService.execute(source, config)
        } catch (e: Exception) {
            AutoJs.instance.globalConsole.error(e)
        }

    }

    private fun prepare() {
        val projectConfigPath = PFiles.join(mProjectDir, ProjectConfig.CONFIG_FILE_NAME)
        val projectConfig = ProjectConfig.fromFile(projectConfigPath)
        if (!BuildConfig.DEBUG && projectConfig != null &&
                TextUtils.equals(projectConfig.buildInfo.buildId, mProjectConfig.buildInfo.buildId)) {
            initKey(projectConfig)
            return
        }
        initKey(mProjectConfig)
        PFiles.deleteRecursively(File(mProjectDir))
        try {
            PFiles.copyAssetDir(mActivity.assets, mAssetsProjectDir, mProjectDir, null)
        } catch (e: IOException) {
            throw UncheckedIOException(e)
        }
    }

    private fun initKey(projectConfig: ProjectConfig) {
        val key = MD5.md5(projectConfig.packageName + projectConfig.versionName + projectConfig.mainScriptFile)
        val vec = MD5.md5(projectConfig.buildInfo.buildId + projectConfig.name).substring(0, 16)
        try {
            val fieldKey = ScriptEncryption::class.java.getDeclaredField("mKey")
            fieldKey.isAccessible = true
            fieldKey.set(null, key)
            val fieldVector = ScriptEncryption::class.java.getDeclaredField("mInitVector")
            fieldVector.isAccessible = true
            fieldVector.set(null, vec)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}
