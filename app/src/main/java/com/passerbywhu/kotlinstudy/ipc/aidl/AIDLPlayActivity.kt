package com.passerbywhu.kotlinstudy.ipc.aidl

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.passerbywhu.kotlinstudy.R
import com.passerbywhu.kotlinstudy.aidl.BackgroundPlayerInterface
import com.passerbywhu.kotlinstudy.aidl.PlayEventInterface
import com.passerbywhu.kotlinstudy.ipc.TrackInfo
import kotlinx.android.synthetic.main.play_activity.*

class AIDLPlayActivity : AppCompatActivity() {
    val preparedPlayList = listOf(TrackInfo("song1"), TrackInfo("song2"), TrackInfo("song3"), TrackInfo("song4"))
    var playServiceProxy : BackgroundPlayerInterface? = null
    val deathRecipient = object : IBinder.DeathRecipient {
        override fun binderDied() {
            playServiceProxy?.asBinder()?.unlinkToDeath(this, 0)
            playServiceProxy = null
            //重新绑定Service
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.play_activity)
        val intent = Intent(this, AIDLPlayService::class.java)
        bindService(intent, object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {
            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                playServiceProxy = BackgroundPlayerInterface.Stub.asInterface(service)
                playServiceProxy?.playList = preparedPlayList
                playServiceProxy?.registerCallback(object : PlayEventInterface.Stub() {
                    override fun songChanged(song: String?) {
                        show.text = song?:"No Information"
                    }

                })
                service?.linkToDeath(deathRecipient, 0)
            }

        }, Service.BIND_AUTO_CREATE)
        prev.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                playServiceProxy?.playPre()
                show.text = playServiceProxy?.currentSong?.name?:"No Information"
            }
        })
        next.setOnClickListener{
            playServiceProxy?.playNext()
            show.text = playServiceProxy?.currentSong?.name?:"No Information"
        }
        current.setOnClickListener{
            show.text = playServiceProxy?.currentSong?.name?:"No Information"
        }
    }
}