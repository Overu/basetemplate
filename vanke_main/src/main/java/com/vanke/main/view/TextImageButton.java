package com.vanke.main.view;

import com.vanke.main.activity.R;

import android.graphics.Typeface;

import android.graphics.Color;

import android.content.res.TypedArray;

import android.graphics.Paint.Align;

import android.graphics.Paint;

import android.graphics.Canvas;

import android.content.Context;
import android.util.AttributeSet;

import android.widget.ImageButton;

public class TextImageButton extends ImageButton {

  private String mImageText = "";
  private int mTextColor = Color.WHITE;
  private float mTextX, mTextY, mTextSize;

  private Paint mPaint;

  public TextImageButton(Context context) {
    this(context, null);
  }

  public TextImageButton(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public TextImageButton(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    this.init(attrs, defStyle);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    canvas.drawText(mImageText, canvas.getWidth() / 2, (canvas.getHeight() / 2) + 45, mPaint);
  }

  private void init(AttributeSet attrs, int defStyle) {
    TypedArray typeArray = getContext().obtainStyledAttributes(attrs, R.styleable.TextImageButton, defStyle, 0);
    mTextColor = typeArray.getColor(R.styleable.TextImageButton_textcolor, mTextColor);
    mTextSize = typeArray.getFloat(R.styleable.TextImageButton_textSize, 30.0f);
    mTextX = typeArray.getFloat(R.styleable.TextImageButton_textX, 50.0f);
    mTextY = typeArray.getFloat(R.styleable.TextImageButton_textY, 100.0f);
    mImageText = typeArray.getString(R.styleable.TextImageButton_text);

    typeArray.recycle();

    mPaint = new Paint();
    mPaint.setTextAlign(Align.CENTER);
    mPaint.setColor(mTextColor);
    mPaint.setTextSize(mTextSize);
  }
}
