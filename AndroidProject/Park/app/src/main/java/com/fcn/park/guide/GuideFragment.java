package com.fcn.park.guide;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 引导页Fragment
 * Created by 860115001 on 2018/04/24.
 */

@SuppressLint("ValidFragment")
public class GuideFragment extends Fragment {
    private int imageId;

    @SuppressLint("ValidFragment")
    public GuideFragment(int imageId) {
        this.imageId = imageId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ImageView imageView = new ImageView(getActivity());
        LinearLayout.LayoutParams LayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setImageResource(imageId);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setLayoutParams(LayoutParams);
        View view= imageView;
        return view;
    }
}
