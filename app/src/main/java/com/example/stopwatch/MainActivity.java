package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.text.MessageFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    MaterialButton reset,start,stop;
    int seconds, minutes, milliseconds;
    long millisecond, startTime, timeBuff, updateTime =0L;
    Handler handler;
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            millisecond = SystemClock.uptimeMillis() - startTime;
            updateTime = timeBuff + millisecond;
            seconds = (int) (updateTime/1000);
            minutes = seconds/60;
            seconds= seconds % 60;
            milliseconds = (int) (updateTime%1000);

            textView.setText(MessageFormat.format("{0}.{1}.{2}",minutes,String.format(Locale.getDefault(),"%02d",seconds),String.format(Locale.getDefault(),"%02d",milliseconds)));
            handler.postDelayed(this,0);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=findViewById(R.id.textView);
        reset=findViewById(R.id.reset);
        start=findViewById(R.id.start);
        stop=findViewById(R.id.stop);

        handler=new Handler(Looper.getMainLooper());

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable,0);
                reset.setEnabled(false);
                stop.setEnabled(true);
                start.setEnabled(false);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeBuff += millisecond;
                handler.removeCallbacks(runnable);
                reset.setEnabled(true);
                stop.setEnabled(false);
                start.setEnabled(true);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                millisecond = 0L;
                startTime = 0L;
                timeBuff = 0l;
                updateTime = 0L;
                seconds = 0;
                minutes = 0;
                milliseconds = 0;
                textView.setText("00:00:00");
            }
        });

        textView.setText(R.string._00_00_00);
    }
}