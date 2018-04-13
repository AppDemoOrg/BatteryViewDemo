package com.abt.battery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.BatteryManager;
import android.util.AttributeSet;
import android.view.View;

import com.abt.R;
import com.abt.util.ResourceUtil;

/**
 * 自定义电池图标最终版
 */
public class BatteryView extends View {

    private int mMargin = 5;    //电池内芯与边框的距离
    private int mBoder = 4;     //电池外框的宽带
    private int mWidth = 70;    //总长
    private int mHeight = 40;   //总高
    private int mHeadWidth = 6;
    private int mHeadHeight = 10;

    private Bitmap mBatteryCharge;
    private Bitmap mBatteryBackground;
    private RectF mMainRect;
    private RectF mHeadRect;

    private float mRadius = 4f;   //圆角
    private float mPower;
    private boolean mIsCharging;    //是否在充电
    private int mPowerWidth;
    private int mPowerLeftPadding;
    private int mPowerRightPadding;

    public BatteryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();

        TypedArray array                    = context.obtainStyledAttributes(attrs, R.styleable.BatteryView);
        Drawable drawable                    = array.getDrawable(R.styleable.BatteryView_batteryBackground);
        BitmapDrawable bitmapDrawable	    = null;

        if (null != drawable) {
            bitmapDrawable 	    		   = (BitmapDrawable) drawable;
            mBatteryBackground 		       = bitmapDrawable.getBitmap();
        }

        drawable 						   = array.getDrawable(R.styleable.BatteryView_batteryChargeIcon);
        if(null != drawable){
            bitmapDrawable 	    		   = (BitmapDrawable) drawable;
            mBatteryCharge 			       = bitmapDrawable.getBitmap();
        }

        array.recycle();
        mPowerLeftPadding = ResourceUtil.getDimensionPixelOffset(R.dimen.dp_2);
        mPowerRightPadding = ResourceUtil.getDimensionPixelOffset(R.dimen.dp_2);
        mPowerWidth = mBatteryBackground.getWidth() - mPowerLeftPadding - mPowerRightPadding;
    }

    public BatteryView(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        mHeadRect = new RectF(0, (mHeight - mHeadHeight)/2, mHeadWidth, (mHeight + mHeadHeight)/2);
        float left = mHeadRect.width();
        float top = mBoder;
        float right = mWidth-mBoder;
        float bottom = mHeight-mBoder;
        mMainRect = new RectF(left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint1 = new Paint();

        //画电池头
        paint1.setStyle(Paint.Style.FILL);  //实心
        paint1.setColor(Color.WHITE);
        canvas.drawRect(mHeadRect, paint1);

        //画外框
        paint1.setStyle(Paint.Style.STROKE);    //设置空心矩形
        paint1.setStrokeWidth(mBoder);          //设置边框宽度
        paint1.setColor(Color.WHITE);
        canvas.drawRoundRect(mMainRect, mRadius, mRadius, paint1);

        //画电池芯
        Paint paint = new Paint();
        if (mIsCharging) {
            //paint.setColor(Color.GREEN);
            canvas.drawBitmap(mBatteryCharge, 0, 0, null);
            canvas.save();
        } else {
            if (mPower < 0.1) {
                paint.setColor(Color.RED);
            } else {
                paint.setColor(Color.WHITE);
            }
        }

        int width   = (int) (mPower * (mMainRect.width() - mMargin*2));
        int left    = (int) (mMainRect.right - mMargin - width);
        int right   = (int) (mMainRect.right - mMargin);
        int top     = (int) (mMainRect.top + mMargin);
        int bottom  = (int) (mMainRect.bottom - mMargin);
        Rect rect = new Rect(left,top,right, bottom);
        canvas.drawRect(rect, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = widthSize;
        int height = heightSize;
        if (MeasureSpec.AT_MOST == widthMode) {
            width = mBatteryBackground.getWidth();
        }
        if (MeasureSpec.AT_MOST == heightMode) {
            height = mBatteryBackground.getHeight();
        }
        setMeasuredDimension(width, height);
        //setMeasuredDimension(mWidth, mHeight);
    }

    private void setPower(float power) {
        mPower = power;
        invalidate();
    }

    private BroadcastReceiver mPowerConnectionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            mIsCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL;

            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

            setPower(((float) level)/scale);
        }
    };

    @Override
    protected void onAttachedToWindow() {
        getContext().registerReceiver(mPowerConnectionReceiver,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        getContext().unregisterReceiver(mPowerConnectionReceiver);
        super.onDetachedFromWindow();
    }
}
