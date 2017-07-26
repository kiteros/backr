package com.example.jules.sesl20;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

public class results extends AppCompatActivity {

    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        final ImageButton restart = (ImageButton) findViewById(R.id.res);

        Button l = (Button) findViewById(R.id.button);
        l.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();

            }
        });

        restart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                progress = new ProgressDialog(results.this);
                progress.setMessage("Downloading pictures");
                progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                //progress.setIndeterminate(true);
                progress.setProgress(0);
                progress.show();
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
                finish();


            }
        });

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(variables.getTenScore() + "/10");

        ProgressBar bar = (ProgressBar) findViewById(R.id.progressBar);
        bar.setProgress(variables.getTenScore() * 10);
    }

    protected void onDestroy() {
        if((progress != null) && progress.isShowing()){
            progress.dismiss();
        }
        super.onDestroy();

    }


}
