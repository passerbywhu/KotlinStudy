package com.passerbywhu.kotlinstudy.ipc.aidl

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.RemoteCallbackList
import com.passerbywhu.kotlinstudy.aidl.PlayEventInterface
import com.passerbywhu.kotlinstudy.aidl.BackgroundPlayerInterface
import com.passerbywhu.kotlinstudy.ipc.TrackInfo
import java.util.*
import kotlin.concurrent.timer

class AIDLPlayService : Service() {
    val callbacks: RemoteCallbackList<PlayEventInterface> = RemoteCallbackList()
    val outPlayList = ArrayList<TrackInfo>()
    var currentIndex = 0
    var changeSongTimer : Timer? = null

    override fun onCreate() {
        super.onCreate()
        changeSongTimer = timer("changeSong", false, 0, 1000) {
            if (outPlayList.isNotEmpty()) {
                currentIndex = (currentIndex + 1) % outPlayList.size
                val callbackNum = callbacks.beginBroadcast()
                for(i in 0 until callbackNum) {
                    callbacks.getBroadcastItem(i).songChanged(outPlayList[currentIndex].name)
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

            override fun getCurrentSong(): TrackInfo {
                return if (outPlayList.isEmpty()) TrackInfo("No information") else outPlayList[currentIndex]
            }

            override fun playNext() : TrackInfo? {
                return if (!outPlayList.isEmpty()) {
                    currentIndex = (currentIndex + 1) % outPlayList.size
                    outPlayList[currentIndex]
                } else {
                    null
                }

            }

            override fun playPre() : TrackInfo? {
                return if (!outPlayList.isEmpty()) {
                    currentIndex = (currentIndex - 1 + outPlayList.size) % outPlayList.size
                    outPlayList[currentIndex]
                } else null
            }

            override fun setPlayList(playList_: MutableList<TrackInfo>?) {
                outPlayList.clear()
                outPlayList.addAll(playList_ as List<TrackInfo>)
            }

            override fun getPlayList(): MutableList<TrackInfo> {
                return outPlayList
            }

        }
    }
}