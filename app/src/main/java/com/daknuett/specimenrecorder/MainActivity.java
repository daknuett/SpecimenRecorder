package com.daknuett.specimenrecorder;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Toast;

import com.daknuett.specimenrecorder.fragments.LocationRecordListFragment;
import com.daknuett.specimenrecorder.fragments.MyFragment;
import com.daknuett.specimenrecorder.fragments.NewLocationRecordFragment;
import com.daknuett.specimenrecorder.fragments.NewSpecimenRecordFragment;
import com.daknuett.specimenrecorder.fragments.SpecimenRecordListFragment;
import com.daknuett.specimenrecorder.listeners.OnInfoClickedListener;
import com.daknuett.specimenrecorder.listeners.OnSettingsClickedListener;

import java.io.File;


public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_WES_CODE = 0x0000beef;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private OnSettingsClickedListener onSettingsClickedListener;

    private File storagePath;
    private OnInfoClickedListener onInfoClickedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.



        if( ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(getApplicationContext(),
                    "This application requires access to the external storage.", Toast.LENGTH_LONG).show();

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_WES_CODE);
        }


        storagePath = new File(Environment.getExternalStorageDirectory(), "specimenrecorder");
        String settingsFileName = storagePath.getAbsolutePath();



        File settingsFilePath = new File(settingsFileName);

        if(!settingsFilePath.exists())
        {
            System.out.println(settingsFilePath.mkdirs());
        }
        File imagePath = new File(storagePath, getString(R.string.image_prefix));
        if(!imagePath.exists())
            imagePath.mkdirs();
        File dataPath = new File(storagePath, getString(R.string.data_path));
        if(!dataPath.exists())
        {
            dataPath.mkdirs();
        }



        settingsFileName = settingsFilePath.getAbsolutePath() + "/" + getString(R.string.settings_filename);


        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),
                new File(storagePath, getString(R.string.data_path)).getAbsolutePath(),
                settingsFileName);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);



        onSettingsClickedListener = new OnSettingsClickedListener(this,
                settingsFileName,
                dataPath.getAbsolutePath() );
        onInfoClickedListener = new OnInfoClickedListener(this);

    }


    private boolean checkAndCreateStorage()
    {
        if(storagePath.exists())
        {
            return true;
        }
        return storagePath.mkdirs();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_WES_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkAndCreateStorage();
                }
                else {
                    this.finish();
                    System.exit(1);
                }
        }
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            onSettingsClickedListener.onMenuItemClick(item);
            return true;
        }
        if (id == R.id.action_info)
        {
            onInfoClickedListener.onMenuItemClick(item);
        }

        return super.onOptionsItemSelected(item);
    }

    
  

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private String dataPath;
        private String settingsFilename;

        public SectionsPagerAdapter(FragmentManager fm, String dataPath, String settingsFilename)
        {
            super(fm);
            this.dataPath = dataPath;
            this.settingsFilename = settingsFilename;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1, dataPath, settingsFilename, storagePath.getPath());
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Add Specimen Record";
                case 1:
                    return "Specimen Records";
                case 2:
                    return "Add Location Record";
                case 3:
                    return "Location Records";
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends android.support.v4.app.Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";


        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static android.support.v4.app.Fragment newInstance(int sectionNumber,
                                                                  String dataPath,
                                                                  String settingsFilename,
                                                                  String storagePath) {
            android.support.v4.app.Fragment fragment = null;
            switch (sectionNumber)
            {
                case 1:
                    fragment = new NewSpecimenRecordFragment();
                    break;
                case 2:
                    fragment = new SpecimenRecordListFragment();
                    break;
                case 3:
                    fragment = new NewLocationRecordFragment();
                    break;
                case 4:
                    fragment = new LocationRecordListFragment();
                    break;
            }

            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putString(MyFragment.ARG_DATA_PATH, dataPath);
            args.putString(MyFragment.ARG_SETTINGS_FILENAME, settingsFilename);
            args.putString(MyFragment.ARG_STORAGE_PATH, storagePath);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }


    }
}
