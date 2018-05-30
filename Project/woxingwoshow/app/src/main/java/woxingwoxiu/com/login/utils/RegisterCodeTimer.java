package woxingwoxiu.com.login.utils;

import android.os.CountDownTimer;
import android.support.annotation.ColorRes;
import android.view.View;
import android.widget.TextView;

/**
 * Created by 丁胜胜 on 2018/04/10.
 * 注册验证码计时器
 */

public class RegisterCodeTimer extends CountDownTimer {
    private static int DEFAULT_TIME = 60000;
    private View mSendView;
    private int mEndTimeBackgroundRes;

    public RegisterCodeTimer(View sendView) {
        super(DEFAULT_TIME, 1000);
        mSendView = sendView;
    }

    public void startTiming(@ColorRes int beginBackgroundRes, @ColorRes int endTimeBackgroundRes) {
        mEndTimeBackgroundRes = endTimeBackgroundRes;
        mSendView.setEnabled(false);
        mSendView.setBackgroundResource(beginBackgroundRes);
        start();
    }

    // 结束
    @Override
    public void onFinish() {
        // TODO Auto-generated method stub
        if (mSendView != null && mSendView instanceof TextView) {
            ((TextView) mSendView).setText("重新发送");
            mSendView.setEnabled(true);
            mSendView.setBackgroundResource(mEndTimeBackgroundRes);
        }

    }

    @Override
    public void onTick(long millisUntilFinished) {
        // TODO Auto-generated method stub
        String s = (millisUntilFinished / 1000) + "s 后重发";
        if (mSendView != null && mSendView instanceof TextView)
            ((TextView) mSendView).setText(s);

    }
}
