package com.loloc.gymtimer;

import java.util.concurrent.TimeUnit;

import static com.loloc.gymtimer.MainActivity.count;
import static com.loloc.gymtimer.MainActivity.minutes;
import static com.loloc.gymtimer.MainActivity.seconds;
import static com.loloc.gymtimer.MainActivity.textView;
import static com.loloc.gymtimer.MainActivity.alarm;


class Time {

    void slider(int time) {

        count = time * 1000;

        minutes = TimeUnit.MILLISECONDS.toMinutes(count);
        seconds = TimeUnit.MILLISECONDS.toSeconds(count);

        sliderTime();

    }

    void sliderTime() {


        if (count == 0) {

            textView.setText("Set Time");
            alarm = true;

        } else {

            textView.setText("" + String.format("%d :%d", minutes, seconds - (minutes * 60)));

        }
    }
}

