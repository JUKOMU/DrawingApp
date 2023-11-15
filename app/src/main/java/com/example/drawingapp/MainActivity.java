package com.example.drawingapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private DrawingSurface drawingSurface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 获取 DrawingSurface
        drawingSurface = findViewById(R.id.drawingCanvas);

        // 设置SurfaceView的绘图回调
        drawingSurface.setCallback(new DrawingSurface.Callback() {
            @Override
            public void onSurfaceCreated(DrawingSurface surface) {
                // 输出加载完成信号到控制台
                Log.d("MainActivity", "Drawing surface created.");
                // 重绘一次，确保画布得以显示
                drawingSurface.invalidate();
            }

            @Override
            public void onDraw(DrawingSurface surface) {
                // 在这里处理绘制逻辑，例如绘制空白画布
            }

            @Override
            public void onSingleClick(float x, float y) {
                // 处理单击事件
                Log.d("MainActivity", "Single click at x: " + x + ", y: " + y);
            }

            @Override
            public void onDoubleClick(float x, float y) {
                // 处理双击事件
                Log.d("MainActivity", "Double click at x: " + x + ", y: " + y);
            }
        });

    }
}
