package com.order.law.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by jhjh on 15. 1. 14.
 */
public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
