package com.passerbywhu.kotlinstudy.ipc.messenger

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger

class MessengerPlayService : Service() {
    var clientMessenger : Messenger? = null

    companion object {
        const val GET_CURRENT_SONG = 1
        const val PLAY_NEXT = 2
        const val PLAY_PREV = 3
    }

    inner class InComingHandler : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            when (msg?.what) {
                GET_CURRENT_SONG -> {
                    clientMessenger = msg.replyTo
                }
                PLAY_NEXT -> {
                    clientMessenger = msg.replyTo
                }
                PLAY_PREV -> {
                    clientMessenger = msg.replyTo
                }
                else -> {}
            }
        }
    }

    val serviceMessenger = Messenger(InComingHandler())

    override fun onBind(intent: Intent?): IBinder {
        return serviceMessenger.binder
    }
}