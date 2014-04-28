/*
 * Copyright (C) 2014 Sebastian Gutsfeld
 * Copyright (C) 2011 Patrik Akerfeldt
 * Copyright (C) 2011 Jake Wharton
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
package com.github.segoh.viewpagerindicator;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Show the currently displayed page of a ViewPager by drawing horizontal circles.
 */
public class ViewPagerIndicator extends View implements ViewPager.OnPageChangeListener {

    private ViewPager.OnPageChangeListener mPageChangeListener;
    private float mRadius;
    private final Paint mPaintPage = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mPaintCurrentPage = new Paint(Paint.ANTI_ALIAS_FLAG);
    private ViewPager mViewPager;
    private int mCurrentPage;

    public ViewPagerIndicator(final Context context) {
        this(context, null);
    }

    public ViewPagerIndicator(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPagerIndicator(final Context context,
                              final AttributeSet attrs,
                              final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (isInEditMode()) {
            return;
        }
        final Resources res = getResources();
        final int defaultCurrentPageColor = res.getColor(R.color.vpi__current_page);
        final int defaultPageColor = res.getColor(R.color.vpi__page);
        final float defaultRadius = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                4,
                res.getDisplayMetrics());
        final TypedArray a = context.obtainStyledAttributes(
                attrs,
                R.styleable.ViewPagerIndicator,
                defStyleAttr,
                0);
        if (a != null) {
            mPaintPage.setStyle(Paint.Style.FILL);
            mPaintPage.setColor(a.getColor(
                    R.styleable.ViewPagerIndicator_pageColor,
                    defaultPageColor));
            mPaintCurrentPage.setStyle(Paint.Style.FILL);
            mPaintCurrentPage.setColor(a.getColor(
                    R.styleable.ViewPagerIndicator_currentPageColor,
                    defaultCurrentPageColor));
            mRadius = a.getDimension(R.styleable.ViewPagerIndicator_radius, defaultRadius);
            Drawable background = a.getDrawable(R.styleable.ViewPagerIndicator_android_background);
            setBackgroundDrawable(background);
            a.recycle();
        }
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        if (mViewPager == null || mViewPager.getAdapter() == null) {
            return;
        }
        final int count = mViewPager.getAdapter().getCount();
        if (count == 0) {
            return;
        }
        if (mCurrentPage >= count) {
            setCurrentItem(count - 1);
            return;
        }

        // Draw circles for each page
        final float widthCircleWithSpacing = mRadius * 4;
        final float offsetFromTop = getMeasuredHeight() / 2f;
        final float offsetFromLeft = getPaddingLeft() + mRadius;
        for (int i = 0; i < count; ++i) {
            final float centerLeft = offsetFromLeft + (i * widthCircleWithSpacing);
            canvas.drawCircle(centerLeft, offsetFromTop, mRadius, mPaintPage);
        }

        // Draw circle for current page
        final float offsetForCircle = mCurrentPage * widthCircleWithSpacing;
        canvas.drawCircle(offsetFromLeft + offsetForCircle, offsetFromTop, mRadius, mPaintCurrentPage);
    }

    public void setViewPager(final ViewPager view) {
        if (mViewPager == view) {
            return;
        }
        if (mViewPager != null) {
            mViewPager.setOnPageChangeListener(null);
        }
        if (view.getAdapter() == null) {
            throw new IllegalStateException("Set an adapter instance on the ViewPager.");
        }
        mViewPager = view;
        mViewPager.setOnPageChangeListener(this);
        invalidate();
    }

    public void setCurrentItem(final int item) {
        if (mViewPager == null) {
            throw new IllegalStateException("Set the ViewPager first.");
        }
        mViewPager.setCurrentItem(item);
        mCurrentPage = item;
        invalidate();
    }

    public void setOnPageChangeListener(final ViewPager.OnPageChangeListener listener) {
        this.mPageChangeListener = listener;
    }

    @Override
    public void onPageScrolled(final int position,
                               final float positionOffset,
                               final int positionOffsetPixels) {
        mCurrentPage = position;
        invalidate();
        if (mPageChangeListener != null) {
            mPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(final int position) {
        mCurrentPage = position;
        invalidate();
        if (mPageChangeListener != null) {
            mPageChangeListener.onPageSelected(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(final int state) {
        if (mPageChangeListener != null) {
            mPageChangeListener.onPageScrollStateChanged(state);
        }
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(final int measureSpec) {
        final int specMode = MeasureSpec.getMode(measureSpec);
        final int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY || mViewPager == null) {
            return specSize;
        }
        final int count = mViewPager.getAdapter().getCount();
        final float roomForCircles = 2 * count * mRadius;
        final float roomBetweenCircles = 2 * (count - 1) * mRadius;
        final int width = (int) (getPaddingLeft() + getPaddingRight()
                + roomForCircles
                + roomBetweenCircles
                + 1);
        return specMode == MeasureSpec.AT_MOST ? Math.min(width, specSize) : width;
    }

    private int measureHeight(final int measureSpec) {
        final int specMode = MeasureSpec.getMode(measureSpec);
        final int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            return specSize;
        }
        final int height = (int) (2 * mRadius + getPaddingTop() + getPaddingBottom() + 1);
        return specMode == MeasureSpec.AT_MOST ? Math.min(height, specSize) : height;
    }

    @Override
    public void onRestoreInstanceState(final Parcelable state) {
        final SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        mCurrentPage = savedState.currentPage;
        requestLayout();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        final Parcelable superState = super.onSaveInstanceState();
        final SavedState savedState = new SavedState(superState);
        savedState.currentPage = mCurrentPage;
        return savedState;
    }


    public static class SavedState extends BaseSavedState {

        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(final Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(final int size) {
                return new SavedState[size];
            }
        };

        int currentPage;

        public SavedState(final Parcelable superState) {
            super(superState);
        }

        private SavedState(final Parcel in) {
            super(in);
            currentPage = in.readInt();
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(currentPage);
        }
    }
}
