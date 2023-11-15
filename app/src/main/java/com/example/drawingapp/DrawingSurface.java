package com.example.drawingapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class DrawingSurface extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder holder;
    private Callback callback;
    private GestureDetector gestureDetector;
    private float lastX, lastY;
    private Paint paintCircle;

    // 构造函数
    public DrawingSurface(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 获取SurfaceHolder，并添加回调
        holder = getHolder();
        holder.addCallback(this);

        // 设置可获得焦点，确保能够接收触摸事件
        setFocusable(true);

        // 初始化 GestureDetector
        gestureDetector = new GestureDetector(context, new GestureListener());

        // 初始化绘制圆圈的画笔
        paintCircle = new Paint();
        paintCircle.setColor(Color.WHITE);
        paintCircle.setStyle(Paint.Style.STROKE);
        paintCircle.setStrokeWidth(20);  // 圆圈的线宽
        paintCircle.setAntiAlias(true);  // 抗锯齿
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (callback != null) {
            // 设置画布背景颜色为淡绿色
            setBackgroundColor(Color.parseColor("#CDEAC0"));

            // 输出完成信号到控制台
            Log.d("DrawingSurface", "Surface created and background color set.");

            callback.onSurfaceCreated(this);
        } else {
            // 如果callback为null，输出一条警告
            Log.w("DrawingSurface", "Callback is null in surfaceCreated method.");
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
        // 处理SurfaceView尺寸变化的逻辑
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        // 处理SurfaceView销毁的逻辑
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        // 在画布上绘制圆圈
        canvas.drawCircle(lastX, lastY, 25, paintCircle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 通过 GestureDetector 处理触摸事件
        gestureDetector.onTouchEvent(event);

        // 处理移动事件
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            float currentX = event.getX();
            float currentY = event.getY();

            // 计算移动向量
            float deltaX = currentX - lastX;
            float deltaY = currentY - lastY;

            // 输出移动向量到控制台
            Log.d("DrawingSurface", "move (" + deltaX + ", " + deltaY + ")");

            lastX = currentX;
            lastY = currentY;

            // 请求重绘
            invalidate();
        }

        return true; // 返回true表示已处理该事件
    }

    // 设置回调
    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    // 绘图回调接口
    public interface Callback {
        void onSurfaceCreated(DrawingSurface surface);
        void onDraw(DrawingSurface surface);
        void onSingleClick(float x, float y);
        void onDoubleClick(float x, float y);
    }

    // GestureDetector 的 SimpleOnGestureListener 实现
    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            // 单击事件
            lastX = e.getX();
            lastY = e.getY();
            // 请求重绘
            invalidate();
            callback.onSingleClick(lastX, lastY);
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            // 双击事件
            lastX = e.getX();
            lastY = e.getY();
            // 请求重绘
            invalidate();
            callback.onDoubleClick(lastX, lastY);
            return true;
        }
    }
}