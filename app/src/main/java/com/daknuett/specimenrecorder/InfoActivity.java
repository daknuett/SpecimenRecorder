package com.daknuett.specimenrecorder;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.daknuett.specimenrecorder.R;

/**
 * Created by daniel on 12.08.17.
 */

public class InfoActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.out.println(getString(R.string.info_text));

        setContentView(R.layout.activity_info);
    }
}
