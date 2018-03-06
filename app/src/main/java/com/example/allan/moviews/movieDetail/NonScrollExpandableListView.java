package com.example.allan.moviews.movieDetail;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

/**
 * Created by Allan Pana on 06/03/18.
 * allan.pana74@gmail.com
 *
 * This class provide a solution to the scrolling behavior of ExpandableListView
 * inside NestedScrollView inside CoordinatorLayout
 *
 * https://stackoverflow.com/questions/38687303/expandablelistview-in-coordinatorlayout
 */

public class NonScrollExpandableListView extends ExpandableListView {

        public NonScrollExpandableListView(Context context) {
            super(context);
        }

        public NonScrollExpandableListView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public NonScrollExpandableListView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        @Override
        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int heightMeasureSpec_custom = MeasureSpec.makeMeasureSpec(
                    Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec_custom);
            ViewGroup.LayoutParams params = getLayoutParams();
            params.height = getMeasuredHeight();
        }
    }