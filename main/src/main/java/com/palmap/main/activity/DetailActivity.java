package com.palmap.main.activity;

import com.google.inject.Inject;

import com.macrowen.macromap.draw.Shop;
import com.macrowen.macromap.utils.MapService;
import com.palmap.main.utils.ImageDownloader;
import com.palmap.main.utils.ImageDownloader.Mode;
import com.palmap.main.view.CirclePageIndicator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Nullable;
import android.widget.TextView;
import android.content.Intent;
import android.graphics.PointF;
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
        imageDownloader.download(urls[position], imageView);
      }
      container.addView(imageView);
      return imageView;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
      return arg0 == arg1;
    }

  }

  private String[] urls = {
      "http://lh5.ggpht.com/_mrb7w4gF8Ds/TCpetKSqM1I/AAAAAAAAD2c/Qef6Gsqf12Y/s144-c/_DSC4374%20copy.jpg",
      "http://lh5.ggpht.com/_Z6tbBnE-swM/TB0CryLkiLI/AAAAAAAAVSo/n6B78hsDUz4/s144-c/_DSC3454.jpg",
      "http://lh3.ggpht.com/_GEnSvSHk4iE/TDSfmyCfn0I/AAAAAAAAF8Y/cqmhEoxbwys/s144-c/_MG_3675.jpg",
      "http://lh6.ggpht.com/_Nsxc889y6hY/TBp7jfx-cgI/AAAAAAAAHAg/Rr7jX44r2Gc/s144-c/IMGP9775a.jpg" };

  private List<ImageView> images = new ArrayList<ImageView>();
  private final ImageDownloader imageDownloader = new ImageDownloader();

  @InjectView(tag = "pager_tag")
  @Nullable
  ViewPager mPager;
  @InjectView(tag = "indicator_tag")
  @Nullable
  CirclePageIndicator mIndicator;
  @InjectView(tag = "activity_detail_layout_shop_name_tag")
  @Nullable
  TextView mShopName;

  @Inject
  LayoutInflater layoutInflater;

  private MapService mMapService = MapService.getInstance();
  private String downLoadURL;

  private Runnable downloadThread = new Runnable() {

    @Override
    public void run() {

    }

  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);
    setTitle("展品详情");
    enableBack(true);
    imageDownloader.setMode(Mode.NO_DOWNLOADED_DRAWABLE);

    Intent intent = getIntent();
    String shopId = intent.getStringExtra("shopid");
    if (shopId != null && !(shopId.equals("") || shopId.equals("0"))) {
      HashMap<PointF, Shop> shops = mMapService.getCurFloor().getShops();
      for (Shop shop : shops.values()) {
        if (shop.getId().equals(shopId)) {
          mShopName.setText(shop.getName());
          break;
        }
      }
    }

    DetailImageAdapter adapter = new DetailImageAdapter();
    for (int i = 0; i < 4; i++) {
      ImageView view = (ImageView) layoutInflater.inflate(R.layout.imageview_layout, null);
      images.add(view);
    }

    mPager.setAdapter(adapter);

    mIndicator.setViewPager(mPager);
    mIndicator.setSnap(true);
  }

  private void downlaodJson(String mallid, String floorId) {
    downLoadURL = "http://apitest.palmap.cn/mall/" + mallid + "/floor/" + floorId;
    new Thread(downloadThread).start();
  }
}
