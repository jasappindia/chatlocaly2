package com.chatlocaly.views;

import android.content.Context;
import android.widget.ExpandableListView;

/**
 * Created by prateek on 8/2/17.
 */

public class ThreeLevelExpandableListView extends ExpandableListView {

    public ThreeLevelExpandableListView(Context context) {
        super(context);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //999999 is a size in pixels. ExpandableListView requires a maximum height in order to do measurement calculations.
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(999999, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}