package com.fujisoft.card;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.fashare.stack_layout.StackLayout;
import com.fashare.stack_layout.transformer.AlphaTransformer;
import com.fashare.stack_layout.transformer.AngleTransformer;
import com.fashare.stack_layout.transformer.StackPageTransformer;
import com.squareup.picasso.Picasso;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    String IMG_BASEURL = "http://172.29.140.35:5004/Card/";
    List<Card> mData;
    StackLayout sl;
    FloatingActionButton fab;
    MyAdapter adapter;
    IToast iToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getData();
        sl = (StackLayout) findViewById(R.id.stack_layout);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        iToast = new IToast(this);
        adapter = new MyAdapter();
        sl.setAdapter(adapter);
        sl.addPageTransformer(
                new StackPageTransformer()     // 堆叠
                , new AlphaTransformer()        // 渐变
                , new AngleTransformer()       // 角度
        );
        sl.setOnSwipeListener(new StackLayout.OnSwipeListener() {
            @Override
            public void onSwiped(View swipedView, int swipedItemPos, boolean isSwipeLeft, int itemLeft) {
                String showStr = (isSwipeLeft ? "往左" : "往右") + "移除" + mData.get(swipedItemPos) + "." + "剩余" + itemLeft + "项";
//                Toast.makeText(MainActivity.this, showStr, Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onSwiped: isSwipeLeft:" + isSwipeLeft);
                Log.e(TAG, "onSwiped: swipedItemPos:" + swipedItemPos);
                Log.e(TAG, "onSwiped: itemLeft:" + itemLeft);
                Log.e(TAG, "onSwiped: mData.size:" + mData.size());
                if (isSwipeLeft) {
                    iToast.imgToast(R.drawable.unlike);
                    RetrofitManager
                            .getInstance()
                            .changeInterestById(
                                    mData.get(swipedItemPos).getInterest_id()
                                    , -1
                                    , new RetrofitManager.Result() {
                                        @Override
                                        public void getResult(Object o) {
                                            Log.d(TAG, "getResult: "+o.toString());
                                        }
                                    });
                } else {
                    iToast.imgToast(R.drawable.like);
                    RetrofitManager
                            .getInstance()
                            .changeInterestById(
                                    mData.get(swipedItemPos).getId()
                                    , 1
                                    , new RetrofitManager.Result() {
                                        @Override
                                        public void getResult(Object o) {
                                            Log.d(TAG, "getResult: "+o.toString());
                                        }
                                    });
                }
                // 少于5条, 加载更多
                if (itemLeft < 5) {
                    // TODO: loadmore
                }
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "点击了悬浮按钮", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getData() {
        mData = new ArrayList<>();
//        RetrofitManager
//                .getInstance()
//                .getRetrofitAPIService()
//                .getCards()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<List<Card>>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(List<Card> cards) {
//                        mData = cards;
//                        adapter.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
        RetrofitManager.getInstance().getCards(new RetrofitManager.Result<List<Card>>() {
            @Override
            public void getResult(List<Card> list) {
                Log.e(TAG, "getResult: hahah50");
                mData = list;
                adapter.notifyDataSetChanged();
            }
        });
    }

    public class MyAdapter extends StackLayout.Adapter<MyAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (mData.get(position).getImg_name() == null || "".equals(mData.get(position).getImg_name())) {
                holder.imageView.setVisibility(View.GONE);
            } else {
                holder.imageView.setVisibility(View.VISIBLE);
                Picasso.with(MainActivity.this)
                        .load(IMG_BASEURL+mData.get(position).getImg_name())
                        .into(holder.imageView);
            }
            if (mData.get(position).getContent()==null || "".equals(mData.get(position).getContent())) {
                holder.content.setVisibility(View.GONE);
            } else {
                holder.content.setVisibility(View.VISIBLE);
                holder.content.setText(mData.get(position).getContent());

            }
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        public class ViewHolder extends StackLayout.ViewHolder {
            TextView content;
            ImageView imageView;

            public ViewHolder(View inflate) {
                super(inflate);
                content = inflate.findViewById(R.id.content);
                imageView = inflate.findViewById(R.id.img);
            }
        }
    }

}
