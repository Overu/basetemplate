package com.palmap.main.view;

import android.view.MotionEvent;

import android.util.AttributeSet;

import android.content.Context;

import android.widget.ScrollView;

public class ScrollViewExtend extends ScrollView {
  private float xDistance, yDistance, xLast, yLast;

  public ScrollViewExtend(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent ev) {
    switch (ev.getAction()) {
    case MotionEvent.ACTION_DOWN:
      xDistance = yDistance = 0f;
      xLast = ev.getX();
      yLast = ev.getY();
      break;
    case MotionEvent.ACTION_MOVE:
      final float curX = ev.getX();
      final float curY = ev.getY();

      xDistance += Math.abs(curX - xLast);
      yDistance += Math.abs(curY - yLast);
      xLast = curX;
      yLast = curY;

      if (xDistance > yDistance) {
        return false;
      }
    }

    return super.onInterceptTouchEvent(ev);
  }
}
