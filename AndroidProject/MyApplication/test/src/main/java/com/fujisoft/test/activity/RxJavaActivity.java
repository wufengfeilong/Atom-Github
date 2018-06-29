package com.fujisoft.test.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.fujisoft.test.R;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class RxJavaActivity extends AppCompatActivity {
    private static final String TAG = "RxJavaActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);

        initView();
    }

    private void initView() {
        //创建 Observer 即观察者
        Observer observer = new Observer() {
            @Override
            public void onError(Throwable e) {
                Toast.makeText(RxJavaActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onError: "+e.getMessage());
            }

            @Override
            public void onComplete() {
                Toast.makeText(RxJavaActivity.this, "Completed", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onCompleted: Completed");
            }

            @Override
            public void onSubscribe(Disposable d) {
                //可用于取消订阅
//                d.dispose();
                //还可以判断是否处于取消状态
                //boolean b=d.isDisposed();
            }

            @Override
            public void onNext(Object o) {
                Toast.makeText(RxJavaActivity.this, o.toString(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onNext: "+o.toString());
            }
        };
        //创建 Observable 即被观察者
        Observable observable = Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter e) throws Exception {
                e.onNext("one");
                e.onNext("two");
                e.onNext("three");
                e.onComplete();
            }
        });
        // Subscribe (订阅)
        // 创建了 Observable 和 Observer 之后，再用 subscribe() 方法将它们联结起来，整条链子就可以工作了
        observable.subscribe(observer);
    }


}
