package com.mobintum.musicplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;


public class SongDetailActivity extends ActionBarActivity implements View.OnClickListener, Runnable{

    MediaPlayer mPlayer;
    ImageButton btnPlay, btnForward, btnBackward;
    ImageView imgThumbDetail;
    TextView textDetailSong, textDetailArtist, textDetailAlbum, textDetailTime;
    ProgressBar progressBar;

    Song song;
    ArrayList<Song> songs;
    Intent intent ;
    int position;
    int flag = 0;

    Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_detail);

        intent= getIntent();
        position = (intent.getExtras()!=null)? intent.getExtras().getInt("ID"): 0 ;

        songs = Song.getSongs(getApplicationContext());
        song = songs.get(position);

        btnPlay = (ImageButton) findViewById(R.id.btnPlay);
        btnBackward = (ImageButton) findViewById(R.id.btnBackward);
        btnForward = (ImageButton) findViewById(R.id.btnForward);

        imgThumbDetail = (ImageView) findViewById(R.id.imgThumbDetail);
        textDetailSong = (TextView) findViewById(R.id.textDetailSong);
        textDetailArtist = (TextView) findViewById(R.id.textDetailArtist);
        textDetailAlbum = (TextView) findViewById(R.id.textDetailAlbum);
        textDetailTime = (TextView) findViewById(R.id.textDetailTime);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        btnPlay.setOnClickListener(this);
        btnBackward.setOnClickListener(this);
        btnForward.setOnClickListener(this);

        thread= new Thread(this);
        loadData(song);
        //song = Song.getSongs(getApplicationContext()).get()



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_song_detail, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onDestroy() {

        mPlayer.stop();
        super.onDestroy();

    }


    public void loadData(Song song){

        imgThumbDetail.setImageDrawable(song.getAlbumImage());
        textDetailSong.setText(song.getTitle());
        textDetailArtist.setText(song.getArtist());
        textDetailAlbum.setText(song.getAlbum());

        mPlayer = MediaPlayer.create(SongDetailActivity.this,  getResources().getIdentifier("raw/"+song.getUrlSong(),
                "raw", getPackageName()));



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

            case R.id.btnPlay:

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

            case R.id.btnBackward:
                mPlayer.stop();
                position = ((position-1) >= 0)?position -1: position;

                loadData(songs.get(position));

                mPlayer.start();
                if(thread.getState()!= Thread.State.TIMED_WAITING)
                    thread.start();

                break;
            case R.id.btnForward:
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
