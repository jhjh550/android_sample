package com.order.law.criminalintent;

import android.support.v4.app.DialogFragment;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by jhjh550 on 2015-01-23.
 */
public class ImageFragment extends DialogFragment {
    public static final String EXTRA_IMAGE_PATH = "com.order.law.criminalintent.image_path";
    private ImageView mImageView;


    public static ImageFragment newInstance(String imagePath){
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_IMAGE_PATH, imagePath);

        ImageFragment fragment = new ImageFragment();
        fragment.setArguments(args);
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mImageView = new ImageView(getActivity());
        String path = (String)getArguments().getSerializable(EXTRA_IMAGE_PATH);
        BitmapDrawable image = PictureUtils.getScaledDrawable(getActivity(), path);
        mImageView.setImageDrawable(image);
        return mImageView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        PictureUtils.cleanImageView(mImageView);
    }
}























