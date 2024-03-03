package com.ptn.myapplicationforlearn.ContactApp.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class ConverImage {

    public static String convertBitmapToString(ImageView imageView) {
        try {
            BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
            Bitmap mBitmap = drawable.getBitmap();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();
            String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            return imageString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap convertStringToBitmap(String imageString) {
        try {
            byte[] encodeByte = Base64.decode(imageString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
