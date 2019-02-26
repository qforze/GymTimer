package com.loloc.gymtimer;

import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {

    SeekBar seekBar;
    TextView textView;
    CountDownTimer timerActual;
    CountDownTimer timerTextOnFinish;
    int time;
    int count;
    long minutes;
    long seconds;
    boolean active;
    Ringtone r;
    boolean alarm;

    public void setTime(View view) {

        Integer[] newCount = {0, 15, 30, 60, 90, 120, 180};

        String tag = view.getTag().toString();

        int tagInt = Integer.parseInt(tag);

        time = newCount[tagInt];

        seekBar.setProgress(time);

        slider(time);


        if (active) {

            timerActual.cancel();
            active = false;

        }
    }
    public void sliderTime() {

        if (count == 0) {

            textView.setText("Set Time");
            alarm = true;

        } else {

            textView.setText("" + String.format("%d :%d", minutes, seconds - (minutes * 60)));

        }

    }

    public void slider(int time) {

        count = time * 1000;

        minutes = TimeUnit.MILLISECONDS.toMinutes(count);
        seconds = TimeUnit.MILLISECONDS.toSeconds(count);

        sliderTime();

    }

    public void onClick(View view) {

        if (alarm == false) {

            timerActual = new CountDownTimer(count, 1000) {

                public void onTick(long count) {

                    seekBar.setProgress((int) count / 1000);

                    sliderTime();

                    if (time == 0) {

                        playFinishTune();

                        textView.setText("done!");

                    }
                }

                public void onFinish() {

                    timerTextOnFinish = new CountDownTimer(3000, 1000) {


                        @Override
                        public void onTick(long millisUntilFinished) {

                        }

                        @Override
                        public void onFinish() {

                            sliderTime();

                            playFinishTune();

                        }
                    }.start();

                }
            };
            timerActual.start();
            active = true;

        }

    }

    public void playFinishTune() {

        if (r.isPlaying()) {

            r.stop();

        } else {

            r.play();

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        textView.setText("Set Time");
        alarm = true;

        setVolumeControlStream(AudioManager.STREAM_NOTIFICATION);
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        r = RingtoneManager.getRingtone(getApplicationContext(), notification);

        seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(60);
        seekBar.setProgress(0);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                time = progress * 5;

                alarm = false;

                slider(time);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                if (active) {

                    timerActual.cancel();
                    active = false;

                }

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


            }
        });

    }
}
