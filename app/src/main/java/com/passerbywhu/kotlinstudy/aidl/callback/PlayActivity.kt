package com.passerbywhu.kotlinstudy.aidl.callback

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
import kotlinx.android.synthetic.main.play_activity.*

class PlayActivity : AppCompatActivity() {
    val preparedPlayList = listOf("song1", "song2", "song3", "song4")
    var playServiceProxy : BackgroundPlayerInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.play_activity)
        val intent = Intent(this, BackgroundPlayService::class.java)
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
            }

        }, Service.BIND_AUTO_CREATE)
        prev.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                playServiceProxy?.playPre()
                show.text = playServiceProxy?.currentSong?:"No Information"
            }
        })
        next.setOnClickListener{
            playServiceProxy?.playNext()
            show.text = playServiceProxy?.currentSong?:"No Information"
        }
        current.setOnClickListener{
            show.text = playServiceProxy?.currentSong?:"No Information"
        }
    }
}