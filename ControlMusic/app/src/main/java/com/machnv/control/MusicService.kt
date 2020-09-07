package com.machnv.control

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast

class MusicService: Service() {

    override fun onBind(p0: Intent?): IBinder? {
        return mBinder
    }

    private val mBinder = object : IControlMusicInterface.Stub() {
        override fun playSong(name: String?): String {
            return "Play $name success!!"
        }

        override fun next(name: String?) {

        }

        override fun prev(name: String?) {

        }

    }
}