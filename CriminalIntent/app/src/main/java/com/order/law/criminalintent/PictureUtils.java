package com.order.law.criminalintent;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.Display;
import android.widget.ImageView;

/**
 * Created by jhjh550 on 2015-01-23.
 */
public class PictureUtils {
    public static BitmapDrawable getScaledDrawable(Activity a, String path){
        Display display = a.getWindowManager().getDefaultDisplay();
        float destWidth = display.getWidth();
        float destHeight = display.getHeight();

        // 이미지의 크기를 읽는다.
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;

        int inSampleSize = 1;
        if(srcHeight > destHeight || srcWidth > destWidth){
            if(srcWidth > srcHeight){
                inSampleSize = Math.round(srcHeight/destHeight);
            }else{
                inSampleSize = Math.round(srcWidth/destWidth);
            }
        }

        options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;

        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        return new BitmapDrawable(a.getResources(), bitmap);
    }

    public static void cleanImageView(ImageView imageView){
        if(!(imageView.getDrawable() instanceof  BitmapDrawable))
            return;

        // 메모리를 절약하기 위해 뷰의 이미지를 클린업한다.
        BitmapDrawable b = (BitmapDrawable)imageView.getDrawable();
        b.getBitmap().recycle();
        imageView.setImageDrawable(null);
    }

}
























