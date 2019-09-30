package com.yf.douyintool;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @Author: zhaoyoucheng
 * @Date: 2019/9/27 9:27
 * @Description:
 */
class DouyinPagerAdapter extends PagerAdapter {

    private List<View> viewList;

    public DouyinPagerAdapter(List<View> viewList) {
        this.viewList = viewList;
    }

    public DouyinPagerAdapter() {
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return false;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v=viewList.get(position);
        ViewGroup parent = (ViewGroup) v.getParent();
        if (parent != null) {
            parent.removeAllViews();
        }
        container.addView(viewList.get(position));

        return viewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position));
    }

}
