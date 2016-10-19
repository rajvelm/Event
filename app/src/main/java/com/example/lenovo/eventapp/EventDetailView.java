package com.example.lenovo.eventapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class EventDetailView extends AppCompatActivity implements YouTubePlayer.OnInitializedListener
{

    public static final String API_KEY = "AIzaSyAEwtcEDwFjnA7EDDIPvGAJKLVRegYZGt8";
    public static final String PLAYLIST_ID = "PLdgcRE6FcTGhFk5z19Jaqdu3XgP_FTrMF";
    private static final int RECOVERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail_view);
        //TextView eventID=(TextView)findViewById(R.id.txtView_eventId);
        TextView eventName=(TextView)findViewById(R.id.txtView_eventName);
        TextView eventDesc=(TextView)findViewById(R.id.txtView_eventDesc);
        TextView eventDate=(TextView)findViewById(R.id.txtView_eventDate);
        TextView eventCreatedBy=(TextView)findViewById(R.id.txtView_eventCreatedBy);
        //TextView eventUrl=(TextView)findViewById(R.id.txtView_eventUrl);
        RatingBar eventRating=(RatingBar)findViewById(R.id.detailpg_eventRating);
        Button btnEventVideo = (Button)findViewById(R.id.btn_eventVideo);

        YouTubePlayerView youtubeView = (YouTubePlayerView)findViewById(R.id.youtube_videos);
        youtubeView.initialize(API_KEY,this);

        int event_id;
        float event_rating;
        String event_name,event_url,event_created,event_desc,event_date;
        Bundle bundle=getIntent().getExtras();

        event_id=bundle.getInt("event_id");
        event_name=bundle.getString("event_name");
        event_created=bundle.getString("event_created");
        event_url=bundle.getString("event_url");
        event_desc=bundle.getString("event_desc");
        event_date=bundle.getString("event_date");
        event_rating=bundle.getFloat("event_rating");

        //eventID.setText("Event ID:"+ event_id);
        eventName.setText("Event Name:"+ event_name);
        eventCreatedBy.setText("CreatedBy:"+ event_created);
        eventDesc.setText("Event Desc:"+ event_desc);
        eventDate.setText("Event Date:"+ event_date);
        //eventUrl.setText("Event URL:"+ event_url);
        eventRating.setRating(event_rating);
        LayerDrawable stars = (LayerDrawable)eventRating.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.parseColor("#800000"), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(Color.parseColor("#8e8b8b"), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(0).setColorFilter(Color.parseColor("#8e8b8b"), PorterDuff.Mode.SRC_ATOP);


        btnEventVideo.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        /*Intent videoIndent = new Intent(EventDetailView.this,EventVideo.class);
                        startActivity(videoIndent);*/
                    }
                }
        );
    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {

        youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
        youTubePlayer.setPlaybackEventListener(playbackEventListener);

        if(!wasRestored)
        {
            youTubePlayer.cuePlaylist(PLAYLIST_ID);
        }
    }

    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onPlaying() {
            showMessage("Playing");
        }

        @Override
        public void onPaused() {
            showMessage("Paused");
        }

        @Override
        public void onStopped() {
            showMessage("Stopped");
        }

        @Override
        public void onBuffering(boolean b) {

        }

        @Override
        public void onSeekTo(int i) {

        }
    };

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {

        }

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onVideoStarted() {

        }

        @Override
        public void onVideoEnded() {

        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }
    };


    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format(getString(R.string.player_error), errorReason.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }
}
