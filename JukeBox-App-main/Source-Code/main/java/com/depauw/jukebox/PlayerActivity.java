package com.depauw.jukebox;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import com.depauw.jukebox.databinding.ActivityPlayerBinding;

public class PlayerActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    // Static Final Variables
    public static final int COLOR_BAR_MAXIMUM = 255;
    public static final int SONG_BAR_MAXIMUM = 100;

    // Member Variables
    private ActivityPlayerBinding binding;
    private MediaPlayer mediaPlayer;
    private float track1Rating;
    private float track2Rating;
    private float track3Rating;

    // Global Variables
    int redProgress;
    int greenProgress;
    int blueProgress;
    int playingAudioID;
    int rating1Counter;
    int rating2Counter;
    int rating3Counter;

    // Called in onCreate to SeekBar Range - Done
    public void setRange(){
        binding.seekbarRed.setMax(COLOR_BAR_MAXIMUM);
        binding.seekbarGreen.setMax(COLOR_BAR_MAXIMUM);
        binding.seekbarBlue.setMax(COLOR_BAR_MAXIMUM);
    }

    // Called when SeekBar Slided - Done
    public void changeScreenColor(){
        binding.constraintlayout.setBackgroundColor(Color.rgb(redProgress,greenProgress,blueProgress));
    }

    // Called when app launched or button clicked - Done
    public void playSong(int audioID){
        if(playingAudioID!=0) mediaPlayer.stop();
        mediaPlayer = mediaPlayer.create(this,audioID);
        mediaPlayer.start();
    }

    // Called when Song Seek Bar slided - Done
    public void seekSong(int seekProgress){
        int seekLength = (mediaPlayer.getDuration()*seekProgress)/SONG_BAR_MAXIMUM;
        mediaPlayer.seekTo(seekLength);
    }

    // Setting Listeners + Playing First Song
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setRange();
        onCheckedChanged(binding.radiogroupSongs,binding.radiogroupSongs.getCheckedRadioButtonId());

        binding.seekbarRed.setOnSeekBarChangeListener(this);
        binding.seekbarGreen.setOnSeekBarChangeListener(this);
        binding.seekbarBlue.setOnSeekBarChangeListener(this);
        binding.seekbarSongPosition.setOnSeekBarChangeListener(this);
        binding.buttonCastVote.setOnClickListener(this);

        binding.radiogroupSongs.setOnCheckedChangeListener(this);
    }

    // Sliding SeekBars - Done
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
    switch (seekBar.getId()){
        case(R.id.seekbar_red):{
            if(fromUser){
                redProgress = progress;
                binding.seekbarRed.setProgress(progress,true);
                binding.textviewRed.setText(String.valueOf(progress));
                changeScreenColor();
            }
            break;
        }
        case (R.id.seekbar_green):{
            if(fromUser){
                greenProgress = progress;
                binding.seekbarGreen.setProgress(progress,true);
                binding.textviewGreen.setText(String.valueOf(progress));
                changeScreenColor();
            }
            break;
        }
        case (R.id.seekbar_blue):{
            if(fromUser){
                blueProgress = progress;
                binding.seekbarBlue.setProgress(progress,true);
                binding.textviewBlue.setText(String.valueOf(progress));
                changeScreenColor();
            }
            break;
        }
        case (R.id.seekbar_song_position): {
            if (fromUser) {
                seekSong(progress);
            }
            break;
        }
    }
    }

    // Do not need to be implemented
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) { }
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) { }

    // Clicking Radio Group's Buttons - Done
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedID){
    switch (checkedID){
        case(R.id.radio_song1):{
            playSong(R.raw.track1);
            binding.imageviewAlbumCover.setImageDrawable(getResources().getDrawable(R.drawable.track1));
            break;
        }
        case(R.id.radio_song2):{
            playSong(R.raw.track2);
            binding.imageviewAlbumCover.setImageDrawable(getResources().getDrawable(R.drawable.track2));
            break;
        }
        case(R.id.radio_song3):{
            playSong(R.raw.track3);
            binding.imageviewAlbumCover.setImageDrawable(getResources().getDrawable(R.drawable.track3));
            break;
        }
    }
        playingAudioID = checkedID;
        binding.seekbarSongPosition.setProgress(0);
    }

    // Clicking Vote Button - Done
    @Override
    public void onClick(View view) {
        float stars = binding.ratingbarVoterRating.getRating();
        switch (view.getId()){
            case (R.id.button_cast_vote):{
                if(playingAudioID==binding.radioSong1.getId()){
                    rating1Counter++;
                    track1Rating +=  stars;
                    binding.progressbarAverageRating1.setProgress(Math.round(track1Rating/rating1Counter));
                    binding.textviewNumVotes1.setText(String.valueOf(rating1Counter));
                    binding.ratingbarVoterRating.setRating(0);
                    break;
                }
                else if(playingAudioID==binding.radioSong2.getId()){
                    rating2Counter++;
                    track2Rating +=  stars;
                    binding.progressbarAverageRating2.setProgress(Math.round(track2Rating/rating2Counter));
                    binding.textviewNumVotes2.setText(String.valueOf(rating2Counter));
                    binding.ratingbarVoterRating.setRating(0);
                    break;
                }
                else if(playingAudioID==binding.radioSong3.getId()) {
                    rating3Counter++;
                    track3Rating +=  stars;
                    binding.progressbarAverageRating3.setProgress(Math.round(track3Rating/rating3Counter));
                    binding.textviewNumVotes3.setText(String.valueOf(rating3Counter));
                    binding.ratingbarVoterRating.setRating(0);
                    break;
                }
                break;
            }
        }
    }
}