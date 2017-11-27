package com.daikin.intelligentNewLifeMulti.app.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.daikin.intelligentNewLifeMulti.app.DSAIRApplication;
import com.daikin.intelligentNewLifeMulti.app.R;
import com.daikin.intelligentNewLifeMulti.app.activity.BaseActivity;
import com.daikin.intelligentNewLifeMulti.app.util.LogUtils;

/**
 * Created by 111 on 2017/4/7 0007.
 */
public class CustomCircleView extends View {
    private static final String TAG = "CustomCircleView";
    //自定义view类型
    private String mType;
    //圆弧画笔，点的画笔，指示圆画笔，进度弧画笔,四点文字画笔，提示圈内文字画笔,指针三角形画笔,显示文本画笔
    private Paint mCirclePaint, mPointPaint, mTipPaint, mProcessPaint, mTextPaint, mTipTextPaint, mTrianglePaint, mShowTextPaint;
    private String mTipText, mVocText="";
    //圆弧半径
    private int mCircleRadius;
    //进度弧填充色
    private int mFillColor = Color.LTGRAY;
    //屏幕宽度
    private int screenWidth;
    //屏幕高度
    private int screenHeight;
    //圆点X
    private int mPointX;
    //圆点Y
    private int mPointY;
    //拱形
    private RectF rect;
    private int tempSensorVal;//温度原始值
    private int tempSensorValStep;//温度原始值
    //传感器传过来的值
    private int mSensorVal;
    //左下点值
    private int mLeftDownVal;
    //左上点值
    private int mLeftUpVal;
    //右上点值
    private int mRightUpVal;
    //右下点值
    private int mRightDownVal;
    //旋转角度
    private float mRotateAngle = -10000;

    private boolean isReset=false;

