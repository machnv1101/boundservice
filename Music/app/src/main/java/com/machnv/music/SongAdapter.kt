package com.machnv.music

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_song.view.*

class SongAdapter : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    private var songList: List<String> = listOf()

    class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)
        return SongViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.itemView.apply {
            tvName.text = songList[position]
            llItemSong.setOnClickListener {
                itemClickListener?.let {
                    it.onItemClickListener(position)
                }
            }
        }

    }

    override fun getItemCount(): Int = songList.size

    fun setSongList(songList: List<String>) {
        this.songList = songList
    }

    private var itemClickListener: ItemClickListener? = null

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    interface ItemClickListener{
        fun onItemClickListener(position: Int)
    }
}