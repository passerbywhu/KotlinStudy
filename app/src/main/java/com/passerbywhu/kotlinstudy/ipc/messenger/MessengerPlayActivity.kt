package com.passerbywhu.kotlinstudy.ipc.messenger

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.support.v7.app.AppCompatActivity
import com.passerbywhu.kotlinstudy.R

class MessengerPlayActivity : AppCompatActivity() {
    var serviceMessenger : Messenger? = null
    var conn : ServiceConnection? = null
    var bound = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.play_activity)
        conn = object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {
                serviceMessenger = null
                bound = false
            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                serviceMessenger = Messenger(service)
                bound = true
                service?.linkToDeath(object : IBinder.DeathRecipient {
                    override fun binderDied() {
                        serviceMessenger = null
                        bound = false
                        //重新绑定
                    }

                }, 0)
            }

        }

        bindService(Intent(this, MessengerPlayService::class.java), conn, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (bound) {
            unbindService(conn)
            bound = false
        }
    }

    fun getCurrentSong() {
        val message = Message.obtain(null, MessengerPlayService.GET_CURRENT_SONG, 0, 0)
        try {
            serviceMessenger?.send(message)
        } catch (e : RemoteException) {
            e.printStackTrace()
        }
    }


}