    private Context mContext;
    private int mStepVal;
    private int mSetVal;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (mSensorVal < mSetVal) {
                        mSensorVal = mSensorVal + mStepVal;
                        if (mSensorVal > mSetVal) {
                            mSensorVal = mSetVal;
                        }
                        mHandler.sendEmptyMessageDelayed(1, 20);
                    }
                    break;
                case 2:
                    if (mSensorVal > mSetVal) {
                        mSensorVal = mSensorVal + mStepVal;
                        if (mSensorVal < mSetVal) {
                            mSensorVal = mSetVal;
                        }
                        mHandler.sendEmptyMessageDelayed(2, 20);
                    }
                    break;
                case 3:
                    if(mContext.getString(R.string.temperature).equals(mType)){
                        LogUtils.e("mSetVal++++++>"+mSetVal);
                    }
                    if (mSensorVal < mSetVal) {
                        mSensorVal = mSensorVal + mStepVal;
                        tempSensorValStep += ((mSetVal/10 + 20) / 10);
                        if (mSensorVal >= mSetVal) {
                            mSensorVal = mSetVal;
                            tempSensorValStep = tempSensorVal;
                        }
                        mHandler.sendEmptyMessageDelayed(3, 20);
                    } else {
                        mSensorVal = mSetVal;
                        tempSensorValStep = tempSensorVal;
                    }
                    break;
            }
            initView();
            invalidate();
        }
    };

    private DSAIRApplication mApplication = ((BaseActivity) getContext()).mApplication;

    public CustomCircleView(Context context) {
        this(context, null);
    }

    public CustomCircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomCircleView);
        mType = ta.getString(R.styleable.CustomCircleView_type);
        mContext = context;

        if (mContext.getString(R.string.temperature).equals(mType)) {
            mSensorVal = -20;
            tempSensorValStep = -200;
        }

        initView();
    }


    /**
     * 初始化
     */
    private void initView() {
        //根据空间不同，设置四个点显示值
        if (mContext.getString(R.string.temperature).equals(mType)) {
            mLeftDownVal = 0;
            switch (mApplication.getMode()) {
                case 1:
                    mLeftUpVal = 220;
                    mRightUpVal = 280;
                    break;
                case 2:
                    mLeftUpVal = 180;
                    mRightUpVal = 240;
                    break;
                case 3:
                    mLeftUpVal = 180;
                    mRightUpVal = 280;
                    break;
                default:
                    mLeftUpVal = 180;
                    mRightUpVal = 280;
            }
            mRightDownVal = 400;
        } else if (mContext.getString(R.string.humidity).equals(mType)) {
            mLeftDownVal = 0;
            switch (mApplication.getMode()) {
                case 1:
                    mLeftUpVal = 40;
                    mRightUpVal = 70;
                    break;
                case 2:
                    mLeftUpVal = 30;
                    mRightUpVal = 60;
                    break;
                case 3:
                    mLeftUpVal = 31;
                    mRightUpVal = 70;
                    break;
                default:
                    mLeftUpVal = 31;
                    mRightUpVal = 70;
            }
            mRightDownVal = 100;
        } else if (mContext.getString(R.string.pm2_5).equals(mType)) {
            mLeftDownVal = 0;
            mLeftUpVal = 35;
            mRightUpVal = 75;
            mRightDownVal = 300;
        } else if (mContext.getString(R.string.co2).equals(mType)) {
            mLeftDownVal = 400;
            mLeftUpVal = 1000;
            mRightUpVal = 2000;
            mRightDownVal = 3000;
        }
        LogUtils.d(TAG, "initView: --------------------------------");
        LogUtils.d(TAG, "initView: mType:" + mType);
        LogUtils.d(TAG, "initView: mSensorVal 000000000:" + mSensorVal);
        //计算旋转角度
        if (mContext.getString(R.string.voc).equals(mType)) {
            mRotateAngle = -10000;
        } else {
            if(mContext.getString(R.string.temperature).equals(mType)){
                LogUtils.e("mSensorVal--->"+mRotateAngle+"mRotateAngle----->"+mRotateAngle);
            }
            if (mSensorVal < mLeftDownVal) {
                mRotateAngle = -1f;
            } else if (mSensorVal >= mLeftDownVal && mSensorVal < mLeftUpVal) {
                mRotateAngle = (mSensorVal - mLeftDownVal) * 90f / (mLeftUpVal - mLeftDownVal);
            } else if (mSensorVal >= mLeftUpVal && mSensorVal < mRightUpVal) {
                mRotateAngle = (mSensorVal - mLeftUpVal) * 90f / (mRightUpVal - mLeftUpVal) + 90f;
            } else if (mSensorVal >= mRightUpVal && mSensorVal <= mRightDownVal) {
                mRotateAngle = (mSensorVal - mRightUpVal) * 90f / (mRightDownVal - mRightUpVal) + 180f;
            } else if (mSensorVal > mRightDownVal) {
                mRotateAngle = 270f;
            }else if(mSetVal==-1000){
                mRotateAngle=-10000f;
            }

            if(mContext.getString(R.string.temperature).equals(mType)){
                LogUtils.e("mSensorVal===>"+mRotateAngle+"mRotateAngle====>"+mRotateAngle);
            }
        }
//        LogUtils.e(TAG, "initView: mSensorVal 1111111111:" +mType + mSetVal);
//        LogUtils.e(TAG, "initView: --------------------------------");
        //填充色
        if (mContext.getString(R.string.temperature).equals(mType)) {
//            if (mRotateAngle >= 0 && mRotateAngle < 90) {
//                //寒冷
//                mFillColor = 0xFF56D7FF;
//                mTipText = mContext.getString(R.string.cold);
//            } else if (mRotateAngle >= 90 && mRotateAngle <= 180) {
//                //舒适
//                mFillColor = 0xFFB2F74E;
//                mTipText = mContext.getString(R.string.comfort);
//            } else if (mRotateAngle > 180 && mRotateAngle <= 270) {
//                //炎热
//                mFillColor = 0xFFFD4648;
//                mTipText = mContext.getString(R.string.hot);
//            }
            int tempSensorValEqu = mSensorVal;
            if (tempSensorValEqu <= mLeftUpVal) {
                if (mApplication.getMode() == 1) {
                    //寒冷
                    mFillColor = 0xFFB2F74E;
                } else {
                    //寒冷
                    mFillColor = 0xFF56D7FF;
                }

                mTipText = mContext.getString(R.string.cold);
            } else if (tempSensorValEqu > mLeftUpVal && tempSensorValEqu < mRightUpVal) {
                if (mApplication.getMode() == 2) {
                    //舒适
                    mFillColor = 0xFFFFC04F;
                } else {
                    //舒适
                    mFillColor = 0xFFB2F74E;
                }
                mTipText = mContext.getString(R.string.comfort);
            } else if (tempSensorValEqu >=mRightUpVal) {
                //炎热
                mFillColor = 0xFFFFC04F;
                mTipText = mContext.getString(R.string.hot);
            }
        } else if (mContext.getString(R.string.humidity).equals(mType)) {
            if (mRotateAngle >= 0 && mRotateAngle < 90) {
                //干燥
                mFillColor = 0xFF56D7FF;
                mTipText = mContext.getString(R.string.dry);
            } else if (mRotateAngle >= 90 && mRotateAngle < 180) {
                //舒适
                mFillColor = 0xFF56D7FF;
                mTipText = mContext.getString(R.string.comfort);
            } else if (mRotateAngle >= 180 && mRotateAngle <= 270) {
                //潮湿
                mFillColor = 0xFF56D7FF;
                mTipText = mContext.getString(R.string.damp);
            }else if(mRotateAngle ==-1) {
                //干燥
                mFillColor = 0xFF56D7FF;
                mTipText = mContext.getString(R.string.dry);
            }
        } else if (mContext.getString(R.string.pm2_5).equals(mType) || mContext.getString(R.string.co2).equals(mType)) {
            if (mRotateAngle >= 0 && mRotateAngle < 90) {
                //优
                mFillColor = 0xFFB2F74E;
                mTipText = mContext.getString(R.string.great);
            } else if (mRotateAngle >= 90 && mRotateAngle < 180) {
                //良
                mFillColor = 0xFFFFC04F;
                mTipText = mContext.getString(R.string.good);
            } else if (mRotateAngle >= 180 && mRotateAngle <= 270) {
                //差
                mFillColor = 0xFFFD4648;
                mTipText = mContext.getString(R.string.bad);
            }else if(mRotateAngle ==-1) {
                //优
                mFillColor = 0xFFB2F74E;
                mTipText = mContext.getString(R.string.great);
            }
        } else if (mContext.getString(R.string.voc).equals(mType)) {
            if (mSensorVal == 1) {
                mRotateAngle = 90;
                mVocText = mContext.getString(R.string.low);
                mFillColor = 0xFFB2F74E;
            } else if (mSensorVal == 2) {
                mRotateAngle = 180;
                mVocText = mContext.getString(R.string.medium);
                mFillColor = 0xFFFFC04F;
            } else if (mSensorVal == 4) {
                mRotateAngle = 270;
                mVocText = mContext.getString(R.string.high);
                mFillColor = 0xFFFD4648;
            }
        }

        //圆弧
        mCirclePaint = new Paint();
        mCirclePaint.setColor(ContextCompat.getColor(getContext(), R.color.forty_percent_white));
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeCap(Paint.Cap.ROUND);//实现末端圆弧
        //画四个点
        mPointPaint = new Paint();
        mPointPaint.setAntiAlias(true);
        //指示圆
        mTipPaint = new Paint();
        mTipPaint.setAntiAlias(true);
        mTipPaint.setColor(mFillColor);
        //填充弧
        mProcessPaint = new Paint();
        mProcessPaint.setColor(mFillColor);
        mProcessPaint.setAntiAlias(true);
        mProcessPaint.setStyle(Paint.Style.STROKE);
        mProcessPaint.setStrokeCap(Paint.Cap.ROUND);//实现末端圆弧

        //四点文字画笔
        mTextPaint = new Paint();
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setAntiAlias(true);


        //提示圈内文字画笔
        mTipTextPaint = new Paint();
        mTipTextPaint.setColor(Color.WHITE);
        mTipTextPaint.setTextAlign(Paint.Align.CENTER);
        mTipTextPaint.setAntiAlias(true);

        //指针三角形画笔
        mTrianglePaint = new Paint();
        mTrianglePaint.setColor(mFillColor);
        mTrianglePaint.setAntiAlias(true);
        mTrianglePaint.setStyle(Paint.Style.FILL);

        //显示文本画笔
        mShowTextPaint = new Paint();
        mShowTextPaint.setTextAlign(Paint.Align.CENTER);
        mShowTextPaint.setAntiAlias(true);
        mShowTextPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(isReset){
            circleArc(canvas);
            isReset=false;
            return;
        }

        //画圆弧
        circleArc(canvas);
        if (mContext.getString(R.string.voc).equals(mType)) {
            //voc 画低中高
            threeText2(canvas);
            //画四个点
            fourPoint(canvas);
        } else {
            //画四个点
            fourPoint(canvas);
            if (mContext.getString(R.string.pm2_5).equals(mType) || mContext.getString(R.string.co2).equals(mType)) {
                //画优良差
                threeText(canvas);
            } else {
                //指示圆,指针三角形
//                tipCircle(canvas);
            }
        }
        if (mRotateAngle >= 0) {
            //进度弧
            processArc(canvas);
        }
        //文本显示
        showText(canvas);
    }

    /**
     * voc 画优良差
     */
    private void threeText(Canvas canvas) {
        Paint p = new Paint();
        if (mRotateAngle < 0) {
            p.setColor(ContextCompat.getColor(getContext(), R.color.forty_percent_white));
        } else {
            p.setColor(ContextCompat.getColor(getContext(), R.color.white));
        }
        p.setAntiAlias(true);
        p.setTextSize(screenWidth / 30);
        p.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(mContext.getString(R.string.great), mPointX - mCircleRadius - screenWidth / 18f, mPointY, p);
        p.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(mContext.getString(R.string.good), mPointX, mPointY - mCircleRadius - screenWidth / 40, p);
        p.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(mContext.getString(R.string.bad), mPointX + mCircleRadius + screenWidth / 18f, mPointY, p);
    }

    /**
     * voc 画低中高
     */
    private void threeText2(Canvas canvas) {
        Paint p = new Paint();
        if (mRotateAngle < 0) {
            p.setColor(ContextCompat.getColor(getContext(), R.color.forty_percent_white));
        } else {
            p.setColor(ContextCompat.getColor(getContext(), R.color.white));
        }
        p.setAntiAlias(true);
        p.setTextSize(screenWidth / 30);
        p.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(mContext.getString(R.string.low), mPointX - mCircleRadius - screenWidth / 18f, mPointY, p);
        p.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(mContext.getString(R.string.medium), mPointX, mPointY - mCircleRadius - screenWidth / 40, p);
        p.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(mContext.getString(R.string.high), mPointX + mCircleRadius + screenWidth / 18f, mPointY, p);
    }

    /**
     * 中间文本显示
     */
    private void showText(Canvas canvas) {
        if (mContext.getString(R.string.temperature).equals(mType)) {
            if (mRotateAngle >= -1) {
                mShowTextPaint.setTextSize(screenWidth / 15);
                String text = String.valueOf(tempSensorValStep / 10.0);
                canvas.drawText(text, mPointX - screenWidth / 40, mPointY + screenWidth / 40, mShowTextPaint);

                //画摄氏度
                Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
                p.setColor(Color.WHITE);
                p.setTextSize(screenWidth / 25);
                // 获取文本的宽度
                float width = mShowTextPaint.measureText(text);
                // 纵向中心高度调整值
                float height = (mShowTextPaint.descent() - mShowTextPaint.ascent()) / 2 - (p.descent() - p.ascent()) / 2;
                canvas.drawText("℃", (mPointX - screenWidth / 40) + width / 2, mPointY + screenWidth / 40 - height, p);
            }
            mShowTextPaint.setTextSize(screenWidth / 25f);
            canvas.drawText(mType, mPointX, mPointY + mCircleRadius + screenWidth / 45, mShowTextPaint);
        } else if (mContext.getString(R.string.humidity).equals(mType)) {
            if (mRotateAngle >= -1) {
                mShowTextPaint.setTextSize(screenWidth / 12);
                canvas.drawText(String.valueOf(mSensorVal) + "%", mPointX, mPointY + screenWidth / 45, mShowTextPaint);
            }
            mShowTextPaint.setTextSize(screenWidth / 25f);
            canvas.drawText(mType, mPointX, mPointY + mCircleRadius + screenWidth / 45, mShowTextPaint);
        } else if (mContext.getString(R.string.pm2_5).equals(mType) || mContext.getString(R.string.co2).equals(mType)) {
            if (mRotateAngle >= -1) {
                mShowTextPaint.setTextSize(screenWidth / 12);
//                canvas.drawText(String.valueOf(mSensorVal), mPointX, mPointY + screenWidth / 45, mShowTextPaint);
                mShowTextPaint.setColor(mFillColor);
                canvas.drawText(mTipText, mPointX, mPointY + screenWidth / 45, mShowTextPaint);
                mShowTextPaint.setColor(ContextCompat.getColor(getContext(), R.color.white));
            }
//            mShowTextPaint.setTextSize(screenWidth / 30f);
//            canvas.drawText(mContext.getString(R.string.pm2_5).equals(mType) ? mContext.getString(R.string.pm2_5_unit) : mContext.getString(R.string.co2_unit), mPointX, mPointY + mCircleRadius - screenWidth / 45, mShowTextPaint);
            mShowTextPaint.setTextSize(screenWidth / 22f);
            canvas.drawText(mType, mPointX, mPointY + mCircleRadius + screenWidth / 45, mShowTextPaint);
        } else if (mContext.getString(R.string.voc).equals(mType)) {
            if (mRotateAngle >= -1) {
                mShowTextPaint.setTextSize(screenWidth / 12);
                mShowTextPaint.setColor(mFillColor);
                canvas.drawText(mVocText, mPointX, mPointY + screenWidth / 45, mShowTextPaint);
                mShowTextPaint.setColor(Color.WHITE);
            }
            mShowTextPaint.setTextSize(screenWidth / 22f);
            canvas.drawText(mType, mPointX, mPointY + mCircleRadius + screenWidth / 45, mShowTextPaint);
        }
        if (mRotateAngle == -10000) {
            mShowTextPaint.setTextSize(screenWidth / 30);
            mShowTextPaint.setColor(ContextCompat.getColor(getContext(), R.color.forty_percent_white));
            canvas.drawText(mContext.getString(R.string.detecting), mPointX, mPointY + screenWidth / 50, mShowTextPaint);
            mShowTextPaint.setColor(Color.WHITE);
        }
    }


    /**
     * 进度弧
     */
    private void processArc(Canvas canvas) {
        mProcessPaint.setStrokeWidth(screenWidth / 36);
        if(mRotateAngle<0){
            canvas.drawArc(rect, 135f, 0f, false, mProcessPaint);
        }else {
            canvas.drawArc(rect, 135f, mRotateAngle, false, mProcessPaint);
        }
    }

    /**
     * 指示圆
     */
