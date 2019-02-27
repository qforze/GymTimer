package com.loloc.gymtimer;

import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {


    Time timeClass;
    SeekBar seekBar;
    Ringtone r;
    static TextView textView;
    static CountDownTimer timerActual;
    static CountDownTimer timerTextOnFinish;
    static int time;
    static int count;
    static long minutes;
    static long seconds;
    static boolean active;
    static boolean alarm;


    public void onClick(View view) {

        if (!alarm) {

            timerActual = new CountDownTimer(count, 1000) {

                public void onTick(long count) {

                    seekBar.setProgress((int) count / 1000);

                    timeClass.sliderTime();

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

                            timeClass.sliderTime();

                            playFinishTune();

                        }
                    }.start();

                }
            };
            timerActual.start();
            active = true;

        }

    }

    public void setTime(View view) {

        Integer[] newCount = {0, 15, 30, 60, 90, 120, 180};

        String tag = view.getTag().toString();

        int tagInt = Integer.parseInt(tag);

        time = newCount[tagInt];

        seekBar.setProgress(time);

        timeClass.slider(time);

        if (active) {

            timerActual.cancel();
            active = false;

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

        timeClass = new Time();

        seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(300);
        seekBar.setProgress(0);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                time = progress;

                alarm = false;

                timeClass.slider(time);

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
