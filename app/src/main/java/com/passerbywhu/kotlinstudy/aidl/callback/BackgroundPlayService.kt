package com.passerbywhu.kotlinstudy.aidl.callback

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.RemoteCallbackList
import com.passerbywhu.kotlinstudy.aidl.PlayEventInterface
import com.passerbywhu.kotlinstudy.aidl.BackgroundPlayerInterface
import java.util.*
import kotlin.concurrent.timer

class BackgroundPlayService : Service() {
    val callbacks: RemoteCallbackList<PlayEventInterface> = RemoteCallbackList()
    val outPlayList = ArrayList<String>()
    var currentIndex = 0
    var changeSongTimer : Timer? = null

    override fun onCreate() {
        super.onCreate()
        changeSongTimer = timer("changeSong", false, 0, 1000) {
            if (outPlayList.isNotEmpty()) {
                currentIndex = (currentIndex + 1) % outPlayList.size
                val callbackNum = callbacks.beginBroadcast()
                for(i in 0 until callbackNum) {
                    callbacks.getBroadcastItem(i).songChanged(outPlayList[currentIndex])
                }
                callbacks.finishBroadcast()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        callbacks.kill()
    }

    override fun onBind(intent: Intent?): IBinder {
        return object : BackgroundPlayerInterface.Stub() {
            override fun registerCallback(listener: PlayEventInterface?) {
                if (listener != null) {
                    callbacks.register(listener)
                }
            }

            override fun unregisterCallback(listener: PlayEventInterface?) {
                if (listener != null) {
                    callbacks.unregister(listener)
                }
            }

            override fun getCurrentSong(): String {
                return if (outPlayList.isEmpty()) "No information" else outPlayList[currentIndex]
            }

            override fun playNext() {
                if (!outPlayList.isEmpty()) {
                    currentIndex = (currentIndex + 1) % outPlayList.size
                }
            }

            override fun playPre() {
                if (!outPlayList.isEmpty()) {
                    currentIndex = (currentIndex - 1 + outPlayList.size) % outPlayList.size
                }
            }

            override fun setPlayList(playList_: MutableList<String>?) {
                outPlayList.clear()
                outPlayList.addAll(playList_ as List<String>)
            }

            override fun getPlayList(): MutableList<String> {
                return outPlayList
            }

        }
    }
}