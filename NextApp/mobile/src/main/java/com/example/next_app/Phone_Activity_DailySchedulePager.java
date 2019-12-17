package com.example.next_app;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.poliba.mylibrary.Schedule;

import java.util.Calendar;

public class Phone_Activity_DailySchedulePager extends FragmentActivity {
    String dayText;
    int pagerId=R.layout.phone_activity_schedulepager;
    private static final int NUM_PAGES =5;
    private ViewPager mPager;
    private PagerAdapter pagerAdapter;
    private Schedule schedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(pagerId);
        Bundle scheduleBundle = getIntent().getExtras();

        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager = findViewById(R.id.pager);
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mPager.setAdapter(pagerAdapter);
        
        TextView dayTextField = findViewById(R.id.dayText);
        dayTextField.setText(dayText);
    }

    
    public void setDay(int d){
        switch (d){
            case (Calendar.TUESDAY):
                dayText = getString(R.string.tuesday);
                break;
            case (Calendar.WEDNESDAY):
                dayText = getString(R.string.wednesday);
                break;
            case (Calendar.THURSDAY):
                dayText = getString(R.string.thursday);
                break;
            case (Calendar.FRIDAY):
                dayText = getString(R.string.friday);
                break;
            default:
                dayText = getString(R.string.monday);
                break;
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return new Phone_Fragment_DailySchedule();
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
    private class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0f);

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0f);
            }
        }
    }
}
