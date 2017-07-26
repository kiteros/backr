package com.example.jules.sesl20;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;

public class head20 extends AppCompatActivity {

    ImageView view = null;
    Bitmap[] allTwentyHeadImages = new Bitmap[20];
    int currentIdPic = 0;
    Button s;
    Button es;
    Button l;
    Button pro;
    TextView time;
    ProgressBar bar;
    int progressState = 0;
    int goodAnswer = 0;
    CountDownTimer t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head);
        view = (ImageView) findViewById(R.id.imageView4);
        s = (Button) findViewById(R.id.S);
        es = (Button) findViewById(R.id.l7);
        l = (Button) findViewById(R.id.l);
        pro = (Button) findViewById(R.id.pro);
        time = (TextView) findViewById(R.id.time);
        bar = (ProgressBar) findViewById(R.id.progress);

        t = new CountDownTimer(35000, 1000) {
            String plus = "";
            public void onTick(long millisUntilFinished) {

                if((millisUntilFinished / 1000) % 60 < 10){
                    plus = "0";
                }else{
                    plus = "";
                }
                time.setText((millisUntilFinished / 1000)/60 + ":" + plus + (millisUntilFinished / 1000) % 60);
            }

            public void onFinish() {
                time.setText("finished");
                endApplication();
            }
        };
        t.start();
        bar.setProgress(progressState);

        String root = Environment.getExternalStorageDirectory().getAbsolutePath();

        for(int x = 0; x < 20; x++){
            File imgFile = new  File(root + "/allImages/" + String.valueOf(variables.getHead20(x)) + ".jpg");

            if(imgFile.exists()){

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                allTwentyHeadImages[x] = myBitmap;
                view.setImageBitmap(myBitmap);
            }
        }

        s.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                printhead(0);
            }
        });

        es.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                printhead(1);
            }
        });

        l.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                printhead(2);
            }
        });

        pro.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                printhead(3);
            }
        });

    }

    private void endApplication(){
        Intent foo = new Intent();
        foo.setClass(getApplicationContext(), results20.class);
        foo.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(foo);


        this.finish();
    }

    public void onDestroy() {
        super.onDestroy();
        t.cancel();
    }

    private void printhead(int idBac){
        /*
        0 => S
        1 => ES
        2 => L
        3 => Pro
         */

        if(currentIdPic < 20){
            if(variables.getBacs20(currentIdPic) == idBac){
                //Le joueur à répondu juste

                //Afficher une animation
                switch(idBac){
                    case 0:
                        s.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                        break;
                    case 1:
                        es.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                        break;
                    case 2:
                        l.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                        break;
                    case 3:
                        pro.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                        break;
                }

                goodAnswer ++;




                new CountDownTimer(1000, 100) {
                    public void onFinish() {
                        s.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(90, 89 ,91)));
                        es.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(90, 89 ,91)));
                        l.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(90, 89 ,91)));
                        pro.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(90, 89 ,91)));
                        if(currentIdPic < 19){
                            currentIdPic ++;
                            view.setImageBitmap(allTwentyHeadImages[currentIdPic]);
                            progressState += 5;
                            bar.setProgress(progressState);

                        }else{
                            String root = Environment.getExternalStorageDirectory().getAbsolutePath();
                            File dir = new File(root + "/allImages");
                            if (dir.isDirectory())
                            {
                                String[] children = dir.list();
                                for (int i = 0; i < children.length; i++)
                                {
                                    new File(dir, children[i]).delete();
                                }
                            }
                            variables.setTwentyScore(goodAnswer);
                            endApplication();

                        }

                    }

                    public void onTick(long millisUntilFinished) {
                        // millisUntilFinished    The amount of time until finished.
                    }
                }.start();



            }else{
                //Le joueur à mal répondu
                switch(idBac){
                    case 0:
                        s.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        break;
                    case 1:
                        es.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        break;
                    case 2:
                        l.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        break;
                    case 3:
                        pro.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        break;
                }

                switch(variables.getBacs20(currentIdPic)){
                    case 0:
                        s.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                        break;
                    case 1:
                        es.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                        break;
                    case 2:
                        l.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                        break;
                    case 3:
                        pro.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                        break;
                }

                new CountDownTimer(1000, 100) {
                    public void onFinish() {
                        s.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(90, 89 ,91)));
                        es.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(90, 89 ,91)));
                        l.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(90, 89 ,91)));
                        pro.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(90, 89 ,91)));
                        if(currentIdPic < 19){
                            currentIdPic ++;
                            view.setImageBitmap(allTwentyHeadImages[currentIdPic]);
                            progressState += 5;
                            bar.setProgress(progressState);
                        }else{
                            String root = Environment.getExternalStorageDirectory().getAbsolutePath();
                            File dir = new File(root + "/allImages");
                            if (dir.isDirectory())
                            {
                                String[] children = dir.list();
                                for (int i = 0; i < children.length; i++)
                                {
                                    new File(dir, children[i]).delete();
                                }
                            }
                            variables.setTenScore(goodAnswer);
                            endApplication();

                        }
                    }

                    public void onTick(long millisUntilFinished) {
                        // millisUntilFinished    The amount of time until finished.
                    }
                }.start();
            }

        }else{
            String root = Environment.getExternalStorageDirectory().getAbsolutePath();
            File dir = new File(root + "/allImages");
            if (dir.isDirectory())
            {
                String[] children = dir.list();
                for (int i = 0; i < children.length; i++)
                {
                    new File(dir, children[i]).delete();
                }
            }
            endApplication();
        }


    }
}
