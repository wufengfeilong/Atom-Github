package woxingwoxiu.com.message.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import woxingwoxiu.com.R;
import woxingwoxiu.com.message.bean.MessageListBean;
import woxingwoxiu.com.message.presenter.MessageListFragmentPresenter;


/**
 * Created by 860117073 on 2018/5/11.
 * 我的列表用多布局adapter
 */

public class MessageListAdapter extends BaseAdapter {
    private Context context;

    private List<MessageListBean.Result> dataList;

    private LayoutInflater inflater;

    //布局数量
    private final int TYPE_COUNT = 3;

    //喜欢了你的作品
    private final int TYPE_ONE = 1;

    //关注了你
    private final int TYPE_TWO = 2;

    //我行我秀小助手
    private final int TYPE_ZERO = 0;

    //加载布局类型
    private int currentType;

    public MessageListAdapter(Context context, List<MessageListBean.Result> dataList){
        this.context = context;
        this.dataList = dataList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        MessageListBean.Result result = dataList.get(position);
        //根据currentType来加载不同的布局,并复用convertview
        currentType = getItemViewType(position);

        if (currentType == TYPE_ONE) {
            //加载喜欢了你的作品布局
            ViewHolderOne viewHolderOne;
            //首先判断convertview==null
            if (convertView == null) {
                viewHolderOne = new ViewHolderOne();
                convertView = inflater.inflate(R.layout.message_like_item, null);
                viewHolderOne.headPic = (ImageView) convertView.findViewById(R.id.like_head);
                viewHolderOne.userName = (TextView) convertView.findViewById(R.id.like_name);
                viewHolderOne.passTime = (TextView) convertView.findViewById(R.id.like_time);
                viewHolderOne.actionPic = (ImageView) convertView.findViewById(R.id.like_action);
                convertView.setTag(viewHolderOne);
            } else {
                viewHolderOne = (ViewHolderOne) convertView.getTag();
            }
            viewHolderOne.userName.setText(result.getUserName());
            viewHolderOne.passTime.setText(result.getPassTime());
            viewHolderOne.headPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MessageListFragmentPresenter presenter =new MessageListFragmentPresenter();
                    presenter.toFansHome();
                }
            });
            viewHolderOne.userName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MessageListFragmentPresenter presenter =new MessageListFragmentPresenter();
                    presenter.toFansHome();
                }
            });

        } else if (currentType == TYPE_TWO) {
            //加载关注了你布局
            ViewHolderTwo viewHolderTwo;
            if (convertView == null) {
                viewHolderTwo = new ViewHolderTwo();
                convertView = inflater.inflate(R.layout.message_attention_item, null);
                viewHolderTwo.headPic = (ImageView) convertView.findViewById(R.id.attention_head);
                viewHolderTwo.userName = (TextView) convertView.findViewById(R.id.attention_user);
                viewHolderTwo.passTime =(TextView) convertView.findViewById(R.id.attention_time);
                viewHolderTwo.actionNum = (TextView) convertView.findViewById(R.id.action_number);
                viewHolderTwo.fansNum = (TextView) convertView.findViewById(R.id.fans_number);
                convertView.setTag(viewHolderTwo);
            } else {
                viewHolderTwo =(ViewHolderTwo)convertView.getTag();
            }
            viewHolderTwo.userName.setText(result.getUserName());
            viewHolderTwo.passTime.setText(result.getPassTime());
            viewHolderTwo.actionNum.setText(result.getActionNumber());
            viewHolderTwo.fansNum.setText(result.getFansNumber());
            viewHolderTwo.headPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MessageListFragmentPresenter presenter =new MessageListFragmentPresenter();
                    presenter.toFansHome();
                }
            });
            viewHolderTwo.userName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MessageListFragmentPresenter presenter =new MessageListFragmentPresenter();
                    presenter.toFansHome();
                }
            });
        }else if (currentType == TYPE_ZERO) {
            //加载小助手布局
             ViewHolderZero viewHolderZero;
            if (convertView == null) {
                viewHolderZero = new ViewHolderZero();
                convertView = inflater.inflate(R.layout.message_assistant_item, null);
                viewHolderZero.passTime =(TextView)convertView.findViewById(R.id.assistant_time);
                viewHolderZero.assistantContent=(TextView)convertView.findViewById(R.id.assistant_content);
                viewHolderZero.headPic=(ImageView)convertView.findViewById(R.id.assistant_head) ;
                viewHolderZero.userName=(TextView) convertView.findViewById(R.id.assistant_name);
                convertView.setTag(viewHolderZero);
            }
            else {
            viewHolderZero =(ViewHolderZero)convertView.getTag();
            }
            viewHolderZero.passTime.setText(result.getPassTime());
            viewHolderZero.assistantContent.setText(result.getAssistantContent());
            viewHolderZero.headPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MessageListFragmentPresenter presenter =new MessageListFragmentPresenter();
                    presenter.toFansHome();
                }
            });
            viewHolderZero.userName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MessageListFragmentPresenter presenter =new MessageListFragmentPresenter();
                    presenter.toFansHome();
                }
            });


        }
        return convertView;
    }

    class ViewHolderOne {
        public ImageView headPic;
        public TextView userName;
        public TextView passTime;
        public ImageView actionPic;
    }

    class ViewHolderTwo {
        public ImageView headPic;
        public TextView userName;
        public TextView passTime;
        public TextView actionNum;
        public TextView fansNum;
    }

    class ViewHolderZero {
        public ImageView headPic;
        public TextView userName;
        public TextView passTime;
        public TextView assistantContent;
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        MessageListBean.Result result = dataList.get(position);
        int type = result.getType();
        switch (type) {
            case TYPE_ONE:
                return TYPE_ONE;

            case TYPE_TWO:
                return TYPE_TWO;

            case TYPE_ZERO:
                return TYPE_ZERO;
            default:
                return -1;
        }
    }
}
