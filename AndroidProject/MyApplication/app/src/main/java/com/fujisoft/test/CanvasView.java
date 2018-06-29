package com.fujisoft.test;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

public class CanvasView extends SurfaceView implements SurfaceHolder.Callback,Runnable{

    // SurfaceHolder实例
    private SurfaceHolder surfaceHolder;
    // 控制子线程是否运行
    private boolean startDraw;
    // Path实例
    private Path lPath,rPath;
    private Paint lPaint,rPaint;
    private Canvas canvas;

    public interface TouchI{
        void rMoveTo(float x,float y);
        void rLineTo(float x,float y);
    }

    private TouchI touchI;

    public void setTouchI(TouchI touchI){
        this.touchI = touchI;
    }

    public CanvasView(Context context) {
        this(context,null);
    }

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //画布透明
        setBackgroundResource(R.drawable.touming);
        setZOrderOnTop(true);//使surfaceview放到最顶层
        getHolder().setFormat(PixelFormat.TRANSLUCENT);//使窗口支持透明度
        init();
    }

    private void init() {
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        lPaint = new Paint();
        //设置画笔类型，STROKE空心，FILL实心，FILL_AND_STROKE用契形填充
        lPaint.setStyle(Paint.Style.STROKE);
        lPaint.setStrokeWidth(5);
        lPaint.setColor(Color.GREEN);
        //抗锯齿
        lPaint.setAntiAlias(true);
        //防抖动
        lPaint.setDither(true);
        //画笔接洽点类型 如影响矩形但角的外轮廓
        lPaint.setStrokeJoin(Paint.Join.ROUND);
        //画笔笔刷类型 如影响画笔但始末端
        lPaint.setStrokeCap(Paint.Cap.ROUND);
        rPaint = new Paint();
        rPaint.setStyle(Paint.Style.STROKE);
        rPaint.setStrokeWidth(5);
        rPaint.setColor(Color.BLUE);
        lPath = new Path();
        rPath = new Path();
        canvas = new Canvas();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int lx = (int) event.getX();    //获取手指移动的x坐标
        int ly = (int) event.getY();    //获取手指移动的y坐标
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                lPath.moveTo(lx, ly);
                touchI.rMoveTo(lx,ly);
                break;

            case MotionEvent.ACTION_MOVE:
                lPath.lineTo(lx, ly);
                touchI.rLineTo(lx, ly);
                break;

            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
    private void draw() {
        try {
            canvas = surfaceHolder.lockCanvas();
//            canvas.drawColor(Color.WHITE);
            //画布透明
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);//绘制透明色
            canvas.drawPath(lPath, lPaint);
            canvas.drawPath(rPath, rPaint);
        } catch (Exception e) {
        } finally {
            // 对画布内容进行提交
            if (canvas != null) {
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        startDraw = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        startDraw = false;
    }

    @Override
    public void run() {
        // 如果不停止就一直绘制
        while (startDraw) {
            // 绘制
            draw();
        }
    }

    public void rPathMoveTo(float x,float y){
        rPath.moveTo(x,y);
    }

    public void rPathLineTo(float x,float y){
        rPath.lineTo(x,y);
    }

    public void clear(){
        surfaceHolder.addCallback(this);
        lPath = new Path();
        rPath = new Path();
    }
}
