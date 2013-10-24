package com.palmap.main.view;

import android.widget.TextSwitcher;

import android.widget.Scroller;

import android.R;

import android.content.Context;
import android.util.AttributeSet;

import android.widget.TextView;

public class ScrollTextView extends TextView {

  private Scroller mScroller;

  public ScrollTextView(Context context) {
    this(context, null);
  }

  public ScrollTextView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public ScrollTextView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    this.init();
  }

  @Override
  public void computeScroll() {
    if (mScroller.computeScrollOffset()) {

    }
    super.computeScroll();
  }

  private void init() {

  }

}
