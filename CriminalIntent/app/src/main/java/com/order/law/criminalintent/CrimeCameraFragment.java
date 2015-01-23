package com.order.law.criminalintent;

import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.IOException;
import java.util.List;

/**
 * Created by jhjh550 on 2015-01-23.
 */
public class CrimeCameraFragment extends Fragment {
    private static final String TAG = "CrimeCameraFragment";

    private Camera mCamera;
    private SurfaceView mSurfaceView;


    @Override
    public void onResume() {
        super.onResume();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD){
            mCamera = Camera.open(0);
        }else{
            mCamera = Camera.open();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if(mCamera != null){
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime_camera, container, false);

        Button takePictureButton = (Button)v.findViewById(R.id.crime_camera_takePictureButton);
        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        mSurfaceView = (SurfaceView)v.findViewById(R.id.crime_camera_surfaceView);
        SurfaceHolder holder = mSurfaceView.getHolder();
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        holder.addCallback(new SurfaceHolder.Callback(){

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                // 이 surface 를 미리 보기영역으로 사용한다는 것을 카메라에게 알려준다.
                if(mCamera != null){
                    try {
                        mCamera.setPreviewDisplay(holder);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                if(mCamera == null) return;

                // surface 의 크기가 변경되었으므로 카메라 미리 보기 크기를 변경한다.
                Camera.Parameters parameters = mCamera.getParameters();
                Camera.Size s =
                        getBestSupportedSize(parameters.getSupportedPreviewSizes(), width, height);
                parameters.setPreviewSize(s.width, s.height);
                mCamera.setParameters(parameters);
                try {
                    mCamera.startPreview();
                }catch (Exception e){
                    e.printStackTrace();
                    mCamera.release();
                    mCamera = null;
                }

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                // 더 이상 이 surface 를 사용할 수 없으므로 미리 보기를 중단한다.
                if(mCamera != null){
                    mCamera.stopPreview();
                }
            }
        });

        return v;
    }

    private Camera.Size getBestSupportedSize(List<Camera.Size> sizes, int width, int height){
        Camera.Size bestSizes = sizes.get(0);
        int largestArea = bestSizes.width * bestSizes.height;
        for(Camera.Size s : sizes){
            int area = s.width * s.height;
            if(area > largestArea){
                bestSizes = s;
                largestArea = area;
            }
        }
        return bestSizes;
    }
}























