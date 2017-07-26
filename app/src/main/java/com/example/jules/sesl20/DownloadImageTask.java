package com.example.jules.sesl20;

/**
 * Created by jules on 27/03/2017.
 */

        import android.app.Activity;
        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.graphics.Canvas;
        import android.graphics.Matrix;
        import android.graphics.Paint;
        import android.graphics.PorterDuff;
        import android.graphics.PorterDuffXfermode;
        import android.graphics.Rect;
        import android.os.AsyncTask;
        import android.os.Environment;
        import android.support.v4.widget.SwipeRefreshLayout;
        import android.util.Log;
        import android.view.WindowManager;
        import android.widget.ImageView;
        import android.widget.Toast;

        import java.io.File;
        import java.io.FileNotFoundException;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.io.InputStream;
        import java.text.SimpleDateFormat;
        import java.util.Date;

/**
 * Created by jules on 23/12/16.
 */

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

    ImageView bmImage;
    boolean reto = false;
    boolean setbookPic = false;
    Context context = null;
    SwipeRefreshLayout swipe = null;
    ProgressDialog progDailog = null;
    int headId;
    int id = 0;
    int max;
    ProgressDialog pr;
    Activity ab;

    public DownloadImageTask(Context context, int head_ud, int id, int max, ProgressDialog pr2) {
        this.context = context;
        this.headId = head_ud;
        this.id = id;
        this.max = max;
        this.pr = pr2;
    }

    public DownloadImageTask(Context context, int head_ud, int id, int max, ProgressDialog pr2, Activity a) {
        this.context = context;
        this.headId = head_ud;
        this.id = id;
        this.max = max;
        this.pr = pr2;
        this.ab = a;
    }



    @Override
    protected void onPreExecute() {


    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        switch(max){
            case 9:
                pr.setProgress(pr.getProgress() + 10);
                break;
            case 19:
                pr.setProgress(pr.getProgress() + 5);
                break;
            case 49:
                pr.setProgress(pr.getProgress() + 2);
                break;
        }

        File folder = new File(Environment.getExternalStorageDirectory() +
                File.separator + "allImages");
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdirs();
        }else{

        }
        if (success) {
            SaveImage(result, String.valueOf(headId));
            if(id == max){

                if((pr != null) && pr.isShowing()){
                    pr.dismiss();
                }

                //Charge new activity
                Intent foo = new Intent();
                switch(max){
                    case 9:

                        foo.setClass(context.getApplicationContext(), head.class);
                        foo.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(foo);
                        break;
                    case 19:

                        foo.setClass(context.getApplicationContext(), head20.class);
                        foo.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(foo);
                        break;
                    case 49:

                        foo.setClass(context.getApplicationContext(), head50.class);
                        foo.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(foo);
                        break;
                }



            }
        } else {
            // Do something else on failure
            Toast toast = Toast.makeText(context, "Gros problème!", Toast.LENGTH_SHORT);
            toast.show();
            SaveImage(result, String.valueOf(headId));
            if(id == max){

                //Charge new activity
                Intent foo = new Intent();
                foo.setClass(context.getApplicationContext(), head.class);
                foo.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(foo);
                /*if(ab != null){
                    ab.finish();
                }*/

            }
        }




    }





    private static void SaveImage(Bitmap finalBitmap, String name) {

        Bitmap dstBmp;
        if (finalBitmap.getWidth() >= finalBitmap.getHeight()){

            dstBmp = Bitmap.createBitmap(
                    finalBitmap,
                    finalBitmap.getWidth()/2 - finalBitmap.getHeight()/2,
                    0,
                    finalBitmap.getHeight(),
                    finalBitmap.getHeight()
            );

        }else{

            dstBmp = Bitmap.createBitmap(
                    finalBitmap,
                    0,
                    finalBitmap.getHeight()/2 - finalBitmap.getWidth()/2,
                    finalBitmap.getWidth(),
                    finalBitmap.getWidth()
            );
        }
        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        File myDir = new File(root + "/allImages");
        myDir.mkdirs();
        dstBmp = Bitmap.createScaledBitmap(dstBmp, 500, 500, false);
        String fname = name +".jpg";
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            dstBmp.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}