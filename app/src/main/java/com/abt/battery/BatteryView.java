package com.abt.battery;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.abt.R;

/**
 * @描述： @自定义水平电池控件
 * @作者： @黄卫旗
 * @创建时间： @2018-04-14
 */
public class BatteryView extends View {

    private int mPower = 100;
    private boolean mCharging = false;
    private int mOrientation;
    private int mWidth;
    private int mHeight;
    private int mColor;
    private Bitmap mBatteryBackground;
    private Bitmap mBatteryCharging;

    public BatteryView(Context context) {
        super(context);
    }

    public BatteryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Battery);
        mColor = typedArray.getColor(R.styleable.Battery_batteryColor, 0xFFFFFFFF);
        mOrientation = typedArray.getInt(R.styleable.Battery_batteryOrientation, 0);
        mPower = typedArray.getInt(R.styleable.Battery_batteryPower, 100);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mBatteryBackground = BitmapFactory.decodeResource(getResources(), R.drawable.power_empty);
        mBatteryCharging = BitmapFactory.decodeResource(getResources(), R.drawable.power_charging);
        /**
         * recycle() :官方的解释是：回收TypedArray，以便后面重用。在调用这个函数后，你就不能再使用这个TypedArray。
         * 在TypedArray后调用recycle主要是为了缓存。当recycle被调用后，这就说明这个对象从现在可以被重用了。
         *TypedArray 内部持有部分数组，它们缓存在Resources类中的静态字段中，这样就不用每次使用前都需要分配内存。
         */
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        mWidth = widthSize;
        mHeight = heightSize;
        if (MeasureSpec.AT_MOST == widthMode) {
            mWidth = mBatteryBackground.getWidth();
        }
        if (MeasureSpec.AT_MOST == heightMode) {
            mHeight = mBatteryBackground.getHeight();
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mOrientation == 0) {
            drawHorizontalBattery(canvas);
        }
    }

    /**
     * 绘制水平电池
     */
    private void drawHorizontalBattery(Canvas canvas) {
        if (mCharging) {
            canvas.drawBitmap(mBatteryCharging, 0, 0, null);  // 将bitmap绘制到画布上
            return;
        }

        Paint paint = new Paint();
        paint.setColor(mColor);
        paint.setStyle(Paint.Style.STROKE);
        float strokeWidth = mWidth / 20.f;
        paint.setStrokeWidth(strokeWidth);

        //画外边框
        canvas.drawBitmap(mBatteryBackground, 0, 0, null);  // 将bitmap绘制到画布上

        //画电池内矩形电量
        paint.setStrokeWidth(0);
        paint.setStyle(Paint.Style.FILL);
        float offset = (mWidth - strokeWidth * 2.5f) * mPower / 100.f;
        if (offset == 0) {
            offset = strokeWidth + 2; // 最小偏移量
        }
        RectF rect= new RectF(strokeWidth + 2, strokeWidth + 2, offset, mHeight - strokeWidth - 2);
        if (mPower < 20) {
            paint.setColor(Color.RED);
        } else {
            paint.setColor(Color.BLACK);
        }
        canvas.drawRect(rect, paint);//根据电池电量决定电池内矩形电量颜色
    }

    /**
     * 设置电池颜色
     */
    public void setColor(int color) {
        this.mColor = color;
        invalidate();
    }

    public void setBatteryCharge(boolean charging) {
        this.mCharging = charging;
        invalidate();
    }

    /**
     * 设置电池电量
     */
    public void setProgress(int power) {
        this.mPower = power;
        if (mPower < 0) {
            mPower = 0;
        } else if (mPower > 100) {
            mPower = 100;
        }
        invalidate();//刷新VIEW
    }

}