//    private void tipCircle(Canvas canvas) {
//        if (mRotateAngle < 0) {
//            return;
//        }
////        LogUtils.d(TAG, "tipCircle:mRotateAngle: "+mRotateAngle);
//        int curAngle = mRotateAngle - 225;
//        int circleX = (int) (mPointX + (mCircleRadius + screenWidth / 14f) * Math.cos(curAngle * Math.PI / 180));
//        int circleY = (int) (mPointY + (mCircleRadius + screenWidth / 14f) * Math.sin(curAngle * Math.PI / 180));
//        int point1X = (int) (mPointX + (mCircleRadius + screenWidth / 40f) * Math.cos(curAngle * Math.PI / 180));
//        int point1Y = (int) (mPointY + (mCircleRadius + screenWidth / 40f) * Math.sin(curAngle * Math.PI / 180));
//        int point2X = (int) (mPointX + (mCircleRadius + screenWidth / 28f) * Math.cos((curAngle - 3) * Math.PI / 180));
//        int point2Y = (int) (mPointY + (mCircleRadius + screenWidth / 28f) * Math.sin((curAngle - 3) * Math.PI / 180));
//        int point3X = (int) (mPointX + (mCircleRadius + screenWidth / 28f) * Math.cos((curAngle + 3) * Math.PI / 180));
//        int point3Y = (int) (mPointY + (mCircleRadius + screenWidth / 28f) * Math.sin((curAngle + 3) * Math.PI / 180));
//        int textOffY = screenWidth / 72;
//        canvas.drawCircle(circleX, circleY, screenWidth / 25, mTipPaint);
//        //画三角形
//        Path path = new Path();
//        path.moveTo(point1X, point1Y);
//        path.lineTo(point2X, point2Y);
//        path.lineTo(point3X, point3Y);
//        path.close();
//        canvas.drawPath(path, mTrianglePaint);
//        mTipTextPaint.setTextSize(screenWidth / 32);
//        canvas.drawText(mTipText, circleX, circleY + textOffY, mTipTextPaint);
//    }

    /**
     * 画四个点
     */
    private void fourPoint(Canvas canvas) {
        float leftX = (float) (mPointX - mCircleRadius / Math.sqrt(2)) - screenWidth / 50;
        float rightX = (float) (mPointX + mCircleRadius / Math.sqrt(2)) + screenWidth / 50;
        float upY = (float) (mPointY - mCircleRadius / Math.sqrt(2)) - screenWidth / 50;
        float downY = (float) (mPointY + mCircleRadius / Math.sqrt(2)) + screenWidth / 50;
        int r = (int) (screenWidth / 145);

        mPointPaint.setColor(ContextCompat.getColor(getContext(), R.color.forty_percent_white));//先默认点透明白
//        if (!mContext.getString(R.string.voc).equals(mType)) {
            //画左下和右下两个点
            canvas.drawCircle(leftX, downY, r, mPointPaint);
            canvas.drawCircle(rightX, downY, r, mPointPaint);
//        }
        //画左上和右上两个点
        drawLeftUpPoint(canvas, leftX, upY, r, mPointPaint);
        drawRightUpPoint(canvas, rightX, upY, r, mPointPaint);
//        canvas.drawCircle(leftX, upY, r, mPointPaint);
//        canvas.drawCircle(rightX, upY, r, mPointPaint);


        mPointPaint.setColor(mFillColor);//再画不同颜色
        //画左下100%亮
        if (mRotateAngle != -10000) {
            canvas.drawCircle(leftX, downY, r, mPointPaint);
        }

        if (mRotateAngle <0) {
            mPointPaint.setColor(ContextCompat.getColor(getContext(), R.color.forty_percent_white));
        } else if (mRotateAngle >= 0 && mRotateAngle < 90) {
//            if (!mContext.getString(R.string.voc).equals(mType)) {
                canvas.drawCircle(leftX, downY, r, mPointPaint);
//            }
        } else if (mRotateAngle >= 90 && mRotateAngle < 180) {
//            if (!mContext.getString(R.string.voc).equals(mType)) {
                canvas.drawCircle(leftX, downY, r, mPointPaint);
//            }
            drawLeftUpPoint(canvas, leftX, upY, r, mPointPaint);
//            canvas.drawCircle(leftX, upY, r, mPointPaint);
        } else if (mRotateAngle >= 180 && mRotateAngle < 270) {
//            if (!mContext.getString(R.string.voc).equals(mType)) {
                canvas.drawCircle(leftX, downY, r, mPointPaint);
//            }
            drawLeftUpPoint(canvas, leftX, upY, r, mPointPaint);
//            canvas.drawCircle(leftX, upY, r, mPointPaint);
            drawRightUpPoint(canvas, rightX, upY, r, mPointPaint);
//            canvas.drawCircle(rightX, upY, r, mPointPaint);
        } else {
//            if (!mContext.getString(R.string.voc).equals(mType)) {
                canvas.drawCircle(leftX, downY, r, mPointPaint);
                canvas.drawCircle(rightX, downY, r, mPointPaint);
//            }
            drawLeftUpPoint(canvas, leftX, upY, r, mPointPaint);
//            canvas.drawCircle(leftX, upY, r, mPointPaint);
            drawRightUpPoint(canvas, rightX, upY, r, mPointPaint);
//            canvas.drawCircle(rightX, upY, r, mPointPaint);
        }

        int offLeftX = screenWidth / 215;
        int offUpY = screenWidth / 54;
        int offRightX = screenWidth / 215;
        int offDownY = screenWidth / 25;
        mTextPaint.setTextSize(screenWidth / 27);//TODO
        if (mRotateAngle < -1) {
            mTextPaint.setColor(ContextCompat.getColor(getContext(), R.color.forty_percent_white));
        }
        if (mContext.getString(R.string.temperature).equals(mType)) {
            mTextPaint.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText(String.valueOf(mLeftDownVal/10), leftX - offLeftX, downY + offDownY, mTextPaint);
            setDegreesCelsius(false, mLeftDownVal/10, canvas, leftX - offLeftX, downY + offDownY);

            //制冷季节不显示
            if (mApplication.getMode() != 1) {
                canvas.drawText(String.valueOf(mLeftUpVal/10), leftX - offLeftX, upY - offUpY, mTextPaint);
                setDegreesCelsius(false, mLeftUpVal/10, canvas, leftX - offLeftX, upY - offUpY);
            }

            mTextPaint.setTextAlign(Paint.Align.LEFT);
            //制热季节不显示
            if (mApplication.getMode() != 2) {
                canvas.drawText(String.valueOf(mRightUpVal/10), rightX + offRightX, upY - offUpY, mTextPaint);
                setDegreesCelsius(true, mRightUpVal/10, canvas, rightX + offRightX, upY - offUpY);
            }

            canvas.drawText(String.valueOf(mRightDownVal/10), rightX + offRightX, downY + offDownY, mTextPaint);
            setDegreesCelsius(true, mRightDownVal/10, canvas, rightX + offRightX, downY + offDownY);
        } else if (mContext.getString(R.string.humidity).equals(mType)) {
            mTextPaint.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText(String.valueOf(mLeftDownVal) + "%", leftX - offLeftX, downY + offDownY, mTextPaint);
//            canvas.drawText(String.valueOf(mLeftUpVal) + "%", leftX - offLeftX, upY - offUpY, mTextPaint);
            mTextPaint.setTextAlign(Paint.Align.LEFT);
//            canvas.drawText(String.valueOf(mRightUpVal) + "%", rightX + offRightX, upY - offUpY, mTextPaint);
            canvas.drawText(String.valueOf(mRightDownVal) + "%", rightX + offRightX, downY + offDownY, mTextPaint);
        } else if (mContext.getString(R.string.pm2_5).equals(mType) || mContext.getString(R.string.co2).equals(mType)) {
            //去掉2个角的文本
            mTextPaint.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText(String.valueOf(mLeftDownVal), leftX - offLeftX, downY + offDownY, mTextPaint);
//            canvas.drawText(String.valueOf(mLeftUpVal), leftX - offLeftX, upY - offUpY, mTextPaint);
            mTextPaint.setTextAlign(Paint.Align.LEFT);
//            canvas.drawText(String.valueOf(mRightUpVal), rightX + offRightX, upY - offUpY, mTextPaint);
            canvas.drawText(String.valueOf(mRightDownVal), rightX + offRightX, downY + offDownY, mTextPaint);
        }
    }

    /**
     * 画左上点
     *
     * @param canvas
     * @param leftX
     * @param upY
     * @param r
     */
    private void drawLeftUpPoint(Canvas canvas, float leftX, float upY, int r, Paint point) {
        if (!mContext.getString(R.string.humidity).equals(mType)) {
            if (mContext.getString(R.string.temperature).equals(mType)) {
                //制冷季节不显示
                if (mApplication.getMode() != 1) {
                    canvas.drawCircle(leftX, upY, r, point);
                }
            } else {
                canvas.drawCircle(leftX, upY, r, point);
            }
        }
    }

    /**
     * 画右上点
     *
     * @param canvas
     * @param rightX
     * @param upY
     * @param r
     */
    private void drawRightUpPoint(Canvas canvas, float rightX, float upY, int r, Paint point) {
        if (!mContext.getString(R.string.humidity).equals(mType)) {
            if (mContext.getString(R.string.temperature).equals(mType)) {
                //制热季节不显示
                if (mApplication.getMode() != 2) {
                    canvas.drawCircle(rightX, upY, r, point);
                }
            } else {
                canvas.drawCircle(rightX, upY, r, point);
            }
        }
    }

    /**
     * //画摄氏度
     *
     * @param isLeft
     * @param val
     * @param canvas
     * @param x
     * @param y
     */
    private void setDegreesCelsius(boolean isLeft, int val, Canvas canvas, float x, float y) {
        Rect rect = new Rect();
        mTextPaint.getTextBounds(String.valueOf(val), 0, String.valueOf(val).length(), rect);
        Paint p = new Paint();
        if (mRotateAngle == -10000) {
            p.setColor(ContextCompat.getColor(getContext(), R.color.forty_percent_white));
        } else {
            p.setColor(ContextCompat.getColor(getContext(), R.color.white));
        }
        p.setTextSize(screenWidth / 40);
        if (isLeft) {
            canvas.drawText("℃", x + rect.width(), y - rect.height() / 3, p);
        } else {
            canvas.drawText("℃", x, y - rect.height() / 3, p);
        }
    }

    /**
     * 画圆弧
     */
    private void circleArc(Canvas canvas) {
        mPointX = screenWidth / 2;
        mPointY = screenHeight / 2 + 30;
//        LogUtils.d(TAG, "circleArc: screenHeight:"+screenHeight);
        mCircleRadius = screenWidth / 8;
        rect = new RectF(mPointX - mCircleRadius, mPointY - mCircleRadius, mPointX + mCircleRadius, mPointY + mCircleRadius);
        mCirclePaint.setStrokeWidth(screenWidth / 36);
        canvas.drawArc(rect, 135, 270, false, mCirclePaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        screenWidth = getMeasuredWidth();
        screenHeight = getMeasuredHeight();
    }

    public int getSensorVal() {
        return mSensorVal;
    }

    public void setSensorVal(int sensorVal) {
        setVal(sensorVal);
    }

    public void setSensorVal(int sensorVal, int tempVal) {
        if (mContext.getString(R.string.temperature).equals(mType)) {
            mSensorVal = -200;
            tempSensorValStep = -200;
        }
        /** iVRV 2017/05/08  add START */
        if (tempVal == -1000) {
            mRotateAngle = -10000;
            invalidate();
            return;
        }
        /** iVRV 2017/05/08  add END */
        mSetVal = tempVal;

        tempSensorVal = tempVal;
        //添加动画效果
        Message m = new Message();
        mStepVal = 10;
        m.what = 3;
//        LogUtils.d(TAG, "setSensorVal: mStepVal:" + mStepVal);
        mHandler.sendMessage(m);
    }

    private void setVal(int sensorVal) {
        /** iVRV 2017/05/08  add START */
        if (sensorVal == -1000) {
            mRotateAngle = -10000;
            invalidate();
            return;
        }
        /** iVRV 2017/05/08  add END */
//        this.mSensorVal = sensorVal;
//        initView();
//        invalidate();
        mSetVal = sensorVal;
        //添加动画效果
        Message m = new Message();
        mStepVal = (sensorVal - mSensorVal) / 50;
        if (sensorVal - mSensorVal > 0 && sensorVal - mSensorVal < 50) {
            mStepVal = 1;
        }
        if (sensorVal - mSensorVal < 0 && sensorVal - mSensorVal > -50) {
            mStepVal = -1;
        }
        if (mStepVal > 0) {
            m.what = 1;
        } else {
            m.what = 2;
        }
        LogUtils.d(TAG, "setSensorVal: mStepVal:" + mStepVal);
        mHandler.sendMessage(m);
    }

    public void setType(String type) {
        this.mType = type;
        initView();
        invalidate();
    }

    public void setTypeAndVal(String type, int sensorVal) {
        this.mSensorVal = sensorVal;
        this.mType = type;
        initView();
        invalidate();
    }
}
