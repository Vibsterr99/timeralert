package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SeekBar timebar;
    TextView time;
    Button button;
    Boolean timeactive=false;
    CountDownTimer cdt;
    int minutes;
    int seconds;
    public void reset(){
        timebar.setEnabled(true);
        timeactive = false;
        button.setText("Go!");
        cdt.cancel();
        time.setText("0:30");

    }
    public void updatetimer(int timer){
        minutes=(int)timer/60;
        seconds=timer-minutes*60;
        String sec=Integer.toString(seconds);
        if(seconds<=9){
            sec="0"+sec;
        }

        time.setText(Integer.toString(minutes)+":"+sec);
    }
    public void timer(View view){
        if(timeactive==false) {
            timeactive = true;
            timebar.setEnabled(false);
            button.setText("Stop!");



        cdt=new CountDownTimer(timebar.getProgress()*1000+100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                updatetimer((int)millisUntilFinished/1000);

            }

            @Override
            public void onFinish() {
                time.setText("0:00");
                MediaPlayer mp=MediaPlayer.create(getApplicationContext(), R.raw.beep);
                mp.start();
                reset();
                timebar.setProgress(30);
                Log.i("finished","timer done");

            }
        }.start();
    }
        else {
            reset();
            timebar.setProgress(minutes*60+seconds);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timebar=(SeekBar)findViewById(R.id.timebar);
        time=(TextView)findViewById(R.id.time);
        button=(Button)findViewById(R.id.button);
        timebar.setMax(600)
        ;
        timebar.setProgress(30);

        timebar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               updatetimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
