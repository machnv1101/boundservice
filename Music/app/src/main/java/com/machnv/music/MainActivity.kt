package com.machnv.music

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.machnv.control.IControlMusicInterface
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SongAdapter.ItemClickListener {

    var controlMusicService: IControlMusicInterface? = null
    private val songAdapter = SongAdapter()
    private var currentSong = listSong[0]
    private var isConnected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initConnection()

        songAdapter.setSongList(listSong)
        rvSong.layoutManager = LinearLayoutManager(this)
        rvSong.setHasFixedSize(true)
        rvSong.adapter = songAdapter

        songAdapter.setItemClickListener(this)

        btnPlay.setOnClickListener {
            val text = controlMusicService?.let {
                it.playSong(currentSong)
            }
            text?.let {
                Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
            }
        }

        btnNext.setOnClickListener {
            controlMusicService?.let {
                it.next(currentSong)
            }
        }

        btnPrev.setOnClickListener {
            controlMusicService?.let {
                it.prev(currentSong)
            }
        }
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            Toast.makeText(this@MainActivity, "Client Connect Success", Toast.LENGTH_SHORT).show()
            controlMusicService = IControlMusicInterface.Stub.asInterface(p1)
            if (p1 != null) {
                isConnected = true
                controlMusicService?.playSong(currentSong)
            }
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            controlMusicService = null
            isConnected = false
        }

    }

    private fun initConnection() {
        val intent = Intent(IControlMusicInterface::class.simpleName)
        intent.action = "service.bound"
        intent.`package` = "com.machnv.control"
        bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE)
    }

    companion object {
        val listSong = listOf("Song 1", "Song 2", "Song 3", "Song 4", "Song 5", "Song 6", "Song 7", "Song 8", "Song 9")
    }

    override fun onItemClickListener(position: Int) {
        currentSong = listSong[position]
        Toast.makeText(this, "On click $currentSong", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        isConnected = false
        controlMusicService = null
        super.onDestroy()
    }

}