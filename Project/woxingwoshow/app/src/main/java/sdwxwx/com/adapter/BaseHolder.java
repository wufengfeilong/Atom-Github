package sdwxwx.com.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

public class BaseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private BaseAdapter.OnItemClickListener mListener;
    //不写死控件变量，而采用Map方式
    private HashMap<Integer, View> mViews = new HashMap<>();
    public BaseHolder(View itemView, BaseAdapter.OnItemClickListener listener) {
        super(itemView);
        mListener = listener;
    }
    /**
     *获取控件的方法
     */
    public<T> T getView(Integer viewId){
        //根据保存变量的类型 强转为该类型
        View view = mViews.get(viewId);
        if(view==null){
            view= itemView.findViewById(viewId);
            //缓存
            mViews.put(viewId,view);
            // 为ItemView添加点击事件
            itemView.setOnClickListener(this);
        }
        return (T)view;
    }
    /**
     *传入文本控件id和设置的文本值，设置文本
     */
    public BaseHolder setText(Integer viewId, String value){
        TextView textView = getView(viewId);
        if (textView != null) {
            textView.setText(value);
        }
        return this;
    }
    /**
     * 传入图片控件id和资源id，设置图片
     */
    public BaseHolder setImageResource(Integer viewId, Integer resId) {
        ImageView imageView = getView(viewId);
        if (imageView != null) {
            imageView.setImageResource(resId);
        }
        return this;
    }

    /**
     * 传入图片控件id和资源id，设置图片
     */
    public BaseHolder setImageBitmap(Integer viewId, Bitmap bitmap) {
        ImageView imageView = getView(viewId);
        if (imageView != null) {
            imageView.setImageBitmap(bitmap);
        }
        return this;
    }

    @Override
    public void onClick(View v) {
        // getpostion()为Viewholder自带的一个方法，用来获取RecyclerView当前的位置，将此作为参数，传出去
        mListener.onItemClick(v, getAdapterPosition());
    }
    //...还可以扩展出各种控件。
    //Fluent API 链式api  obj.setxxx().setyyy()....
}
