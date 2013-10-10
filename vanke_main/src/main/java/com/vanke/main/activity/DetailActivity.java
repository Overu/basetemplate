package com.vanke.main.activity;

import com.google.inject.Inject;

import com.vanke.main.utils.ImageDownloader;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import android.widget.ImageView;

import android.view.LayoutInflater;

import android.view.ViewGroup;

import android.view.View;

import android.support.v4.view.PagerAdapter;

import android.support.v4.view.ViewPager;

import roboguice.inject.InjectView;

import android.os.Bundle;

public class DetailActivity extends PublicActivity {

  public class DetailImageAdapter extends PagerAdapter {

    public DetailImageAdapter() {
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
      container.removeView(images.get(position));
    }

    @Override
    public int getCount() {
      return images.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
      ImageView imageView = images.get(position);
      if (imageView != null) {
      }
      container.addView(imageView);
      return imageView;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
      return arg0 == arg1;
    }

  }

  private List<ImageView> images = new ArrayList<ImageView>();
  private final ImageDownloader imageDownloader = new ImageDownloader();

  @InjectView(tag = "pager_tag")
  @Nullable
  ViewPager pager;

  @Inject
  LayoutInflater layoutInflater;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);
    setTitle("展品详情");
    enableBack(true);

    DetailImageAdapter adapter = new DetailImageAdapter();

    for (int i = 0; i < 4; i++) {
      ImageView view = (ImageView) layoutInflater.inflate(R.layout.imageview_layout, null);
      images.add(view);
      view.setBackgroundResource(R.drawable.shop_map);
    }

    pager.setAdapter(adapter);
  }
}
