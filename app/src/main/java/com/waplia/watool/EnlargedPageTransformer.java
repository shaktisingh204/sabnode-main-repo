package com.waplia.watool;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

public class EnlargedPageTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.8f;

    @Override
    public void transformPage(View page, float position) {
        int pageWidth = page.getWidth();
        if (position < -1) {  // Page is off-screen to the left
            page.setScaleY(MIN_SCALE);
        } else if (position <= 1) {  // Page is in the center
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            page.setScaleY(scaleFactor);
        } else {  // Page is off-screen to the right
            page.setScaleY(MIN_SCALE);
        }
    }
}
