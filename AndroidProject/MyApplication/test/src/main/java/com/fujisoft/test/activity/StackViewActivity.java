package com.fujisoft.test.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.StackView;
import android.widget.TextView;
import com.fujisoft.test.R;
import com.fujisoft.test.bean.Card;

import java.util.ArrayList;
import java.util.List;

public class StackViewActivity extends AppCompatActivity {
    StackView stackView;
    MyAdapter myAdapter;
    List<Card> mDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stack_view);
        stackView = (StackView) findViewById(R.id.stack_view);
        getData();
        myAdapter = new MyAdapter();
        stackView.setAdapter(myAdapter);
    }

    public void getData() {
        mDate = new ArrayList<>();
        Card card1 = new Card(R.drawable.yezi,"层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件1");
        mDate.add(card1);
        Card card2 = new Card(R.drawable.yezi,"层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件2");
        mDate.add(card2);
        Card card3 = new Card(R.drawable.yezi,"层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件3");
        mDate.add(card3);
        Card card4 = new Card(R.drawable.yezi,"层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件4");
        mDate.add(card4);
        Card card5 = new Card(R.drawable.yezi,"层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件层叠卡片控件5");
        mDate.add(card5);
    }

    public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mDate.size();
        }

        @Override
        public Object getItem(int position) {
            return mDate.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(StackViewActivity.this).inflate(R.layout.stack_item, null);
                holder.tv = convertView.findViewById(R.id.textView);
                holder.img = convertView.findViewById(R.id.img);

                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv.setText(mDate.get(position).getContent());
            holder.img.setImageResource(mDate.get(position).getImg());

            return convertView;
        }

         class ViewHolder {
            ImageView img;
            TextView tv;
        }
    }


}
