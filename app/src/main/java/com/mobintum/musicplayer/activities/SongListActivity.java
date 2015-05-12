package com.mobintum.musicplayer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mobintum.musicplayer.R;
import com.mobintum.musicplayer.adapters.SongListAdapter;
import com.mobintum.musicplayer.models.Song;

import java.util.ArrayList;


public class SongListActivity extends ActionBarActivity {

    ListView listViewSongs;
    SongListAdapter adapter;
    ArrayList<Song> songs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);

        songs = Song.getSongs(getApplicationContext());

        listViewSongs = (ListView) findViewById(R.id.listViewSongs);
        adapter = new SongListAdapter(getApplicationContext(),R.layout.item_listview_song, songs);
        listViewSongs.setAdapter(adapter);

        listViewSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), SongDetailActivity.class);
                intent.putExtra("ID", position);
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_song_list, menu);
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
}
