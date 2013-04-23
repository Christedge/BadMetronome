package com.jpapps.badmetronome;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class MainActivity extends Activity {
	
	SeekBar speedBar, accuracyBar;
	Button playbackButton;
	Metronome metronome;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		speedBar = (SeekBar) this.findViewById(R.id.speedBar);
		accuracyBar = (SeekBar) this.findViewById(R.id.accuracyBar);
		playbackButton = (Button) this.findViewById(R.id.button1);
		
		playbackButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View view) {
				metronome.togglePlayback();
			}
		});
		
		speedBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				metronome.setSpeed(progress);
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
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				
			}
		});
		
		MediaPlayer player = MediaPlayer.create(this, R.raw.tablasnap);
		metronome = new Metronome(speedBar.getProgress(), accuracyBar.getProgress(), player);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
