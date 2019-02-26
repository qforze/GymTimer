package com.loloc.gymtimer;

import android.os.CountDownTimer;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity {

    SeekBar seekBar;
    TextView textView;
    CountDownTimer timerActual;
    int time;
    int count;
    long minutes;
    long seconds;
    boolean active;
    Button[] buttons;


    public void sliderTime() {

        if (count == 0) {

            textView.setText("Set Time");

        } else {

            textView.setText("" + String.format("%d :%2d", minutes, seconds - (minutes * 60)));

        }

    }

    public void slider(int time) {

        count = time * 1000;

        textView = findViewById(R.id.textView);

        minutes = TimeUnit.MILLISECONDS.toMinutes(count);
        seconds = TimeUnit.MILLISECONDS.toSeconds(count);

        sliderTime();

    }

    public void setTime(View view) {


    }

    public void onClick(View view) {

        timerActual = new CountDownTimer(count, 1000) {

            public void onTick(long count) {

                seekBar.setProgress((int) count / 1000);

                sliderTime();

                if (time == 0) {

                    textView.setText("done!");

                }
            }

            public void onFinish() {

            }
        };
        timerActual.start();
        active = true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.seekBar);
        Button go = findViewById(R.id.Go);

        seekBar.setMax(300);
        seekBar.setProgress(1);


        //Creating different buttons

       /* buttons = new Button[6];

        for (int i = 1; i <= buttons.length; i++) {

            String buttonID = "button" + i;
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = findViewById(resID);

        } */


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                time = progress;

                slider(time);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                if (active == true) {

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
