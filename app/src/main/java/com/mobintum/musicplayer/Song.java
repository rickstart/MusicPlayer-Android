package com.mobintum.musicplayer;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

/**
 * Created by Rick on 13/04/15.
 */
public class Song {

    private String title;
    private String artist;
    private String album;
    private String urlSong;
    private String time;
    private Drawable albumImage;

    public Song(String title, String artist, String album, String urlSong, String time, Drawable albumImage) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.urlSong = urlSong;
        this.time = time;
        this.albumImage = albumImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getUrlSong() {
        return urlSong;
    }

    public void setUrlSong(String urlSong) {
        this.urlSong = urlSong;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Drawable getAlbumImage() {
        return albumImage;
    }

    public void setAlbumImage(Drawable albumImage) {
        this.albumImage = albumImage;
    }

    public static ArrayList<Song> getSongs(Context context){

        ArrayList<Song> arraySongs = new ArrayList<Song>();


        arraySongs.add(new Song("Get Lucky", "Daft Punk", "Get Lucky","song_getlucky","5:03",context.getResources().getDrawable(R.mipmap.thumb_get_lucky)));
        arraySongs.add(new Song("Tachas y Perico", "Galatzia", "Unknow","song_tachas","5:03",context.getResources().getDrawable(R.mipmap.thumb_galatzia_tachas)));

        return arraySongs;

    }
}
