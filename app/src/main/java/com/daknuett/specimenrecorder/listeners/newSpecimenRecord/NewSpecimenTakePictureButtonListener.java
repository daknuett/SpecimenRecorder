package com.daknuett.specimenrecorder.listeners.newSpecimenRecord;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daknuett.specimenrecorder.R;
import com.daknuett.specimenrecorder.fragments.NewLocationRecordFragment;
import com.daknuett.specimenrecorder.model.intents.RequestCodes;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by daniel on 07.08.17.
 */

public class NewSpecimenTakePictureButtonListener
        implements View.OnClickListener

{

    private TextView imageURITextView;
    private ImageView imageView;
    private Activity activity;
    private Fragment fragment;

    public NewSpecimenTakePictureButtonListener(TextView imageURITextView, ImageView imageView, Activity activity, Fragment fragment) {
        this.imageURITextView = imageURITextView;
        this.imageView = imageView;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public void onClick(View v) {
        dispatchCaptureImage();
    }

    private void dispatchCaptureImage()
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try {
            File imageFile = createImageFile();
            imageURITextView.setText(imageFile.getName());
            Uri photoURI = FileProvider.getUriForFile(activity,
                    "com.daknuett.android.fileprovider",
                    imageFile);


                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            fragment.startActivityForResult(takePictureIntent, RequestCodes.REQUEST_CAPTURE_PHOTO);


        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(activity.getApplicationContext(), activity.getString(R.string.failed_to_capture_image), Toast.LENGTH_SHORT).show();
        }
    }


    public void onActivityResult(Intent data) {

        File storageDir = new File(Environment.getExternalStorageDirectory(), activity.getString(R.string.path));
        storageDir = new File(storageDir, activity.getString(R.string.image_prefix));
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + ".jpg";
        File imageNow = new File(storageDir, "just_taken.jpg");
        File imageRenamed = new File(storageDir, imageFileName);
        imageNow.renameTo(imageRenamed);

        imageURITextView.setText(imageFileName);

        Bitmap imageData = BitmapFactory.decodeFile(imageRenamed.getAbsolutePath());
        imageView.setImageBitmap(imageData);
    }

    private File createImageFile() throws IOException {


        File storageDir = new File(Environment.getExternalStorageDirectory(), activity.getString(R.string.path));
        storageDir = new File(storageDir, activity.getString(R.string.image_prefix));
        File image = new File(storageDir, "just_taken.jpg");

        return image;
    }
}
