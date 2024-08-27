package com.jaehyeon.basic.coroutinesecrets

import android.app.Service
import android.content.Intent
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

/**
 * Created by jaehyeon.
 * Date: 2024. 8. 27.
 */

class MyService: Service() {

    /**
     * Coroutine Scope 에 SupervisorJob 에 합쳐
     * lifecycle 에 맞게끔 Scope 를 구성.
     * 참고 : LifecycleOwner.lifecycleScope
     * 참고 : Lifecycle.coroutineScope
     */

//    private val serviceScope = CoroutineScope(Dispatchers.Default)
    private val serviceScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

}