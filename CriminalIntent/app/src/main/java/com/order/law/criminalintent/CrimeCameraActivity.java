package com.order.law.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by jhjh550 on 2015-01-23.
 */
public class CrimeCameraActivity extends SingleFragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 윈도우 제목을 감춘다.
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 상태바와 그 밖의 운영체제 수준의 기능을 감춘다.
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment createFragment() {
        return new CrimeCameraFragment();
    }
}
