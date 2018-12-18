package com.example.user.myapplication;

import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ViewPager contacts_frag_container;
    private PagerAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contacts_frag_container = (ViewPager) findViewById(R.id.contacts_frag_container);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        contacts_frag_container.setAdapter(pagerAdapter);
    }

    private class PagerAdapter extends FragmentStatePagerAdapter {

        private Fragment[] contactFrags = {new DisplayContactsFrag(),
                                        new ContactsGroupFrag()};

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0 :
                    return contactFrags[position];

                case 1 :
                    return contactFrags[position];

                default:
                    Log.w(TAG, "No Fragments available");
                    break;
            }
            return null;
        }

        @Override
        public int getCount() {
            return contactFrags.length;
        }
    }
}
