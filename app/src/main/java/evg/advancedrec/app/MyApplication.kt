package evg.advancedrec.app

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication :Application() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)


    }
}