package com.machnv.music

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import com.machnv.control.IControlMusicInterface

class MainActivity : AppCompatActivity() {

    var controlMusicService : IControlMusicInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initConnection()
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            controlMusicService = IControlMusicInterface.Stub.asInterface(p1)
            if (p1 != null) {
                //Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_SHORT).show()
                //val song = controlMusicService?.addSong()
            }
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            //Toast.makeText(this@MainActivity, "onServiceDisconnected", Toast.LENGTH_SHORT).show()
            controlMusicService = null
        }

    }

    private fun initConnection() {
        val intent = Intent(IControlMusicInterface::class.simpleName)
        intent.action = "service.bound"
        intent.`package` = "com.machnv.control"
        bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE)
    }


}