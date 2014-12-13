package com.example.demo;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.util.Log;

public class TabListener<T extends Fragment> implements ActionBar.TabListener {
	private Fragment mFragment;  
    private final Activity mActivity;  
    private final String mTag;  
    private final Class<T> mClass;  
  
    public TabListener(Activity activity, String tag, Class<T> clz) {  
        mActivity = activity;  
        mTag = tag;  
        mClass = clz;  
    }  
  
    public void onTabSelected(Tab tab, FragmentTransaction ft) {  
        if (mFragment == null) {  
            mFragment = Fragment.instantiate(mActivity, mClass.getName());  
            ft.add(android.R.id.content, mFragment, mTag);  
        } else {  
            ft.attach(mFragment);  
        }  
    }  
  
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {  
        if (mFragment != null) {  
            ft.detach(mFragment);  
        }  
    }  
  
    public void onTabReselected(Tab tab, FragmentTransaction ft) {  
    }  

}