package com.jpapps.badmetronome;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	static final int TABLA_SNAP_BYTES = 17638;
	
	SeekBar speedBar, accuracyBar;
	TextView speedText, accuracyText;
	Button playbackButton;
	Metronome metronome;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		speedBar = (SeekBar) this.findViewById(R.id.speedBar);
		accuracyBar = (SeekBar) this.findViewById(R.id.accuracyBar);
		playbackButton = (Button) this.findViewById(R.id.button1);
		speedText = (TextView) this.findViewById(R.id.bpm);
		accuracyText = (TextView) this.findViewById(R.id.accuracy);
		
		playbackButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View view) {
				metronome.togglePlayback();
				if(metronome.isPlaying()) {
					getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
				} else {
					getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
				}
			}
		});
		
		speedBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				metronome.setSpeed(progress+1);
				speedText.setText(String.valueOf(progress+1));
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				
			}
		});
		
		accuracyBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				metronome.setAccuracy(progress);
				accuracyText.setText(String.valueOf(progress));
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				
			}
		});
		
		//MediaPlayer player = MediaPlayer.create(this, R.raw.tablasnap);
		//metronome = new Metronome(speedBar.getProgress()+1, accuracyBar.getProgress(), player);
		InputStream is = this.getResources().openRawResource(R.raw.tablasnap);
		BufferedInputStream bis = new BufferedInputStream(is, 8000);
		DataInputStream dis = new DataInputStream(bis);
		
		byte[] sound = new byte[TABLA_SNAP_BYTES];
		try {
			dis.read(sound);
		} catch (IOException e) {
			Log.e("BadMetronome", "Error while reading in sound file.");
		}
		
		metronome = new Metronome(speedBar.getProgress()+1, accuracyBar.getProgress(), sound);
		speedText.setText(String.valueOf(speedBar.getProgress()+1));
		accuracyText.setText(String.valueOf(accuracyBar.getProgress()));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if(metronome != null) metronome.stop();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		if(metronome != null) metronome.stop();
	}

}
