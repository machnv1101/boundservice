package com.machnv.control;
import com.machnv.control.Song;

interface IControlMusicInterface {

   String playSong(String name);

   void next(String name);

   void prev(String name);

   Song createSong(String name, String singer);
}
