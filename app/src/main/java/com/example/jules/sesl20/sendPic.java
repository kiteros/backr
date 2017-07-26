package com.example.jules.sesl20;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class sendPic extends AppCompatActivity {

    ImageView im;
    private String KEY_IMAGE = "image";
    private String KEY_NAME = "name";
    private Bitmap imageToUpload;
    private int idbac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_pic);
        Button choose = (Button) findViewById(R.id.choose);
        im = (ImageView) findViewById(R.id.imageView);
        Button send = (Button) findViewById(R.id.send);
        Spinner dropdown = (Spinner)findViewById(R.id.spinner);
        ImageButton turn1 = (ImageButton) findViewById(R.id.t1);
        ImageButton turn2 = (ImageButton) findViewById(R.id.t2);

        String[] items = new String[]{"Bac S", "Bac ES", "Bac L", "Pro"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                idbac = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });



        turn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                imageToUpload = Bitmap.createBitmap(imageToUpload , 0, 0, imageToUpload .getWidth(), imageToUpload .getHeight(), matrix, true);
                im.setImageBitmap(imageToUpload);

            }

        });

        turn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Matrix matrix = new Matrix();
                matrix.postRotate(270);
                imageToUpload = Bitmap.createBitmap(imageToUpload , 0, 0, imageToUpload .getWidth(), imageToUpload .getHeight(), matrix, true);
                im.setImageBitmap(imageToUpload);

            }

        });

        choose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Ouvre la camera
                final String[] cameraGallery = {"Camera", "Gallery"};

                AlertDialog.Builder builder = new AlertDialog.Builder(sendPic.this);
                builder.setTitle("Take image from:").setItems(cameraGallery, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which){
                            case 0:
                                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intent,0);
                                break;
                            case 1:
                                showFileChooser();
                                break;
                        }
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                uploadImage();
            }

        });


    }
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Bitmap imageBitmap = null;
            if(requestCode == 1){

                Uri filePath = data.getData();
                try {
                    //Getting the Bitmap from Gallery
                    imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                Bundle extras = data.getExtras();
                imageBitmap = (Bitmap) extras.get("data");

            }

            Bitmap dstBmp;
            if (imageBitmap.getWidth() >= imageBitmap.getHeight()){

                dstBmp = Bitmap.createBitmap(
                        imageBitmap,
                        imageBitmap.getWidth()/2 - imageBitmap.getHeight()/2,
                        0,
                        imageBitmap.getHeight(),
                        imageBitmap.getHeight()
                );

            }else{

                dstBmp = Bitmap.createBitmap(
                        imageBitmap,
                        0,
                        imageBitmap.getHeight()/2 - imageBitmap.getWidth()/2,
                        imageBitmap.getWidth(),
                        imageBitmap.getWidth()
                );
            }

            imageToUpload = Bitmap.createScaledBitmap(dstBmp, 500, 500, false);

            im.setImageBitmap(imageToUpload);

            //On envoi l'image sur le serveur et un fois que c'est fait, on l'affiche
            /*imageToUpload = imageBitmap;
            imageToUpload = resize(imageToUpload, 750, 2000);
            ImageView preview = new ImageView(context);
            RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativ);
            preview.setImageBitmap(imageToUpload);
            relativeLayout.addView(preview);*/

        }
    }

    private void uploadImage(){
        //double rapport = imageToUpload.getHeight() / imageToUpload.getWidth();
        //double initalValue = 750.0;
        //int nouvelleValue = (int) (initalValue *rapport);
        //Toast.makeText(addBook.this, String.valueOf(imageToUpload.getHeight()) + String.valueOf(imageToUpload.getWidth()), Toast.LENGTH_LONG).show();
        //imageToUpload = getResizedBitmap(imageToUpload, 750, imageToUpload.getHeight());
        //Showing the progress dialog

        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://jeschbach.com/sesl/uploadphoto.php?bac=" + String.valueOf(idbac),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Toast.makeText(sendPic.this, s , Toast.LENGTH_LONG).show();
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();
                        if(volleyError.getMessage() == null){
                            //Toast.makeText(addBook.this, "erreur d'envoi", Toast.LENGTH_LONG).show();
                            finish();
                        }else{
                            //Showing toast
                            Toast.makeText(sendPic.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }


                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                String image = getStringImage(imageToUpload);


                //Getting Image Name
                String name = "newPic";


                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put(KEY_IMAGE, image);
                params.put(KEY_NAME, name);

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


}
