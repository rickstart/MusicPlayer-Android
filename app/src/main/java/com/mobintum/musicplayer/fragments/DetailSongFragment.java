package com.mobintum.musicplayer.fragments;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mobintum.musicplayer.R;
import com.mobintum.musicplayer.models.Song;

import java.util.ArrayList;


public class DetailSongFragment extends Fragment implements View.OnClickListener, Runnable {

    private static final String ARG_PARAM_SONG = "paramSong";
    private int position;

    private Song song;
    private ArrayList<Song> songs;


    private MediaPlayer mPlayer;
    private ImageButton btnPlay, btnForward, btnBackward;
    private ImageView imgThumbDetail;
    private TextView textDetailSong, textDetailArtist, textDetailAlbum, textDetailTime;
    private ProgressBar progressBar;

    private int flag = 0;
    private Thread thread;

    public static DetailSongFragment newInstance(int position) {
        DetailSongFragment fragment = new DetailSongFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM_SONG, position);

        fragment.setArguments(args);
        return fragment;
    }

    public DetailSongFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.position = getArguments().getInt(ARG_PARAM_SONG);
            this.songs = Song.getSongs(getActivity());
            this.song = songs.get(position);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.fragment_detail_song, container, false);


        btnPlay = (ImageButton) viewRoot.findViewById(R.id.btnPlayF);
        btnBackward = (ImageButton) viewRoot.findViewById(R.id.btnBackwardF);
        btnForward = (ImageButton) viewRoot.findViewById(R.id.btnForwardF);

        imgThumbDetail = (ImageView) viewRoot.findViewById(R.id.imgThumbDetailF);
        textDetailSong = (TextView) viewRoot.findViewById(R.id.textDetailSongF);
        textDetailArtist = (TextView) viewRoot.findViewById(R.id.textDetailArtistF);
        textDetailAlbum = (TextView) viewRoot.findViewById(R.id.textDetailAlbumF);
        textDetailTime = (TextView) viewRoot.findViewById(R.id.textDetailTimeF);
        progressBar = (ProgressBar) viewRoot.findViewById(R.id.progressBarF);


        btnPlay.setOnClickListener(this);
        btnBackward.setOnClickListener(this);
        btnForward.setOnClickListener(this);

        thread= new Thread(this);
        loadData(song);

        return viewRoot;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPlayer.stop();
    }

    public void loadData(Song song){

        imgThumbDetail.setImageDrawable(song.getAlbumImage());
        textDetailSong.setText(song.getTitle());
        textDetailArtist.setText(song.getArtist());
        textDetailAlbum.setText(song.getAlbum());

        mPlayer = MediaPlayer.create(getActivity(),  getResources().getIdentifier("raw/"+song.getUrlSong(),
                "raw", getActivity().getPackageName()));



        int seconds = (int) (mPlayer.getDuration() / 1000) % 60 ;
        int minutes = (int) ((mPlayer.getDuration() / (1000*60)) % 60);
        int hours   = (int) ((mPlayer.getDuration() / (1000*60*60)) % 24);
        if(hours>0) {
            textDetailTime.setText("" + hours + ":" + minutes + ":" + seconds);
        }else{
            textDetailTime.setText(""+minutes + ":" + seconds);
        }
        progressBar.setVisibility(ProgressBar.VISIBLE);
        progressBar.setProgress(0);
        progressBar.setMax(mPlayer.getDuration());
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btnPlayF:

                if(flag==0){

                    mPlayer.start();
                    flag=1;
                    btnPlay.setImageResource(R.mipmap.btn_pause);


                    Log.e("THREAD", "" + thread.getState());
                    if(thread.getState()!= Thread.State.TIMED_WAITING)
                        thread.start();

                }else{
                    mPlayer.pause();
                    btnPlay.setImageResource(R.mipmap.btn_play);
                    flag=0;

                }

                break;

            case R.id.btnBackwardF:
                mPlayer.stop();
                position = ((position-1) >= 0)?position -1: position;

                loadData(songs.get(position));

                mPlayer.start();
                if(thread.getState()!= Thread.State.TIMED_WAITING)
                    thread.start();

                break;
            case R.id.btnForwardF:
                mPlayer.stop();
                position=(position<songs.size()-1)?position+1:position;
                loadData(songs.get(position));
                mPlayer.start();
                if(thread.getState()!= Thread.State.TIMED_WAITING)
                    thread.start();
                break;
        }

    }

    @Override
    public void run() {
        int currentPosition= 0;
        int total = mPlayer.getDuration();
        while (mPlayer!=null && currentPosition<total) {
            try {
                Thread.sleep(1000);
                currentPosition= mPlayer.getCurrentPosition();
            } catch (InterruptedException e) {
                return;
            } catch (Exception e) {
                return;
            }
            progressBar.setProgress(currentPosition);
        }

    }
}
