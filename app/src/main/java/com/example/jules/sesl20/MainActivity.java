package com.example.jules.sesl20;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    int dialogProgress = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Activity a = this;

        Button head10 = (Button) findViewById(R.id.button7);
        Button l7 = (Button) findViewById(R.id.l7);
        Button l8 = (Button) findViewById(R.id.l8);
        Button add = (Button) findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent foo = new Intent();
                foo.setClass(getApplicationContext(), sendPic.class);
                foo.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(foo);
            }
        });

        //50
        l8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                variables.setFiftyScore(0);
                ProgressDialog progress = new ProgressDialog(MainActivity.this);
                progress.setMessage("Downloading pictures");
                progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progress.setProgress(0);
                progress.show();

                //Récupérer les ids des photos
                RequestTask getfollow = new RequestTask(getApplicationContext());
                String urlGetFollow = "http://www.jeschbach.com/sesl/selectPics.php?nbHead=50";
                String result = null;
                try {
                    result = getfollow.execute(urlGetFollow).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                String[] values = result.split(" ");
                for(int x = 0; x < values.length; x++){
                    String newValue = values[x].split("-")[0];
                    String newValue2 = values[x].split("-")[1];
                    variables.setHead50(x, Integer.parseInt(newValue));
                    variables.setBacs50(x, Integer.parseInt(newValue2));
                    DownloadImageTask im2 = new DownloadImageTask(getApplicationContext(), variables.getHead50(x), x, 49, progress, a);
                    im2.execute("http://www.jeschbach.com/sesl/photos/" + variables.getHead50(x) + ".jpg");
                }

            }
        });

        //20
        l7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                variables.setTwentyScore(0);
                ProgressDialog progress = new ProgressDialog(MainActivity.this);
                progress.setMessage("Downloading pictures");
                progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progress.setProgress(0);
                progress.show();

                //Récupérer les ids des photos
                RequestTask getfollow = new RequestTask(getApplicationContext());
                String urlGetFollow = "http://www.jeschbach.com/sesl/selectPics.php?nbHead=20";
                String result = null;
                try {
                    result = getfollow.execute(urlGetFollow).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                String[] values = result.split(" ");
                for(int x = 0; x < values.length; x++){
                    String newValue = values[x].split("-")[0];
                    String newValue2 = values[x].split("-")[1];
                    variables.setHead20(x, Integer.parseInt(newValue));
                    variables.setBacs20(x, Integer.parseInt(newValue2));
                    DownloadImageTask im2 = new DownloadImageTask(getApplicationContext(), variables.getHead20(x), x, 19, progress, a);
                    im2.execute("http://www.jeschbach.com/sesl/photos/" + variables.getHead20(x) + ".jpg");
                }

            }
        });


        //10
        head10.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                variables.setTenScore(0);
                ProgressDialog progress = new ProgressDialog(MainActivity.this);
                progress.setMessage("Downloading pictures");
                progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

                progress.setProgress(0);
                progress.show();
                progress.setCanceledOnTouchOutside(false);

                //Récupérer les ids des photos
                RequestTask getfollow = new RequestTask(getApplicationContext());
                String urlGetFollow = "http://www.jeschbach.com/sesl/selectPics.php?nbHead=10";
                String result = null;
                try {
                    result = getfollow.execute(urlGetFollow).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                String[] values = result.split(" ");
                for(int x = 0; x < values.length; x++){
                    String newValue = values[x].split("-")[0];
                    String newValue2 = values[x].split("-")[1];
                    variables.setHead10(x, Integer.parseInt(newValue));
                    variables.setBacs(x, Integer.parseInt(newValue2));
                    DownloadImageTask im2 = new DownloadImageTask(getApplicationContext(), variables.getHead10(x), x, 9, progress);
                    im2.execute("http://www.jeschbach.com/sesl/photos/" + variables.getHead10(x) + ".jpg");
                }
            }
        });
    }
}
