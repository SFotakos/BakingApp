package com.sfotakos.foodsteps;

import android.graphics.Typeface;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ActivityUtils {


    public static void applyFontForToolbarTitle(Toolbar toolbar, Typeface typeface){
        for(int i = 0; i < toolbar.getChildCount(); i++){
            View view = toolbar.getChildAt(i);
            if(view instanceof TextView){
                TextView tv = (TextView) view;
                if(tv.getText().equals(toolbar.getTitle())){
                    tv.setTypeface(typeface);
                    break;
                }
            }
        }
    }
}
