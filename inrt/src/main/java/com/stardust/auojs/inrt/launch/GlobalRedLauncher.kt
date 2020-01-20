package com.stardust.auojs.inrt.launch

import android.annotation.SuppressLint
import com.stardust.app.GlobalAppContext

/**
 * Created by Stardust on 2018/3/21.
 */

@SuppressLint("StaticFieldLeak")
object GlobalRedLauncher : AssetsProjectLauncher("project", GlobalAppContext.get())

@SuppressLint("StaticFieldLeak")
object GlobalKuaiShouLauncher : AssetsProjectLauncher("kuaishou", GlobalAppContext.get())

@SuppressLint("StaticFieldLeak")
object GlobalDouYinLauncher : AssetsProjectLauncher("douyin", GlobalAppContext.get())