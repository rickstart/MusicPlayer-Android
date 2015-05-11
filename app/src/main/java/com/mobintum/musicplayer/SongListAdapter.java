package com.mobintum.musicplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Rick on 13/04/15.
 */
public class SongListAdapter extends ArrayAdapter {

    private Context context;
    private ArrayList<Song> songs;


    public SongListAdapter(Context context,int resource, ArrayList<Song> songs) {
        super(context, resource,songs);
        this.context = context;
        this.songs = songs;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.item_listview_song, parent, false);
        ViewHolder holder = new ViewHolder();

        holder.textSong = (TextView) rowView.findViewById(R.id.textSong);
        holder.textArtist = (TextView) rowView.findViewById(R.id.textArtist);
        holder.textAlbum = (TextView) rowView.findViewById(R.id.textAlbum);
        holder.textTime = (TextView) rowView.findViewById(R.id.textTime);
        holder.imageThumbSong = (ImageView) rowView.findViewById(R.id.imageThumbAlbum);


        Song song = songs.get(position);

        holder.textSong.setText(song.getTitle());
        holder.textArtist.setText(song.getArtist());
        holder.textAlbum.setText(song.getAlbum());
        holder.textTime.setText(song.getTime());
        holder.imageThumbSong.setImageDrawable(song.getAlbumImage());

        return rowView;
    }

    static class ViewHolder{
        TextView textSong;
        TextView textAlbum;
        TextView textArtist;
        TextView textTime;
        ImageView imageThumbSong;
    }

}
