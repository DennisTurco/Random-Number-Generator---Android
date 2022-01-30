package com.example.randomnumbergenerator;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.widget.Button;
import android.view.View;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.concurrent.ThreadLocalRandom;
import android.os.CountDownTimer;


public class MainActivity extends AppCompatActivity {
    private ImageButton buttonExit;
    private ImageButton buttonClear;
    private Button buttonGenerate;
    private EditText input1;
    private EditText input2;
    private TextView label;
    private MediaPlayer DiceSound;
    private ImageButton buttonUpFrom, buttonDownFrom, buttonUpTo, buttonDownTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DiceSound = MediaPlayer.create(MainActivity.this, R.raw.dice);  // per il suono del dado

        //--------------------- ExitButton -----------------------
        buttonExit = (ImageButton) findViewById(R.id.btnExit);
        buttonExit.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v){
                finish();
            }
        });
        //--------------------- ClearButton -----------------------
        input1=findViewById(R.id.InputFrom);
        input2=findViewById(R.id.InputTo);

        buttonClear = findViewById(R.id.btnClear);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input1.getText().clear();
                input2.getText().clear();
                label.setText("");
            }
        });

        //--------------------- Up1Button -----------------------
        buttonUpFrom = (ImageButton) findViewById(R.id.upFromButton);
        buttonUpFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input1.getText().length() == 0) input1.setText("1");
                else {
                    int ris;
                    ris = Integer.parseInt(input1.getText().toString()) + 1;
                    input1.setText("" + ris);
                }

            }
        });

        //--------------------- Down1Button -----------------------
        buttonDownFrom = (ImageButton) findViewById(R.id.downFromButton);
        buttonDownFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input1.getText().length() == 0) input1.setText("-1");
                else {
                    int ris;
                    ris = Integer.parseInt(input1.getText().toString()) - 1;
                    input1.setText("" + ris);
                }
            }
        });

        //--------------------- Up2Button -----------------------
        buttonUpTo = (ImageButton) findViewById(R.id.upToButton);
        buttonUpTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input2.getText().length() == 0) input2.setText("1");
                else {
                    int ris;
                    ris = Integer.parseInt(input2.getText().toString()) + 1;
                    input2.setText("" + ris);
                }
            }
        });

        //--------------------- Down2Button -----------------------
        buttonDownTo = (ImageButton) findViewById(R.id.downToButton);
        buttonDownTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input2.getText().length() == 0) input2.setText("-1");
                else {
                    int ris;
                    ris = Integer.parseInt(input2.getText().toString()) - 1;
                    input2.setText("" + ris);
                }
            }
        });

        //--------------------- GenerateButton -----------------------
        buttonGenerate=findViewById(R.id.btnGenerate);
        label=findViewById(R.id.labelNumber);

        //loading=findViewById(R.id.loading);

        buttonGenerate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                if(input1.length()>9 || input2.length()>9)
                {
                    label.setTextColor(Color.RED);
                    label.setTextSize(15);
                    label.setText("The range has been exceeded!");
                }else if(input1.length() == 0 || input2.length() == 0){
                    label.setTextSize(15);
                    label.setTextColor(Color.RED);
                    label.setText("Input Missing!");
                }else if(Integer.parseInt(input1.getText().toString())>Integer.parseInt(input2.getText().toString())){
                    label.setTextSize(15);
                    label.setTextColor(Color.RED);
                    label.setText("First number must be shorter than the second!");
                }else{
                    DiceSound.start(); // parte il suono del dado
                    new CountDownTimer(3000,1000) {
                        Context context;
                        int index = 0;
                        String loading = "";

                        @Override
                        public void onTick(long millisUntilFinished) {
                            buttonGenerate.setEnabled(false);
                            label.setTextColor(Color.YELLOW);
                            label.setTextSize(25);
                            loading = loading + ".";
                            index++;
                            label.setText(loading);
                        }

                        @Override
                        public void onFinish() {
                            label.setTextColor(Color.GREEN);
                            label.setTextSize(25);
                            int random = ThreadLocalRandom.current().nextInt( Integer.parseInt(input1.getText().toString()), Integer.parseInt(input2.getText().toString()) + 1 );
                            label.setText(""+random);
                            buttonGenerate.setEnabled(true);
                        }
                    }.start();
                }
            }
        });
    }

}
