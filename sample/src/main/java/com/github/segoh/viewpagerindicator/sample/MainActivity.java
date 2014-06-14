/*
 * Copyright (C) 2014 Sebastian Gutsfeld
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.segoh.viewpagerindicator.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

import com.github.segoh.viewpagerindicator.ViewPagerIndicator;


public class MainActivity extends ActionBarActivity {

    private static final int NUM_PAGES = 5;

    private ViewPager mPager;
    private ViewPagerIndicator mPagerIndicator;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerIndicator = (ViewPagerIndicator) findViewById(R.id.pager_indicator);
        initPager();
    }

    private void initPager() {
        mPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        mPagerIndicator.setViewPager(mPager);
    }

    @Override
    public void onBackPressed() {
        final int currentItem = mPager.getCurrentItem();
        if (currentItem == 0) {
            super.onBackPressed();
        } else {
            mPager.setCurrentItem(currentItem - 1);
        }
    }

    private static class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(final FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(final int position) {
            return PageFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
