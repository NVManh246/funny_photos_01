package com.example.ginz.funnyphoto.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public final class ImageHandler {
    private static final int SAMPLE_SIZE = 8;
    private static final int ENCODE_QUALITY = 100;

    public static String imageToString(Bitmap bitmap) {
        if(bitmap != null) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, ENCODE_QUALITY, outputStream);
            byte[] imageBytes = outputStream.toByteArray();

            String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            return encodedImage;
        }
        return "";
    }

    public static String imageToString(String photoPath) {
        Bitmap bitmap = BitmapFactory.decodeFile(photoPath);
        if(bitmap != null) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, ENCODE_QUALITY, outputStream);
            byte[] imageBytes = outputStream.toByteArray();

            String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            return encodedImage;
        }
        return "";
    }

    private Bitmap decodeImage(String path){
        File file = new File(path);
        try {
            FileInputStream fis = new FileInputStream(file);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = SAMPLE_SIZE;
            return BitmapFactory.decodeStream(fis, null, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Bitmap decodeSampledBitmapFromPath(File f, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        try {
            BitmapFactory.decodeStream(new FileInputStream(f), null, options);
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth,
                                            int reqHeight) {
        int inSampleSize = 1;
        final int height = options.outHeight;
        final int width = options.outWidth;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }
}
