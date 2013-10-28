package com.palmap.main.activity;

import com.macrowen.macromap.draw.Shop;

import java.util.List;

import javax.annotation.Nullable;

import android.graphics.PointF;

import roboguice.inject.InjectView;

import android.widget.ArrayAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

/** 品牌墙页面 BrandWallActivity */
public class BrandWallActivity extends PublicActivity {
  /** 跳转 */
  private Intent activity_brandwall_intent;
  /** 品牌墙页面 BrandWallActivity 展示品牌墙的GridView控件 */
  @InjectView(tag = "activity_brandwall_layout_gridview_tag")
  @Nullable
  private GridView activity_brandwall_layout_gridview;
  /** 品牌墙页面 BrandWallActivity 展示品牌墙的GridView的适配器 */
  // private BrandListAdapter activity_brandwall_layout_gridview_adapter;
  /** 品牌墙页面 BrandWallActivity 品牌墙资源 */
  private List<Shop> activity_brandwall_layout_gridview_list;

  /** 品牌墙 logo图片资源 */
  // private int activity_brandwall_layout_gridview_list_logo[] = {
  // R.drawable.logo_converse, R.drawable.logo_costa_coffee, R.drawable.logo_dairy_queen, R.drawable.logo_dairy_queen,
  // R.drawable.logo_emoi, R.drawable.logo_esprit, R.drawable.logo_folli_follie, R.drawable.logo_gap, R.drawable.logo_guess,
  // R.drawable.logo_haagen_dazs, R.drawable.logo_hm, R.drawable.logo_kfc, R.drawable.logo_moschino, R.drawable.logo_pizza_hut,
  // R.drawable.logo_prada, R.drawable.logo_sasa, R.drawable.logo_sephora, R.drawable.logo_shundian, R.drawable.logo_starbucks,
  // R.drawable.logo_suxie, R.drawable.logo_swarovski, R.drawable.logo_swatch, R.drawable.logo_vans, R.drawable.logo_versace,
  // R.drawable.logo_watsons, R.drawable.logo_zara, R.drawable.logo_zuczug, };

  /** ListView点击事件 */
  AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
      /** 跳转到详细界面 */
      activity_brandwall_intent = new Intent(BrandWallActivity.this, DetailActivity.class);
      activity_brandwall_intent.putExtra("floorid", mMapService.getFloorId());
      Shop shop = activity_brandwall_layout_gridview_list.get((int) id);
      activity_brandwall_intent.putExtra("shopid", "" + shop.getId());
      startActivity(activity_brandwall_intent);
    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_brandwall);
    setTitle("商铺列表");
    enableBack(true);
    this.activity_brandwall_intent = new Intent();
    this.setData();
  }

  /** 给GridView添加数据 */
  private void setData() {
    float x = 38 * 100;
    float y = 19 * 100;
    PointF position = mMapService.getPostition();
    if (position != null) {
      x = position.x;
      y = position.y;
    }
    activity_brandwall_layout_gridview_list = mMapService.getShopsByScope(x, y, 100);
    // for (int i = 0; i < activity_brandwall_layout_gridview_list_logo.length; i++) {
    // HashMap<String, Object> hm = new HashMap<String, Object>();
    // hm.put("logo", activity_brandwall_layout_gridview_list_logo[i]);
    // // hm.put("rating", Math.random() * 3 + "");
    // hm.put("rating1", R.drawable.icon_golden_star);
    // hm.put("rating2", R.drawable.icon_half_star);
    // hm.put("rating3", R.drawable.icon_gray_star);
    // activity_brandwall_layout_gridview_list.add(hm);
    // }

    /** 生成适配器的Item和动态数组对应的元素 */
    ArrayAdapter<Shop> simpleAdapter =
        new ArrayAdapter<Shop>(this, R.layout.item_brand_list, R.id.item_brand_list_shop_text, activity_brandwall_layout_gridview_list);
    // ListActivity simpleAdapter =
    // new ListActivity(this, activity_brandwall_layout_gridview_list, /** 数据源 */
    // R.layout.item_brand_list, /** ListItem的XML布局 */
    // new String[] { "logo", "rating1", "rating2", "rating3" }, new int[] {
    // R.id.brand_img, R.id.imageRating1, R.id.imageRating2, R.id.imageRating3 } /***/
    // );

    // activity_brandwall_layout_gridview_adapter = new BrandListAdapter(activity_brandwall_layout_gridview_list);
    /** 给GridView加适配器 */
    activity_brandwall_layout_gridview.setAdapter(simpleAdapter);
    activity_brandwall_layout_gridview.setOnItemClickListener(onItemClickListener);

  }
}
